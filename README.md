# Twitch Java API (TJA)
This is a asynchronous Java ported library of the Twitch REST API.

Pull requests welcome.

## Adding SHJA as a dependency
SHJA currently uses Jitpack https://jitpack.io/#TheLimeGlass/Twitch-Java-API/VERSION
### Maven
In your `pom.xml` add:
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
    <groupId>com.github.TheLimeGlass</groupId>
    <artifactId>Twitch-Java-API</artifactId>
    <version>VERSION</version>
</dependency>

```
### Gradle
In your `build.gradle` add: 
```groovy
repositories {
  jcenter()
  maven {
    url 'https://jitpack.io'
  }
}

dependencies {
  compile 'com.github.TheLimeGlass:Twitch-Java-API:VERSION'
}
```
Check the link above for SBT and leiningen support.

Examples: https://github.com/TheLimeGlass/Twitch-Java-API/tree/master/src/test/java/me/limeglass/twitch

### TODO
- Built in rate limiter.
