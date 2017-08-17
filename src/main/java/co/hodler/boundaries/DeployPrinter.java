package co.hodler.boundaries;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;

import java.math.BigInteger;

public class DeployPrinter {
  private Web3j web3j;

  public DeployPrinter(Web3j web3j) {
    this.web3j = web3j;
  }

  public Printer deployWith(Credentials credentials) throws Exception {
    BigInteger initialEther = BigInteger.valueOf(0);
    BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
    BigInteger gasLimit = web3j.ethGetBlockByNumber(latestBlock(), true)
      .send().getResult().getGasLimit();
    return Printer.deploy(web3j, credentials, gasPrice, gasLimit, initialEther)
      .get();
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
