package co.hodler.boundaries;

import co.hodler.model.PrintableId;
import co.hodler.model.UserId;
import org.web3j.abi.datatypes.Address;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class DefaultPrinterService {
  private final Printer printer;
  private final EthereumService ethereumService;

  public DefaultPrinterService(Printer printer, EthereumService ethereumService) {
    this.ethereumService = ethereumService;
    this.printer = printer;
  }

  public BigInteger checkAmountAllowedToPrint(PrintableId printableId,
                                              UserId userId) {
    try {
      return printer.timesUserIsAllowedToPrint(ethereumService.keccak256
        (printableId.asString()), new Address(userId.asString())).get()
        .getValue();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  public void resetPrints(PrintableId printableId, UserId userId) {
    try {
      printer.resetPrints(ethereumService.keccak256(printableId.asString()),
        new Address
          (userId.asString()))
        .get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
