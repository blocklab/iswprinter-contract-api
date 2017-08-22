const printer = require('./../smart_contract/build/contracts/Printer.json')
const networkId = Object.keys(printer.networks)[0]
const verificationAddress = printer.networks[networkId].address
console.log(verificationAddress)
