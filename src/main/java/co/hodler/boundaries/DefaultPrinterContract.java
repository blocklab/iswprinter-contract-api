package co.hodler.boundaries;

import co.hodler.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultPrinterContract implements PrinterContract {

  private final EthereumService ethereumService;
  private final Configuration configuration;
  private final CredentialsService credentialsService;

  @Autowired
  public DefaultPrinterContract(EthereumService ethereumService,
                                Configuration configuration,
                                CredentialsService credentialsService) {
    this.ethereumService = ethereumService;
    this.configuration = configuration;
    this.credentialsService = credentialsService;
  }

  @Override
  public Printer get() {
    return Printer.load(configuration.contractAddress, ethereumService
        .getWeb3(), credentialsService.loadCredentials(),
      ethereumService.currentGasPrice(), ethereumService.currentGasLimit());
  }
}
