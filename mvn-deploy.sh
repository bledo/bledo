#!/bin/sh

p=$1

if [ "$p" = "" ]
then
	echo ""
	echo "Path to cloned copy of https://github.com/bledo/maven2"
	echo ""
	exit
fi

rp=`readlink -f $p`

cmd="mvn deploy -DaltDeploymentRepository=mine::default::file://$rp"
$cmd

