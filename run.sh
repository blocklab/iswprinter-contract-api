#!/bin/sh

set -euo pipefail

NAME="foo"
SPRING_APPLICATION_JSON='{"name":"'$NAME'"}' java -jar target/3dprintman-0.1.0.jar
