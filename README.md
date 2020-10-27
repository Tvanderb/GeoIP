# GeoIP
 
A simple Java library that uses web scraping to get an IP addresses geolocational coordinates.

## Example Usage:

```java
public class Test {
    public static void main(String... args) throws IOException {
        System.out.println("Location: " + Location.getMyLocation());
        System.out.println("Info: " + AddressInfo.getMyAddress());
        
        System.out.println("\n--------------------------\n");
        
        System.out.println("Location: " + Location.getLocation("google.com"));
        System.out.println("Info: " + AddressInfo.getInfo("google.com"));
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
