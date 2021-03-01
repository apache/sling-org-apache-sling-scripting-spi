[<img src="https://sling.apache.org/res/logos/sling.png"/>](https://sling.apache.org)

  [![Build Status](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-scripting-spi/job/master/badge/icon)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-scripting-spi/job/master/) [![Test Status](https://img.shields.io/jenkins/tests.svg?jobUrl=https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-scripting-spi/job/master/)](https://ci-builds.apache.org/job/Sling/job/modules/job/sling-org-apache-sling-scripting-spi/job/master/test/?width=800&height=600) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-scripting-spi&metric=coverage)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-scripting-spi) [![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=apache_sling-org-apache-sling-scripting-spi&metric=alert_status)](https://sonarcloud.io/dashboard?id=apache_sling-org-apache-sling-scripting-spi) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![scripting](https://sling.apache.org/badges/group-scripting.svg)](https://github.com/apache/sling-aggregator/blob/master/docs/groups/scripting.md)

# Apache Sling Scripting SPI

This module is part of the [Apache Sling](https://sling.apache.org) project.

The bundle currently provides the `org.apache.sling.scripting.spi.bundle` SPI, which allows the
[Apache Sling Servlets Resolver](https://github.com/apache/sling-org-apache-sling-servlets-resolver) and the
[Apache Sling Scripting Core](https://github.com/apache/sling-org-apache-sling-scripting-core) to wire up and
execute bundled scripts (precompiled or not) in order to render HTTP requests.

## Integration Tests

The integration tests for bundled scripts are provided by the [`org.apache.sling.scripting.bundle.tracker.it`](https://github.com/apache/sling-org-apache-sling-scripting-bundle-tracker-it) project.
