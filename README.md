# Platform Application

The Platform application outputs metrics of a service application and availability through a series of API calls. It takes JSON files to display general information and usage statistics. Developed according to the specifications in [Research Platform Support for the CANARIE Registry and Monitoring System](https://www.canarie.ca/about-us/documents/?wpdmc=research-software) using Java and Spring Framework and deployed in Tomcat.

## Installation and Configuration Guide

### Pre-requisites
1. git 
2. Tomcat version 8.5
3. maven

### Set-up root directory
The Platform root directory contains the two necessary JSON files that the application reads to display metrics data.

### Create configuration file.
Create ``.properties`` file that contains references to the application root directory. Optionally, URLs of external websites can be added for redirection purposes.
```
#
# Platform Application root
# 
platform.root.dir=/path/to/chosen/root/directory/
#
# Page View URL Redirects
#
platform.factsheet.url=
platform.documentation.url=
platform.licence.url=
platform.provenance.url=
platform.releasenotes.url=
platform.source.url=
platform.support.url=
platform.tryme.url=
```

### Set-up environment variables
Indicate in Java environment variables the location of the configuration file.

```
JAVA_OPTS="${JAVA_OPTS} -Dplatform.config.file=file:/path/to/config/file"
```

### Create JSON files

##### General Information
Format ``info.json`` as nested JSON objects with ``name`` and ``value`` as keys in the inner JSON object. File must be located in the Platform root directory. 
```
{
  "name": {
    "name": "Name",
    "value": "<name of service>"
  },
  "synopsis": {
    "name": "Synopsis",
    "value": "<service synopsis>"
  },
  "version": {
    "name": "Version",
    "value": "<service version>"
  },
  "institution": {
    "name": "Institution",
    "value": "<name of institution involved>"
  },
  "releaseTime": {
    "name": "Release Time",
    "value": "<timestamp ISO 8601>"
  },
  "researchSubject": {
    "name": "Research Subject",
    "value": "<research subject according to NSERC>"
  },
  "supportEmail": {
    "name": "Support Email",
    "value": "<e-mail string>"
  },
  "tags": {
    "name": "Tags",
    "value": "<comma separated tags>"
  }
}
```

##### Usage Statistics
Format ``stats.json`` as nested JSON objects with ``name`` and ``value`` as keys in the nested JSON object. File must be located in the Platform root directory. A separate script populates the contents of this file.
```
{
  "<usageType>": {
    "name": "<Usage Type Name>",
    "value": <usage type value>
  },
  "lastReset": {
    "name": "Last reset",
    "value": "<timestamp ISO 8601>"
  },
  "additionalStatistic": {
    "name": "<Additional statistic name>",
    "value": <additional statistic value>
  }
}
```

### Build and deploy
```
mvn clean install
```
## API calls

##### HTTP GET /platform/info
* Purpose: Return general information about the service application.
* Response Code: 200 (on success)
* Response Body: JSON if ``Accept`` header is ``application/json`` (similar to below), HTML otherwise.
* Content Type: ``application/json`` if ``Accept`` header is ``application/json`` (similar to below), ``text/html`` otherwise.
```
{
    "name": "DuraCloud North",
    "synopsis": "DuraCloud Canada: Linking Data Repositories to Preservation Storage",
    "version": "6.2.0",
    "institution": "University of Toronto Libraries, Scholars Portal",
    "releaseTime": "2020-04-01T00:00:00Z",
    "researchSubject": "Information Technology",
    "supportEmail": "duracloud@scholarsportal.info",
    "tags": [
        "cloud storage",
        "preservation storage",
        "research data preservation"
    ]
}
```
##### HTTP GET /platform/stats
* Purpose: Return usage statistics on the service application.
* Response Code: 200 (on success)
* Response Body: JSON if ``Accept`` header is ``application/json`` (similar to below), HTML otherwise.
* Content Type: ``application/json`` if ``Accept`` header is ``application/json`` (similar to below), ``text/html`` otherwise.
```
{
    "filesPreserved": 119,
    "lastReset": "2020-03-23T21:33:16Z",
    "totalUsers": 3,
    "totalData": "4.01GB"
}
```

##### HTTP GET /platform/documentation
* Purpose: Return service application documentation.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/releasenotes
* Purpose: Return service application release notes.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/support
* Purpose: Return service application support information.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/source
* Purpose: Return service application source code.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/tryme
* Purpose: Return service application trial instance.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/licence
* Purpose: Return service application licencing information.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/provenance
* Purpose: Return service application provenance.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

##### HTTP GET /platform/factsheet
* Purpose: Return service application factsheet.
* Response Code: 200 (on success)
* Response Body: HTML
* Content Type: ``text/html``

