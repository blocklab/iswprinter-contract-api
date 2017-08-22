const contractProperties = require('./../smart_contract/build/contracts/Printer')
const Web3 = require('./../smart_contract/node_modules/web3')
const web3 = new Web3(new Web3.providers.HttpProvider('http://localhost:8545'));

const printerAddress = contractProperties.networks[Object.keys(contractProperties.networks)[0]].address

var contract = new web3.eth.Contract(contractProperties.abi, printerAddress)
