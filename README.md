## Hivemq-metrics-per-topic-extension

### Purpose
This extension is designed to expose `Prometheus` metrics tracking the count of inbound and outbound messages per topic. Its implementation adheres closely to the HiveMQ extension development [documentation](https://docs.hivemq.com/hivemq/4.5/extensions/introduction.html).

### Features
- The extension exposes `Prometheus` metrics to track the count of inbound PUBLISH messages per topic.
- The extension exposes `Prometheus` metrics to track the count of outbound PUBLISH messages per topic.

### Installation
To install the extension, follow these steps:
1. Download the extension from the release section.
2. Copy the contents of the zip file to the `extensions` folder of your HiveMQ nodes.
3. Modify the [`config.xml`](src/hivemq-extension/conf/config.xml) files according to your needs.

### Metrics
Metrics for inbound messages follow this format: `<inbound-metric-prefix>_<topic>`.

Metrics for outbound messages follow this format: `<outbound-metric-prefix>_<topic>`.

You can customize the prefixes for inbound and outbound metrics by updating the `publish-inbound-metric-prefix` and `publish-outbound-metric-prefix` elements in the [`config.xml`](src/hivemq-extension/conf/config.xml) file. 

If not updated, the default prefixes will be used:

- *Inbound Metric Prefix*: `com.hivemq.messages.inbound.count`.
- *Outbound Metric Prefix*: `com.hivemq.messages.outbound.count`.

> **Note:**
> The topic path separation is replaced by `_` instead of `/` as well as the `.` in the prefixes.

### Configuration
The configuration includes the following settings.

Example [`config.xml`](src/hivemq-extension/conf/config.xml):
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<metrics-per-topic-extension-configuration>
    <verbose>false</verbose>
    <prefix-metrics-name>
        <publish-inbound-metric-prefix>com.hivemq.messages.inbound.count</publish-inbound-metric-prefix>
        <publish-outbound-metric-prefix>com.hivemq.messages.outbound.count</publish-outbound-metric-prefix>
    </prefix-metrics-name>
</metrics-per-topic-extension-configuration>
```

### Development

#### Unit tests
This extension includes unit tests to cover certain logic. You can find them in the `test` folder.

#### GitHub Workflows
This repository utilizes the following GitHub Actions Workflows:

- **check**: This workflow runs on each push to a feature branch or for merge requests. It runs the unit tests.
- **releaseExtension**: This workflow runs on each GitHub release of the form `major.minor.patch`. It builds the extension code base, generates a `Zip` archive, and attaches that archive to the release.

> **Note:**
> The GitHub release tag should have the same version as the specified one in [gradle.properties](gradle.properties).