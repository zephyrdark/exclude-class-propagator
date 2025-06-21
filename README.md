# `ExcludeClassPropagator` extension for OpenTelemetry Java Agent

## Purpose
Wraps the default `W3CTraceContextPropagator` to conditionally exclude `ClassNames` from having context injected into outgoing requests/messages in Java Apps that instrumented by OpenTelemetry Java Agent.

This can be used to ensure that outgoing requests/messages do not contain context propagation "extra data" like `traceparent` in their headers, while still allowing the instrumented Apps to emit traces.

## Requirements
- Gradle 8.14.2
- Java 8

## Building manually
1. Clone the repo into your `<project-root-directory>`
    ```
    git clone https://github.com/zephyrdark/exclude-class-propagator.git
    ```
2. Change directory into the repo
    ```
    cd exclude-class-propagator
    ```
3. Ensure formatting 
    ```
    gradle spotlessApply
    ```
4. Running below command will build 3 `.jar` files into `build/libs`
    ```
    gradle build
    ```
    Either of these built `.jar` files can be used:
    - `opentelemetry-javaagent-with-excludeClassPropagator.jar`
    - `exclude-class-propagator-1.0-all.jar`


## Usage
1. You can choose to either use the extended Otel Java Agent `opentelemetry-javaagent-with-excludeClassPropagator.jar` as follows:
    ```
    java -javaagent:path/to/opentelemetry-javaagent-with-excludeClassPropagator.jar \
    -Dotel.propagators.tracecontext-exclude \
    -Dotel.instrumentation.propagators.tracecontext-exclude.injection=package1.ClassName1,package2.ClassName2 \
    -jar path/to/target-app.jar
    ```
    or the original Otel Java Agent `opentelemetry-javaagent.jar` with the extension jar: 

    ```
    java -javaagent:path/to/opentelemetry-javaagent.jar \
    -Dotel.javaagent.extensions=build/libs/exclude-class-propagator.jar \
    -Dotel.propagators.tracecontext-exclude \
    -Dotel.instrumentation.propagators.tracecontext-exclude.injection=package1.ClassName1,package2.ClassName2 \
    -jar path/to/target-app.jar
    ```


## Reference
- https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/main/examples/extension


## To-do
1. Set up cron github action for updating otelSdk version via auto-update-otel-sdk.yml