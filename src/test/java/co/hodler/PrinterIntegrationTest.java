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

  private PrintableId printableId;

  @Before
  public void initialize_contract() throws Exception {
    if (!initialized) {
      new ProcessBuilder("testrpc").start();
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
    Files.delete(Paths.get("wallet.tmp"));
  }

  @Test
  public void is_able_to_buy_a_print_job() throws Exception {
    Bytes32 firstDeliverableHash = ethereumService.keccak256(printableId
      .asString());

    buyRightToPrint(firstDeliverableHash);

    UserId userId = new UserId(credentials.getAddress());
    Integer response = printableAmount(userId, printableId);
    assertThat(response, is(1));
  }

  @Test
  public void is_able_to_reset_print_amount() throws Exception {
    UserId userId = new UserId(credentials.getAddress());
    PrintableId secondPrintableHash = new PrintableId
      ("another gcodehash");
    Bytes32 printableHash = ethereumService.keccak256(secondPrintableHash
      .asString());
    buyRightToPrint(printableHash);

    resetPrintables(userId, secondPrintableHash);

    Integer response = printableAmount(userId, secondPrintableHash);
    assertThat(response, is(0));
  }

  private void buyRightToPrint(Bytes32 printableHash) throws
    InterruptedException, java.util.concurrent.ExecutionException {
    contract.get().buyRightToPrintOnce(printableHash,
      BigInteger
        .valueOf
          (10000))
      .get();
  }

  private Integer printableAmount(UserId userId, PrintableId
    secondPrintableHash) {
    return this.restTemplate.getForObject
      (String.format("/printables/%s/%s", secondPrintableHash.asString(), userId.asString()),
        Integer.class);
  }

  private void resetPrintables(UserId userId, PrintableId secondPrintableHash) {
    this.restTemplate.delete(String.format("/printables/%s/%s", secondPrintableHash
        .asString(), userId.asString()));
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
