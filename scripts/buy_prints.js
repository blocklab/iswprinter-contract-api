const contractProperties = require('./../smart_contract/build/contracts/Printer')
const Web3 = require('./../smart_contract/node_modules/web3')
const web3 = new Web3(new Web3.providers.HttpProvider('http://localhost:8545'));

const printerAddress = contractProperties.networks[Object.keys(contractProperties.networks)[0]].address

const contract = new web3.eth.Contract(contractProperties.abi, printerAddress)

const gCodeHash = web3.utils.sha3('somegcode')

async function run() {
  const accounts = await web3.eth.getAccounts()
  await contract.methods.buyRightToPrintOnce(gCodeHash).send({
    from: accounts[1],
    value: 10000
  })
  console.log('Bought ', gCodeHash, ' using Account: ' + accounts[1])
}

run()
