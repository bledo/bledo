#!/bin/sh

p=$1

if [ "$p" = "" ]
then
	echo ""
	echo "Path to cloned copy of https://github.com/bledo/maven2"
	echo "$0 ../path/to/dir"
	exit
fi

rp=`readlink -f $p`

cmd="mvn deploy -DaltDeploymentRepository=mine::default::file://$rp"
$cmd

