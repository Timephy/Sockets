#!/bin/sh

#./javacAll.sh

source javaproj.txt

JARNAME=LifeVsLife

cd $BIN

jar cfm $JAR$JARNAME.jar $PROJ/manifest.txt *
cd $RES
jar uf $JAR$JARNAME.jar *
cd $LIB
jar uf $JAR$JARNAME.jar *

#mv $JARNAME.jar $JAR$JARNAME.jar
