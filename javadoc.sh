#!/bin/bash

source javaproj.txt

<<<<<<< HEAD
javadoc -private -cp $(cat $CP) -d $DOC -sourcepath $SRC -subpackages .
=======
javadoc -private -d $DOC -cp $(cat $CP) -sourcepath $SRC -subpackages .
>>>>>>> feedbfe232763d28adce70c47b1800badb48457c
