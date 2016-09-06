#!/bin/bash

# this function is taken from linuxjournal
function valid_ip()
{
    local  ip=$1
    local  stat=1

    if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
        OIFS=$IFS
        IFS='.'
        ip=($ip)
        IFS=$OIFS
        [[ ${ip[0]} -le 255 && ${ip[1]} -le 255 \
            && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
        stat=$?
    fi
    return $stat
}

# own code
echo $1

if ! valid_ip $1 ; then
	echo 'Invalid IP!';
	exit 2;
fi

sed -i "2s/.*/url = jdbc:postgresql:\/\/$1:5432\/robot/g" robot-db/src/main/resources/database.properties

mvn clean package

java -jar robot-logic/target/dependency/webapp-runner.jar robot-logic/target/robot-logic-1.0-SNAPSHOT.war & java -jar robot-service/target/dependency/webapp-runner.jar --port 8090 robot-service/target/robot-service-1.0.war 
