# `ExcludeClassPropagator` extension for OpenTelemetry Java Agent

## Purpose
Wraps the default `W3CTraceContextPropagator` to conditionally exclude `ClassNames` from having context injected into outbound requests/messages in Java Apps that instrumented by OpenTelemetry Java Agent.

## Requirements
- Gradle 8.14.2
- Java 8

## Usage
```
java -javaagent:path/to/opentelemetry-javaagent.jar \
-Dotel.javaagent.extensions=build/libs/exclude-class-propagator.jar \
-Dotel.propagators.tracecontext-exclude \
-Dotel.instrumentation.propagators.tracecontext-exclude.injection=package1.ClassName1,package2.ClassName2 \
-jar path/to/target-app.jar
```

## Reference
- https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/examples/extension