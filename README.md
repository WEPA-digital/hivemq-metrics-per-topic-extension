[//]: # (REVIEW a link to where hivemq extensions are documented)

## Hivemq-metrics-per-topic-extension

### Purpose
This extension serves the purpose of exposing `prometheus` metrics that count the number of inbound and outbound messages per topic.

### Features
- Exposes `prometheus` metrics for counting the number of inbound PUBLISH messages per topic.
- Exposes `prometheus` metrics for counting the number of outbound PUBLISH messages per topic.

### Installation
To install the extension, follow these steps:
1. Download the extension from the release section.
2. Copy the contents of the zip file to the `extensions` folder of your HiveMQ nodes.
3. Modify the `config.xml` files according to your needs.

### Metrics
[//]: # (REVIEW update this with the link to the xml config where one can set the prefix)
The exposed metrics for inbound messages are in the following format: `eu_wepa_hivemq_message_incoming_count_<topic>`.
The exposed metrics for outbound messages are in the following format: `eu_wepa_hivemq_message_outgoing_count_<topic>`.

> **Note:**
> The topic path separation is replaced by `_` instead of `/`.

### Configuration
The configuration includes the following settings.

Example `config.xml`:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<metrics-per-topic-extension-configuration>
    <verbose>false</verbose>
</metrics-per-topic-extension-configuration>
```

### Development

#### Unit tests
This extension includes unit tests to cover certain logic. You can find them in the `test` folder.

#### GitHub Workflows
This repository utilizes the following GitHub Actions Workflows:

- **check**: This workflow runs on each push to a feature branch or for merge requests. It performs code linting using pylint and runs the unit tests.
- **releaseExtension**: This workflow runs on each GitHub release of the form `major.minor.patch`. It builds the extension code base, generates a `Zip` archive, and attaches that archive to the release.

> **Note:**
> The GitHub release tag should have the same version as specified in [gradle.properties](gradle.properties).