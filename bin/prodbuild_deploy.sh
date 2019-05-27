#!/bin/sh
cd ~/code/ami/note
mvn -DskipTests clean package
echo "Build is complete not moving the file to server."

scp  /Users/phamal/code/ami/note/target/kanchi-0.0.1-SNAPSHOT.jar root@47.254.91.56:/root/jars

# Once deployment is complete move the jar to server. 
