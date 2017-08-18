package co.hodler.boundaries;

import co.hodler.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

@Service
public class DefaultPrinterContract implements PrinterContract {

  private final EthereumService ethereumService;
  private final Configuration configuration;

  @Autowired
  public DefaultPrinterContract(EthereumService ethereumService,
                                Configuration configuration) {
    this.ethereumService = ethereumService;
    this.configuration = configuration;
  }

  @Override
  public Printer get(Credentials credentials) {
    return Printer.load(configuration.contractAddress, ethereumService
        .getWeb3(), credentials,
      ethereumService.currentGasPrice(), ethereumService.currentGasLimit());
  }
}
