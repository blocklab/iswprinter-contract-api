#!/bin/sh

set -eou pipefail

COMPILED_CONTRACT_DIR=compiled_contract
CONTRACT_NAME=Printer

command -v web3j >/dev/null 2>&1 || { echo >&2 "Please install web3j."; exit 1; }

(
  rm -rf $COMPILED_CONTRACT_DIR
  cd smart_contract/contracts/ # specifying full path in cmd below did not work
  ./../node_modules/.bin/solcjs $CONTRACT_NAME.sol --output-dir \
  ../../$COMPILED_CONTRACT_DIR/ --bin --abi --optimize
)

(
  cd $COMPILED_CONTRACT_DIR
  mv *.abi $CONTRACT_NAME.abi
  mv *.bin $CONTRACT_NAME.bin
)

web3j solidity generate $COMPILED_CONTRACT_DIR/$CONTRACT_NAME.bin \
$COMPILED_CONTRACT_DIR/$CONTRACT_NAME.abi -o src/main/java -p co.hodler.boundaries
