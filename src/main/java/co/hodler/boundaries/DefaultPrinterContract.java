package co.hodler.boundaries;

import org.springframework.stereotype.Service;

@Service
public class DefaultPrinterContract implements PrinterContract {

  @Override
  public Printer get() {
    return null;
  }
}
