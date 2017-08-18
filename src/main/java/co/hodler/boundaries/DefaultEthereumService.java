package co.hodler.boundaries;

import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

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

  @Override
  public BigInteger currentGasPrice() {
    try {
      return this.web3.ethGasPrice().send().getGasPrice();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public BigInteger currentGasLimit() {
    try {
      return web3.ethGetBlockByNumber(latestBlock(), true)
        .send().getResult().getGasLimit();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Web3j getWeb3() {
    return this.web3;
  }

  private byte[] hexStringToByteArray(String hexString) {
    int len = hexString.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
        + Character.digit(hexString.charAt(i + 1), 16));
    }
    return data;
  }

  private DefaultBlockParameter latestBlock() {
    return new DefaultBlockParameter() {
      @Override
      public String getValue() {
        return "latest";
      }
    };
  }
}
