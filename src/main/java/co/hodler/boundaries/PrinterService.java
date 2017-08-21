package co.hodler.boundaries;

import co.hodler.model.PrintableId;
import co.hodler.model.UserId;

import java.math.BigInteger;

public interface PrinterService {
  BigInteger checkAmountAllowedToPrint(PrintableId printableId,
                                       UserId userId);

  void resetPrints(PrintableId printableId, UserId userId);
}
