#!/bin/sh
echo "publish module $1"
publish="./gradlew :$1:publish"
$publish