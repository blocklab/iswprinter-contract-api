package co.hodler.boundaries;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;

public interface EthereumService {
  Bytes32 keccak256(String content);

  BigInteger currentGasPrice();

  BigInteger currentGasLimit();

  Web3j getWeb3();
}
