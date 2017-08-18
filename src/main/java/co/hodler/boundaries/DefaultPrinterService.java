package co.hodler.boundaries;

import co.hodler.model.PrintableId;
import co.hodler.model.UserId;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Bytes32;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class DefaultPrinterService {
  private final Printer printer;

  public DefaultPrinterService(Printer printer) {
    this.printer = printer;
  }

  public BigInteger checkAmountAllowedToPrint(PrintableId printableId,
                                              UserId userId) {
    try {
      return printer.timesUserIsAllowedToPrint(new Bytes32(printableId
          .asByteArray())
        , new Address(userId.asString())).get().getValue();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
