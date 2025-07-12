#!/bin/bash -e
# Ref: https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/.github/scripts/gha-free-disk-space.sh

# GitHub Actions runners have only provide 14 GB of disk space which we have been exceeding regularly
# https://docs.github.com/en/actions/using-github-hosted-runners/about-github-hosted-runners#supported-runners-and-hardware-resources

df -h
sudo rm -rf /usr/local/lib/android
sudo rm -rf /usr/share/dotnet
df -h