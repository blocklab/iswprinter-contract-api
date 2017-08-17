package co.hodler;

public interface ContractService {

  Integer amountAllowedToPrint(String gCodeHash, String userId);

  void resetAmountAllowedToPrint(String gCodeHash, String userId);
}
