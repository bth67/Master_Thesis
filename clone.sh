#!/bin/bash

cat $1 | while read line
do
    git clone $line
done
