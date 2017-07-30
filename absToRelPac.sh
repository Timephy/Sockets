#!/bin/bash

echo $1 | sed 's/.*\(src\)/\1/g' | cut -d'/' -f2-
