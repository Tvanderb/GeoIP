# GeoIP
 
A simple Java library that uses web scraping to get an IP addresses geolocational coordinates.

## Example Usage:

```java
public class Test {
    public static void main(String... args) throws IOException {
        Location loc = Location.getMyLocation();
        
        System.out.println("Latitude: " + loc.getLatitude());
        System.out.println("Longitude: " + log.getLongitude());
        
        loc = Location.getLocation("192.168.0.1");
        
        System.out.println("Latitude: " + loc.getLatitude());
        System.out.println("Longitude: " + loc.getLongitude());
    }
}
```

## Installation

### Gradle
```gradle
repositories {
    mavenCentral()
}

dependencies {
    compile 'com.github.tvanderb:GeoIP:version'
}
```

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>com.github.tvanderb</groupId>
        <artifactId>GeoIP</artifactId>
        <version>version</version>
    </dependency>
</dependencies>
```
