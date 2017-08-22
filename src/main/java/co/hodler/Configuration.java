package co.hodler;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@Component
@Validated
@ConfigurationProperties
public class Configuration {

  @Size(min = 42, max = 42)
  @Value("${contractAddress}")
  public String contractAddress;

  @NotEmpty
  @Value("${walletSource}")
  public String walletSource;

  @NotEmpty
  @Value("${walletPassword}")
  public String walletPassword;
}
