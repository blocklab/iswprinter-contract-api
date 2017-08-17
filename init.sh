#!/bin/sh

set -euo pipefail

git submodule init && git submodule update && cd smart_contract && npm i
