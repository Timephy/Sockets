#!/bin/bash

source javaproj.txt

javadoc -private -d $DOC -cp $(cat $CP) -sourcepath $SRC -subpackages .
