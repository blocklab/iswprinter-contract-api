#!/bin/sh

set -euo pipefail

mvn package -DskipTests

(cd smart_contract && rm -rf build && npm run deploy)

CONTRACT_ADDRESS=`node scripts/grab_address`
WALLET_PASSWORD=axel
WALLET_FILE=wallets/testing.json
SPRING_APPLICATION_JSON='{"contractAddress":"'$CONTRACT_ADDRESS'", "walletSource":"'$WALLET_FILE'", "walletPassword":"'$WALLET_PASSWORD'"}' java -jar target/3dprintman-0.1.0.jar
