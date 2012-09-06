#!/bin/sh

DSTDIR=target
JARFILE=beanlogd-0.1.0-jar-with-dependencies.jar

if [ ! -f ${JARFILE} ];
then
	echo "System has not been built. Installing..."
	mvn package
	mv ${DSTDIR}/${JARFILE} .
fi

java -jar ${JARFILE} $1 $2
