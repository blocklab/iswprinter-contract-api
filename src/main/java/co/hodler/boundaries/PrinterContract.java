package co.hodler.boundaries;

import org.web3j.crypto.Credentials;

public interface PrinterContract {
  Printer get(Credentials credentials);
}
