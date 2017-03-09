# SensorML 2.0 Release Notes

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
