# SimpleJWT
###### Utility to easily manage JWT

[![Generic badge](https://img.shields.io/badge/Java-17-white.svg)]()
[![Generic badge](https://img.shields.io/nexus/r/de.hypercdn.simple/JWT?color=white&label=Latest%20Version&nexusVersion=3&server=https%3A%2F%2Fcd.voigt.app%2F)]()

## Installation
Grab the latest version from our [Nexus-Proxy](https://nexus.hypercdn.de/) using Gradle or Maven.  
⚠️Be aware that this instance acts as proxy for load distribution.  
⚠️Artifacts might not be listed till requested and the initial load might be very slow.

### Gradle

```groovy
repositories {
    maven {
        name 'maven-hypercdn'
        url 'https://nexus.hypercdn.de/repository/maven-hypercdn'
    }
}
```
```groovy
dependencies {
    implementation group: 'de.hypercdn.simple', name: 'JWT', version: 'REPLACE WITH LATEST VERSION'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>maven-hypercdn</id>
        <name>maven-hypercdn</name>
        <url>https://nexus.hypercdn.de/repository/maven-hypercdn</url>
    </repository>
</repositories>
```

```xml

<dependencies>
    <dependency>
        <groupId>de.hypercdn.simple</groupId>
        <artifactId>JWT</artifactId>
        <version>REPLACE WITH LATEST VERSION</version>
    </dependency>
</dependencies>
```

## Usage
Initialize a manager for the jwts. This instance should be reused throughout your application.
```java
var jwtManager = new GenericJwtManagerBuilder()
    .setIssuerId("desired.issuer.id")
    .build();
```
Create a new jwt with the desired settings through the manager.
```java
var jwt = jwtManager.newJwt()
    .setExpiration(Duration.ofHours(1))
    .build();

// jwt as url safe string
var jwtString = jwt.asJwtString();

// can also be parsed from such string
var jwtParsed = JwtEntity.fromString(jwtString);
```
Verify and invalidate jwts
```java
jwtManager.verify(jwtParsed); // true
jwtManager.invalidate(jwtParsed);
jwtManager.verify(jwtParsed); // false
```