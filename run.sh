#!/bin/bash

# Run from project root

#RUN='java -cp ".:./lib/library.jar:/usr/share/java/commons-cli-1.2.jar"';
#$RUN cs475.Classify

java -cp ".:./lib/libarary.jar:/usr/share/java/commons-cli-1.2.jar" $@
