(function() {
  'use strict'

  let localWeb3;
  let abi = [{"constant":false,"inputs":[{"name":"gCodeHash","type":"bytes32"},{"name":"user","type":"address"}],"name":"resetPrints","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"gCodeHash","type":"bytes32"},{"name":"user","type":"address"}],"name":"timesUserIsAllowedToPrint","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"owner","outputs":[{"name":"","type":"address"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"gCodeHash","type":"bytes32"}],"name":"buyRightToPrintOnce","outputs":[],"payable":true,"type":"function"},{"inputs":[],"payable":false,"type":"constructor"}];
  let contractAddressDefault = '0x876158be9b71d55833ecd7b1fc896bafea2133a4';

  var metaMaskIsInstalled = () => {
    return typeof web3 !== 'undefined'
  }

  document.getElementById('buy-print').addEventListener('click', () => {
  	var contractAddress = document.getElementById('ctraddr').value;
    if (!contractAddress) {
    	contractAddress = contractAddressDefault;
    }
    var contract = localWeb3.eth.contract(abi).at(contractAddress);
    var printid = document.getElementById('printid').value;
    contract.buyRightToPrintOnce(localWeb3.sha3(printid), {
      value: 10000,
      from: localWeb3.eth.coinbase
    }, (err, succ) => {
    	console.log(err, succ);
		if (succ) {
			document.getElementById('status').innerHTML = 'Successfully bought print';
		}
    })
  });

  window.onload = () => {
    if (!metaMaskIsInstalled()) {
      document.getElementById('status').innerHTML =
        'Your browser does not support the app. Please install MetaMask'
    } else {
    	localWeb3 = new Web3(web3.currentProvider);
    }
  }
})()