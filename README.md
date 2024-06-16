# Hiss Spring Boot JPA Starter [![build status](https://github.com/Tap30/hiss-spring-boot-jpa-starter/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/Tap30/hiss-spring-boot-jpa-starter/actions/workflows/build.yml)

An small library which uses Spring Boot autoconfiguration capability that integrates Hiss with Spring Boot and Spring Data JPA.

By integrating Hiss with Spring Boot project we mean registration of:
- Hiss bean using `HissPropertiesFromEnvProvider`
- JPA interceptor which automatically encrypts objects before saving to DB and decrypts them after loading.

## Quick Start

### 1. Add Hiss dependency

Apache Maven:
```xml
<dependency>
    <groupId>io.github.tap30</groupId>
    <artifactId>hiss-spring-boot-jpa-starter</artifactId>
    <version>0.9.0</version>
</dependency>
```

Gradle (Groovy):
```groovy
implementation 'io.github.tap30:hiss-spring-boot-jpa-starter:0.9.0'
```

Gradle (Kotlin):
```kotlin
implementation("io.github.tap30:hiss-spring-boot-jpa-starter:0.9.0")
```

### 2. Set environment variables

```bash
HISS_KEYS_A=AAAAAAAAAAAAAAAAAAAAAA\=\=
HISS_KEYS_B=AAAAAAAAAAAAAAAAAAAAAA\=\=
# other keys...
HISS_DEFAULT_ENCRYPTION_KEY_ID=a
HISS_DEFAULT_ENCRYPTION_ALGORITHM=aes-128-gcm
HISS_DEFAULT_HASHING_KEY_ID=b
HISS_DEFAULT_HASHING_ALGORITHM=hmac-sha256
```

For more information about envs see
[this](https://github.com/Tap30/hiss?tab=readme-ov-file#hisspropertiesfromenvprovider).

### 3. Annotate your class with `@EntityListeners(value = {HissJpaEventListener.class})` and the fields you want to encrypt with `@Encrypted`

```java
import io.github.tap30.Encrypted;

@EntityListeners(value = {HissJpaEventListener.class})
public class User {
    @Encrypted
    private String phoneNumber;
    private String hashedPhoneNumber;

    // getters and setters
}
```

Note: Getters and setters must exist as Hiss use them to get/set values.

## Using custom `HissPropertiesProvider`

By implementing `HissPropertiesProvider` and annotating it with `@Component`
this library will pick your implementation rather than default `HissPropertiesFromEnvProvider`.

## Querying Data

Currently there is not easy way to support querying encrypted fields.

To query data, inject Hiss bean (`@Autowired Hiss hiss`)
and use `Hiss$hash(String)` method to generate hash of content;
then pass it to the queries which use hashed fields.
