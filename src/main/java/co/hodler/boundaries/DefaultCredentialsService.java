package co.hodler.boundaries;

import co.hodler.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;

@Service
public class DefaultCredentialsService implements CredentialsService {
  @Autowired
  Configuration config;

  @Override
  public Credentials loadCredentials() {
    try {
      return WalletUtils.loadCredentials(config.walletPassword, config
        .walletSource);
    } catch (IOException | CipherException e) {
      throw new RuntimeException(e);
    }
  }
}
