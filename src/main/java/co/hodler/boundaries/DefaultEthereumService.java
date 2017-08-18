package co.hodler.boundaries;

import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

@Service
public class DefaultEthereumService implements EthereumService {

  private final Web3j web3;

  public DefaultEthereumService() {
    this.web3 = Web3j.build(new HttpService());
  }

  @Override
  public Bytes32 keccak256(String content) {
    try {
      return new Bytes32(hexStringToByteArray(web3.web3Sha3(content).send()
        .getResult().replaceFirst("0x", "")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] hexStringToByteArray(String hexString) {
    int len = hexString.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
        + Character.digit(hexString.charAt(i+1), 16));
    }
    return data;
  }
}
