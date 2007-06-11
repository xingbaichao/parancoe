#!/bin/sh

mvn archetype:create -DarchetypeGroupId=org.parancoe \     
    -DarchetypeArtifactId=parancoe-webarchetype \
    -DarchetypeVersion=1.0-SNAPSHOT \
    -DgroupId=com.mycompany -DartifactId=minidemodao
