package co.hodler;

import co.hodler.boundaries.*;
import co.hodler.model.PrintableId;
import co.hodler.model.UserId;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrinterIntegrationTest {
  private String BLOCKCHAIN_URL = "http://localhost:8545";
  private Credentials credentials;
  private static Process testRpcProcess;
  private String contractAddress;
  private boolean initialized = false;

  @Autowired
  EthereumService ethereumService;
  @Autowired
  Configuration configuration;
  @Autowired
  PrinterContract contract;
  @Autowired
  TestRestTemplate restTemplate;
  @Autowired
  CredentialsService credentialsService;
  @Autowired
  DefaultPrinterService printerService;

  private PrintableId printableId;

  @Before
  public void initialize_contract() throws Exception {
    if (!initialized) {
      testRpcProcess = new ProcessBuilder("testrpc").start();
      Thread.sleep(2000);
      credentials = credentialsService.loadCredentials();

      send100EtherToWeb3CreatedAccount(credentials, extractFirstAccount());

      DeployPrinter deployPrinter = new DeployPrinter(new
        DefaultEthereumService());
      contractAddress = deployPrinter.deployWith(credentials)
        .getContractAddress();
      printableId = new PrintableId("some-gcode");
      configuration.contractAddress = contractAddress;
      initialized = true;
    }
  }

  @AfterClass
  public static void kill_testrpc() throws Exception {
    testRpcProcess.destroy();
    Files.delete(Paths.get("wallet.tmp"));
  }

  @Test
  public void is_able_to_buy_a_print_job() throws Exception {
    Bytes32 firstDeliverableHash = ethereumService.keccak256(printableId
      .asString());

    contract.get().buyRightToPrintOnce(firstDeliverableHash,
      BigInteger
        .valueOf
          (10000))
      .get();

    UserId userId = new UserId(credentials.getAddress());
    Integer response = this.restTemplate.getForObject
      (String.format("/printables/%s/%s", printableId.asString(), userId.asString()),
        Integer.class);
    assertThat(response, is(1));
  }

  @Test
  public void is_able_to_reset_print_amount() throws Exception {
    UserId userId = new UserId(credentials.getAddress());

    printerService.resetPrints(printableId, userId);

    BigInteger result = printerService.checkAmountAllowedToPrint(printableId,
      userId);
    assertThat(result, is(BigInteger.valueOf(0)));
  }

  private void send100EtherToWeb3CreatedAccount(Credentials
                                                  credentials, String
                                                  firstAccount) throws
    Exception {
    JSONObject sendTxRpcPayload = new JSONObject();
    sendTxRpcPayload.put("jsonrpc", "2.0");
    sendTxRpcPayload.put("id", 1);
    sendTxRpcPayload.put("method", "eth_sendTransaction");
    JSONObject parameterContents = new JSONObject();
    JSONArray parameters = new JSONArray();
    parameters.put(parameterContents);
    parameterContents.put("value", new BigInteger("1000000000000000000"));
    parameterContents.put("to", credentials.getAddress());
    parameterContents.put("from", firstAccount);
    sendTxRpcPayload.put("params", parameters);
    Unirest.post(BLOCKCHAIN_URL).body(sendTxRpcPayload).asJson();
  }

  private String extractFirstAccount() throws Exception {
    JSONObject ethAccountsRpcPayload = new JSONObject();
    ethAccountsRpcPayload.put("jsonrpc", "2.0");
    ethAccountsRpcPayload.put("method", "eth_accounts");
    ethAccountsRpcPayload.put("id", 1);
    JsonNode responseBody = Unirest.post(BLOCKCHAIN_URL).body
      (ethAccountsRpcPayload).asJson().getBody();
    JsonNode ethAccounts = new JsonNode(responseBody.getObject().get
      ("result").toString());
    return (String) ethAccounts.getArray().get(0);
  }

}
