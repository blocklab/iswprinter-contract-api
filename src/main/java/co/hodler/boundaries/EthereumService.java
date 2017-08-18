package co.hodler.boundaries;

import org.web3j.abi.datatypes.generated.Bytes32;

public interface EthereumService {
  Bytes32 keccak256(String content);
}
