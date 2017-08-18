package co.hodler.boundaries;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class DeployPrinter {

  private final EthereumService ethereumService;

  @Autowired
  public DeployPrinter(EthereumService ethereumService) {
    this.ethereumService = ethereumService;
  }

  public Printer deployWith(Credentials credentials) throws Exception {
    return Printer.deploy(ethereumService.getWeb3(), credentials,
      ethereumService.currentGasPrice(),
      ethereumService.currentGasLimit(),
      BigInteger
        .valueOf(0))
      .get();
  }

}
