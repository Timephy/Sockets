#!/bin/bash

source javaproj.txt

./rmAllClass.sh

#echo $SRC

cd $SRC

function javacDir {
    
    for javaFile in $(ls $1*.java 2>/dev/null)
    do
        if [ -f $CPSH ]; then
            #echo WHERE: $(pwd)
	    #echo  CMD: javac -d $BIN $javaFile
            javac -cp $(cat $CPSH) -d $BIN $javaFile
        else
            javac -d $BIN $javaFile
        fi
    done

    for dir in $(ls -d $1*/ 2>/dev/null)
    do
	#echo $dir
        javacDir $dir
    done
}
javacDir
