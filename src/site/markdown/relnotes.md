# SensorML 2.0 Release Notes

## 1.1 (2025-01-27)

Attention: Java 17 and Tomcat 10.1 are required.

- [#62](https://github.com/opengeospatial/ets-sensorml20/issues/62) Migrate test suite to TEAM Engine 6 (Java 17)

## 1.0 (2021-05-11)

- [#60](https://github.com/opengeospatial/ets-sensorml20/issues/60) Prepare Production release
- [#59](https://github.com/opengeospatial/ets-sensorml20/pull/59) Set Docker TEAM Engine version to 5.4.1

## 0.10 (2021-01-28)

- [#58](https://github.com/opengeospatial/ets-sensorml20/pull/58) Annotated descriptions with Annex A and B numbers
- [#54](https://github.com/opengeospatial/ets-sensorml20/issues/54) Convert xml.xsd file encoding from UTF-16 to UTF-8
- [#50](https://github.com/opengeospatial/ets-sensorml20/issues/50) Cleanup dependencies
- [#52](https://github.com/opengeospatial/ets-sensorml20/issues/52) Add template to get an XML/JSON response via rest endpoint
- [#56](https://github.com/opengeospatial/ets-sensorml20/pull/56) Bump xercesImpl from 2.11.0 to 2.12.1

## 0.9
- [#38](https://github.com/opengeospatial/ets-sensorml20/issues/38) Create unit tests for each test
- [#39](https://github.com/opengeospatial/ets-sensorml20/issues/39) Introduce Dockerfile and Maven Docker plugin
- [#41](https://github.com/opengeospatial/ets-sensorml20/issues/41) Minor typo in AssertionError message of AggregateProcessSchema.PropertyConnectionrestrictions()
- [#42](https://github.com/opengeospatial/ets-sensorml20/issues/42) Minor typos in PhysicalComponent.ByPointOrLocation() method
- [#43](https://github.com/opengeospatial/ets-sensorml20/issues/43) Remove PhysicalSystem from Line 71 of CoreConcepts class
- [#43](https://github.com/opengeospatial/ets-sensorml20/issues/43) Remove PhysicalSystem from Line 71 of CoreConcepts class
- [#44](https://github.com/opengeospatial/ets-sensorml20/issues/44) Replace item.getNodeValue() with item.getTextContent() on Line 107 of CoreConcepts
- [#45](https://github.com/opengeospatial/ets-sensorml20/issues/45) Revise the test for Requirement 4 to be consistent with Core Metadata listed in Section 7.2.2
- [#46](https://github.com/opengeospatial/ets-sensorml20/issues/46) Req 49 test cannot be run automatically
- [#47](https://github.com/opengeospatial/ets-sensorml20/issues/47) Req 54 added in commit #69b1328
- [SecurityConstraints example added](https://github.com/opengeospatial/ets-sensorml20/blob/master/src/test/resources/OEM_Sensor_SecurityConstraints.xml)

## 0.8
- [#35](https://github.com/opengeospatial/ets-sensorml20/issues/35) Version displayed on start page of beta environment is not correct
- [#36](https://github.com/opengeospatial/ets-sensorml20/issues/36) Improve name of test suite
## 0.7
- [#33](https://github.com/opengeospatial/ets-sensorml20/issues/33) Add conformance class configuration into the sensorml20 test. 

## 0.6 
- [#30](https://github.com/opengeospatial/ets-sensorml20/issues/30) typeOf elements should accept relative URL as its source reference. 
- [#31](https://github.com/opengeospatial/ets-sensorml20/issues/31) ConfigurableProcess might lose some base elements while using typeOf reference.   
- [#32](https://github.com/opengeospatial/ets-sensorml20/issues/32) requirement 45 - schematron validation always fail on core concepts 

## 0.5
- Fix Req 4 by removing this reqirement from the test group of CoreConcepts. The qualification metadata should not fail the entire test suite. 
- Fix Req 75, 76 for position issues.
- Fix namespace issues in schematron files.

## 0.4
- [#22](https://github.com/opengeospatial/ets-sensorml20/issues/22) Process with Advanced Data Types. Req 36 ~ 37
- [#21](https://github.com/opengeospatial/ets-sensorml20/issues/21) Physical System: Req 33~35
- [#20](https://github.com/opengeospatial/ets-sensorml20/issues/20) Physical Component. Req 26 ~ 32
- [#19](https://github.com/opengeospatial/ets-sensorml20/issues/19) Conformance class - Aggregate Process. Req 22~25
- [#17](https://github.com/opengeospatial/ets-sensorml20/issues/17) Conformance classes - A.14 - XML/Configurable Process minimum requirement
- [#15](https://github.com/opengeospatial/ets-sensorml20/issues/15) Conformance classes - A.12 - XML/Physical Component minimum requirement
- [#13](https://github.com/opengeospatial/ets-sensorml20/issues/13) Conformance classes - A.10 - XML/Simple Process minimum requirement
- [#12](https://github.com/opengeospatial/ets-sensorml20/issues/12) Conformance classes - A.9 - XML/Core Process minimum requirement
- [#11](https://github.com/opengeospatial/ets-sensorml20/issues/11) Conformance classes - A.8 - Model/Configurable Process minimum requirement
- [#10](https://github.com/opengeospatial/ets-sensorml20/issues/10) Conformance classes - A.7 - Model/Processes with Advanced Data Types minimum requirement
- [#9](https://github.com/opengeospatial/ets-sensorml20/issues/9) Conformance classes - A.6 - Model/Physical System minimum requirement
- [#8](https://github.com/opengeospatial/ets-sensorml20/issues/8) Conformance classes - A.5 - Model/Physical Component minimum requirement
- [#7](https://github.com/opengeospatial/ets-sensorml20/issues/7) Conformance classes - A.4 - Model/Aggregate Process minimum requirement
- [#6](https://github.com/opengeospatial/ets-sensorml20/issues/6) Conformance classes - A.3 - Model/Simple Process minimum requirement
- [#5](https://github.com/opengeospatial/ets-sensorml20/issues/5) Conformance classes - A.2 - Abstract Process minimum requirement
- [#4](https://github.com/opengeospatial/ets-sensorml20/issues/4) Conformance classes - A.1 - Core Concepts minimum requirement

## 0.3

- Update pom.xml to build with Maven 2 and minor updates in index.md

## 0.2

- [#2](https://github.com/opengeospatial/ets-sensorml20/issues/2)Clean the structure of the test. The test scripts (ctl) have not changed. Starting this revision, the revision number will follow the conventions described in the [wiki](https://github.com/opengeospatial/cite/wiki/OGC-Compliance-Testing-Tools).

## r1

  * Add conformance Tests. (Annex A and Annex B)

## r0

  * First release - basic test against schema.
