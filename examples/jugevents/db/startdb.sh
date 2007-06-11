#!/bin/sh

java -cp ./hsqldb-1.8.0.4.jar org.hsqldb.Server -database.0 file:jugevents -dbname.0 jugevents
