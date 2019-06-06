package me.limeglass.twitch.internals.event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import me.limeglass.twitch.api.TwitchClient;
import me.limeglass.twitch.api.annotations.EventListener;
import me.limeglass.twitch.api.objects.Listener;
import me.limeglass.twitch.internals.exceptions.TwitchAPIException;

public class EventDispatcher {

	private final Set<MethodHandler<?,?>> registered = new HashSet<>();
	private final MethodHandles.Lookup lookup = MethodHandles.lookup();
	private final TwitchClient client;

	public EventDispatcher(TwitchClient client) {
		this.client = client;
	}

	/**
	 * @param <C> <? extends Listener>
	 * @param listener The class of which contains the EventListener annotation to then call that method when the Event parameter happens.
	 */
	public <C extends Listener> Class<C>[] registerListeners(@SuppressWarnings("unchecked") Class<C>... listeners) {
		for (Class<C> listener : listeners)
			registerListener(listener);
		return listeners;
	}

	/**
	 * @param listener The class of which contains the EventListener annotation to then call that method when the Event parameter happens.
	 */
	public <C extends Listener> Class<C> registerListener(Class<C> listener) {
		return registerListener(listener, null);
	}

	/**
	 * @param <E> <? extends TwitchEvent>
	 * @param <L> <? extends Listener>
	 * @param clazz The class of which contains the EventListener annotation to then call that method when the Event parameter happens.
	 * @param The instance of the listener class.
	 */
	public <L extends Listener, E extends TwitchEvent> Class<L> registerListener(Class<L> clazz, Listener listener) {
		Stream.of(clazz.getMethods()).filter(method -> method.isAnnotationPresent(EventListener.class))
				.forEach(method -> {
					if (method.getParameterCount() != 1) {
						TwitchAPIException cause = new TwitchAPIException("A EventHandler's method parameter was incorrect, it may only be one parameter that being of a TwitchEvent.");
						client.getLogger().atSevere()
								.withCause(cause)
								.log("Invalid method " + method);
						return;
					}
					Class<?> parameter = method.getParameterTypes()[0];
					if (!TwitchEvent.class.isAssignableFrom(parameter)) {
						TwitchAPIException cause = new TwitchAPIException("Parameter type is not a TwitchEvent.");
						client.getLogger().atSevere()
								.withCause(cause)
								.log("Invalid method " + method);
						return;
					}
					@SuppressWarnings("unchecked") // parameter is |E|
					Class<E> event = (Class<E>) parameter;
					method.setAccessible(true);
					try {
						registered.add(new MethodHandler<E,L>(lookup.unreflect(method), clazz, event, listener));
					} catch (IllegalAccessException e) {
						client.getLogger().atWarning()
								.withCause(new TwitchAPIException(e))
								.log("Method %s is not accessible.", method.getName());
						return;
					}
				});
		return clazz;
	}

	/**
	 * Used internally, call this when an event happens to trigger the API events.
	 * 
	 * @param event The TwitchEvent that just happened.
	 * @return The TwitchEvent that was called, used for returning modified events.
	 */
	public TwitchEvent dispatch(TwitchEvent event) {
		registered.parallelStream()
				.filter(handler -> handler.accepts(event))
				.forEach(handler -> {
					try {
						handler.handle(event);
					} catch (Throwable e) {
						client.getLogger().atSevere()
								.withCause(new TwitchAPIException(e))
								.log("Error dispatching event %s", event.getClass().getSimpleName());
					}
				});
		return event;
	}

	/**
	 * A class in which helps invoke registered methods that contain the EventListener annotation.
	 * @param <E> <? extends TwitchEvent>
	 * @param <L> <? extends Listener>
	 */
	private class MethodHandler<E extends TwitchEvent, L extends Listener> {

		private final Class<L> listenerClass;
		private final Class<E> eventClass;
		private final MethodHandle handle;
		private Listener listener;

		public MethodHandler(MethodHandle handle, Class<L> listenerClass, Class<E> eventClass, Listener listener) {
			this.listenerClass = listenerClass;
			this.eventClass = eventClass;
			this.listener = listener;
			this.handle = handle;
		}

		public void handle(TwitchEvent event) throws Throwable {
			Constructor<?>[] constructors = listenerClass.getConstructors();
			if (constructors.length > 0 && constructors[0].getParameterCount() > 0 && listener == null) {
				client.getLogger().atWarning()
						.withCause(new IllegalArgumentException())
						.log("The listener instance may not be null and have a constructor at the same time.\n Use registerListener(Listener.class, new SubClassListener())");
			} else if (listener == null)
				listener = (Listener) listenerClass.newInstance();
			handle.invoke(event);
		};

		public boolean accepts(TwitchEvent event) {
			return eventClass.equals(event.getClass());
		}

	}

}
