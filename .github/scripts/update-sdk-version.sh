#!/bin/bash -e
# Ref: https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/.github/scripts/update-sdk-version.sh

version=$1

sed -Ei "s/(opentelemetrySdk\s*:\s*\")[0-9]+\.[0-9]+\.[0-9]+/\1$version/" build.gradle
