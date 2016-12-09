#!/bin/bash

if [ $# = 2 ]
then
	targetFilePath="${HOME}/AndroidInven/$1/"
	destinationFilePath="${HOME}/WorkSpace/$1/"

	echo "---moving files start---"
	
	if [ $2 = "r" ]
	then
		eval "cp -rfv ${destinationFilePath} ${targetFilePath}"
	else
		eval "cp -rfv ${targetFilePath} ${destinationFilePath}"
	fi

	echo "---moving files end---"
else
	echo "---insert directory name as parameter---"
	echo "---default process---"
	echo "---AndroidInven -> WorkSpace---"
fi
