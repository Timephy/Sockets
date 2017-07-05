#!/bin/sh

#./javacAll.sh

source javaproj.txt

JARNAME=TNet

cd $BIN

jar cfm $JAR$JARNAME.jar manifest.txt tnet/


cd $LIB
jar uf $JAR$JARNAME.jar *

#mv $JARNAME.jar $JAR$JARNAME.jar
