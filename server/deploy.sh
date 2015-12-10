#!/bin/bash
client="client"
archiveToDeploy=`find /root -name "*.zip" | sort -r | head -1`
echo "Archive $archiveToDeploy will be deployed..."
unzip $archiveToDeploy
jarToDeploy=`find /root -name "*.jar" | sort -r | head -1 | basename`
echo "Jar $jarToDeploy will be installed..."

cp -rpf $jarToDeploy /srv/$jarToDeploy
ln -sf /srv/$jarToDeploy /srv/chalets-current.jar

#rm -rF /root/*
systemctl restart chalets