package co.hodler.boundaries;

import co.hodler.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DefaultCredentialsService implements CredentialsService {
  @Autowired
  Configuration config;

  @Override
  public Credentials loadCredentials() {
    try {
      Files.write(Paths.get("wallet.tmp"), config.walletSource.getBytes());
      return WalletUtils.loadCredentials(config.walletPassword, new File
        ("wallet.tmp"));
    } catch (IOException | CipherException e) {
      throw new RuntimeException(e);
    }
  }
}
