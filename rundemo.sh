#!/bin/sh
#
# Start and scale the number of kafka servers

sbt docker:publishLocal

docker-compose up -d
