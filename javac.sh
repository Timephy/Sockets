#!/bin/bash

source javaproj.txt

cd $SRC

FILES=$1
#echo javac -cp $(cat $CPSH) -d $BIN $1
for FILE in $FILES
do
    if [ -f $CPSH ]; then
        javac -cp $(cat $CPSH) -source 1.7 -target 1.7 -d $BIN $1
    else
        javac -d $BIN $1
    fi
done