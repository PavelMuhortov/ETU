#!/bin/bash

rm -r market-1.0.0-SNAPSHOT
./gradlew clean distZip
unzip build/distributions/market-1.0.0-SNAPSHOT
./market-1.0.0-SNAPSHOT/bin/market