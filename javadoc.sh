#!/bin/bash

source javaproj.txt

javadoc -private -cp $(cat $CP) -d $DOC -sourcepath $SRC -subpackages .
