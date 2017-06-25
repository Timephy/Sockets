#!/bin/bash

source javaproj.txt

cd $BIN

#echo $CPSH
if [ -f $CPSH ]; then
    echo java -cp $(cat $CPSH) $1
    java -cp $(cat $CPSH) $1
else
    java $1
fi
