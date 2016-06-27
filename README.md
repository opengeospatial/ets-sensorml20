## ets-sensorml20

### Scope

This test suite validates the instances of SenorML 2.0.

Visit the [project documentation website](http://opengeospatial.github.io/ets-sensorml20/)
for more information, including the API documentation.

### How to run the test
#### 1. via IDE (Eclipse, Netbeans, etc.)
Clone this project into the IDE.
Set the instance location in the config file which located at the user home folder by default:
 `${user.home}/test-run-props.xml`
You can modify the value of the element with the key "iut" to locate the testing instance, like:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties version="1.0">
  <comment>Test run arguments</comment>
  <!--Modify the instance location here-->
  <entry key="iut">https://raw.githubusercontent.com/opengeospatial/ets-sensorml20/master/src/main/example/SensorML2_1.xml</entry>
</properties>
```
After the configuring, set the starting class to run as java application:
`org.opengis.cite.sensorml20.TestNGController`

The TestNG results file (`testng-results.xml`) will be written to a subdirectory
in `${user.home}/testng/` having a UUID value as its name.
#### 2. via console
Build the source code with Maven in the shell:

`mvn package`

Execute the build artifact under the target folder:

`java -jar ets-sensorml20-0.5-SNAPSHOT-aio.jar [-o|--outputDir $TMPDIR] [test-run-props.xml]`

#### 3. OGC test harness

Use [TEAM Engine](https://github.com/opengeospatial/teamengine), the official OGC test harness.

### How to contribute

If you would like to get involved, you can:

* [Report an issue](https://github.com/opengeospatial/ets-sensorml20/issues) such as a defect or
an enhancement request
* Help to resolve an [open issue](https://github.com/opengeospatial/ets-sensorml20/issues?q=is%3Aopen)
* Fix a bug: Fork the repository, apply the fix, and create a pull request
* Add new tests: Fork the repository, implement (and verify) the tests on a new topic branch,
and create a pull request
