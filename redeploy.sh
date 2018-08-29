#!/bin/bash

cd /home/WeChatConnector/tomcat-WeChatConnector/deploy/WeChatConnector/
git pull
mvn package
/home/WeChatConnector/tomcat-WeChatConnector/bin/shutdown.sh
cp target/WeChatConnector.war /home/WeChatConnector/tomcat-WeChatConnector/webapps/
rm -rf /home/WeChatConnector/tomcat-WeChatConnector/webapps/WeChatConnector
cd /home/WeChatConnector/tomcat-WeChatConnector/bin
./startup.sh

