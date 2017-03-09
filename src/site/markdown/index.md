# Overview
This test suite is based on the following OGC specification:
  * Sensor Model Language Documents, version 2.0.0 [OGC 12-000](https://portal.opengeospatial.org/files/?artifact_id=55939)
  
## What is tested 
| Conformance Class Name               | Reference | Notes                                                                                                                   | Available in Test Suite | 
|--------------------------------------|-----------|-------------------------------------------------------------------------------------------------------------------------|-------------------------| 
| Core Concepts     | A.1 | | YES                     | 
| Core Abstract Process | A.2, B.1 | | YES | 
| Simple Process       | A.3, B.2  | | YES | 
| Aggregate Process    | A.4, B.3  | | YES | 
| Physical Component | A.5, B.4 | | YES | 
| Physical System | A.6, B.5 | | YES | 
| Process with Advanced Data Types | A.7 | | YES | 
 
## What is not tested 
For dependency tests like GML, SWECommon, etc, these tests will be validated by XSD only. If there is any GML(or so) problems in the XML body, this ETS might not print the detail messages to indicate why the test failed. 
 
## The test data 
* [SimpleProcess](https://raw.githubusercontent.com/opengeospatial/ets-sensorml20/master/src/test/resources/SimpleProcess.xml) 
* [PhysicalComponent](https://raw.githubusercontent.com/opengeospatial/ets-sensorml20/master/src/test/resources/PhysicalComponent.xml) 
* [PhysicalSystem](https://raw.githubusercontent.com/opengeospatial/ets-sensorml20/master/src/test/resources/PhysicalSystem.xml) 
* [AggregateProcess](https://raw.githubusercontent.com/opengeospatial/ets-sensorml20/master/src/test/resources/AggregateProcess.xml) 
* [ConfigurableProcess; config with array values](https://raw.githubusercontent.com/opengeospatial/ets-sensorml20/master/src/test/resources/SensorConfigWithArray.xml) 
 
 ## Release Notes  
Release notes are available [here.](relnotes.html) 