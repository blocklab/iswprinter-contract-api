package co.hodler.boundaries;

import co.hodler.model.PrintableId;
import co.hodler.model.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Service
public class DefaultPrinterService implements PrinterService {
  private final PrinterContract printerContract;
  private final EthereumService ethereumService;

  @Autowired
  public DefaultPrinterService(PrinterContract printerContract, EthereumService
    ethereumService) {
    this.printerContract = printerContract;
    this.ethereumService = ethereumService;
  }

  @Override
  public BigInteger checkAmountAllowedToPrint(PrintableId printableId,
                                              UserId userId) {
    try {
      return printerContract.get().timesUserIsAllowedToPrint(ethereumService
        .keccak256
        (printableId.asString()), new Address(userId.asString())).get()
        .getValue();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void resetPrints(PrintableId printableId, UserId userId) {
    try {
      printerContract.get().resetPrints(ethereumService.keccak256(printableId
          .asString()),
        new Address
          (userId.asString()))
        .get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
