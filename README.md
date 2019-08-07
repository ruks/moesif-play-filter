# Moesif Play Filter SDK

 [![Built For][ico-built-for]][link-built-for]
 [![Latest Version][ico-version]][link-package]
 [![Software License][ico-license]][link-license]
 [![Source Code][ico-source]][link-source]

## Introduction

`moesif-play-filter` is a Play framework Filter that logs API calls and sends to [Moesif](https://www.moesif.com) for API analytics and log analysis.

The SDK is implemented as a [Scala HTTP Filter](https://www.playframework.com/documentation/latest/ScalaHttpFilters)
without importing framework specific dependencies. Any Web Application built on Play can use this SDK with minimal configuration.

[Source Code on GitHub](https://github.com/moesif/moesif-play-filter)

## How to install

#### Maven users

Add the Moesif dependency to your project's pom.xml file:

```xml
<!-- Include jcenter repository if you don't already have it. -->
<repositories>
    <repository>
        <id>jcenter</id>
        <url>https://jcenter.bintray.com/</url>
    </repository>
</repositories>
    
<dependency>
    <groupId>com.moesif.filter</groupId>
    <artifactId>moesif-play-filter</artifactId>
    <version>1.0</version>
</dependency>
```

#### Gradle users

Add the Moesif dependency to your project's build.gradle file:

```gradle
// Include jcenter repository if you don't already have it.
repositories {
    jcenter()
}
 
dependencies {   
    compile 'com.moesif.filter:moesif-play-filter:1.0'
}
```

#### Others

The jars are available from a public [Bintray Jcenter](https://bintray.com/moesif/maven/moesif-servlet) repository.


## How to use

MoesifApiFilter can be enabled using play framework application configuration. More details [here]

In your play configuration file(default application.conf), enable MoesifApiFilter as below

```
play.filters.enabled += "com.moesif.filter.MoesifApiFilter"

play.modules.enabled += "com.moesif.filter.MoesifApiFilterModule"

play.filters.moesifApiFilter.moesifApplicationId = "Your Moesif application Id"
```

Your Moesif Application Id can be found in the [_Moesif Portal_](https://www.moesif.com/).
After signing up for a Moesif account, your Moesif Application Id will be displayed during the onboarding steps. 

You can always find your Moesif Application Id at any time by logging 
into the [_Moesif Portal_](https://www.moesif.com/), click on the top right menu,
and then clicking _Installation_.

With the above basic configuration setup Moesif filter will capture api events and send them to Moesif. 

## Advanced Configuration options

Moesif provides the most value when we can identify users. You may also want to specify metadata, mask certain data, or prevent tracking of certain requests entirely. This is possible with the hooks available in trait MoesifAdvancedFilterConfiguration.

To configure the filter, extend the `MoesifAdvancedFilterConfiguration` class to override methods as desired and then
set config in MoesifAdvancedFilterConfiguration to override the default config

```scala
import com.moesif.filter.MoesifAdvancedFilterConfiguration
import play.api.mvc.{RequestHeader, Result}

class CustomMoesifAdvancedConfig extends MoesifAdvancedFilterConfiguration {

  override def getMetadata(request: RequestHeader, result: Result): Map[String, String] = Map("customAttribute" -> "atrributeValue")
}

// In you root play application initialization code set custom config
MoesifAdvancedFilterConfiguration.setConfig(new CustomMoesifAdvancedConfig())
```
Advanced configuration options are explained beloe

#### 1. `def skip(request: RequestHeader, result: Result): Boolean`
Return `true` if you want to skip logging a
request to Moesif i.e. to skip boring requests like health probes.

```scala
  override def skip(request: RequestHeader, result: Result): Boolean =  {
    // Skip logging health probes
    request.uri == "health/probe";
  }
```

#### 2. `def maskContent(moesifEventModel: EventModel): EventModel = moesifEventModel`
If you want to remove any sensitive data in the HTTP headers or body before sending to Moesif, you can do so with `maskContent`

#### 3. `def identifyUser(request: RequestHeader, result: Result): Option[String]`
Highly recommended. Even though Moesif automatically detects the end userId if possible, setting this configuration
ensures the highest accuracy with user attribution.

```scala
  override def identifyUser(request: RequestHeader, result: Result): Option[String] = {
    request.headers.headers.find(_._1 == "user").map(_._2)
  }
```

#### 4. `def identifyCompany(request: RequestHeader, result: Result): Option[String]`
You can set this configuration to add company Id to the event.

```scala
  override def identifyCompany(request: RequestHeader, result: Result): Option[String] = {
    return "12345";
  }
```

### 5. `public String getSessionToken(HttpServletRequest request, HttpServletResponse response)`

Moesif automatically detects the end user's session token or API key, but you can manually define the token for finer control.

```scala
  override def sessionToken(request: RequestHeader, result: Result): Option[String] = {
    request.headers.headers.toMap.get("Authorization")
  }
```

### 6. `def getMetadata(request: RequestHeader, result: Result): Map[String, String] `
You can add any additional tags as needed
to the event.


## Other integrations

To view more more documentation on integration options, please visit __[the Integration Options Documentation](https://www.moesif.com/docs/getting-started/integration-options/).__

