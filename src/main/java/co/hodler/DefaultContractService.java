package co.hodler;

import org.springframework.stereotype.Service;

@Service
public class DefaultContractService implements ContractService {

  @Override
  public Integer amountAllowedToPrint(String gCodeHash, String userId) {
    return 1;
  }

  @Override
  public void resetAmountAllowedToPrint(String gCodeHash, String userId) {
    System.out.println(gCodeHash + userId);
  }
}
