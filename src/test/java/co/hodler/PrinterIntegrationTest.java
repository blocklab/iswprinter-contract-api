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
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.math.BigInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"contractAddress=foo"})
public class PrinterIntegrationTest {
  private static String BLOCKCHAIN_URL = "http://localhost:8545";
  private static Credentials credentials;
  private static Process testRpcProcess;
  private static String contractAddress;

  @Autowired
  EthereumService ethereumService;
  @Autowired
  Configuration configuration;
  @Autowired
  PrinterContract contract;

  DefaultPrinterService printerService;
  private static PrintableId printableId;

  @BeforeClass
  public static void initialize_contract() throws Exception {
    testRpcProcess = new ProcessBuilder("testrpc").start();
    Thread.sleep(2000);
    credentials = generateSampleWallet();

    send100EtherToWeb3CreatedAccount(credentials, extractFirstAccount());

    DeployPrinter deployPrinter = new DeployPrinter(new
      DefaultEthereumService());
    contractAddress = deployPrinter.deployWith(credentials)
      .getContractAddress();
    printableId = new PrintableId("some-gcode");
  }

  @Before
  public void initialize() throws Exception {
    configuration.contractAddress = contractAddress;
    printerService = new DefaultPrinterService(contract.get(credentials),
      ethereumService);
  }

  @AfterClass
  public static void kill_testrpc() {
    testRpcProcess.destroy();
  }

  @Test
  public void is_able_to_buy_a_print_job() throws Exception {
    Bytes32 firstDeliverableHash = ethereumService.keccak256(printableId
      .asString());

    contract.get(credentials).buyRightToPrintOnce(firstDeliverableHash,
      BigInteger
      .valueOf
        (10000))
      .get();

    UserId userId = new UserId(credentials.getAddress());
    BigInteger result = printerService.checkAmountAllowedToPrint(printableId,
      userId);
    assertThat(result, is(BigInteger.valueOf(1)));
  }

  @Test
  public void is_able_to_reset_print_amount() throws Exception {
    UserId userId = new UserId(credentials.getAddress());

    printerService.resetPrints(printableId, userId);

    BigInteger result = printerService.checkAmountAllowedToPrint(printableId,
      userId);
    assertThat(result, is(BigInteger.valueOf(0)));
  }

  private static Credentials generateSampleWallet() throws Exception {
    new File("wallets").mkdir();
    String fileName = WalletUtils.generateLightNewWalletFile("axel", new File
      ("wallets"));
    return WalletUtils.loadCredentials("axel", "wallets/" + fileName);
  }

  private static void send100EtherToWeb3CreatedAccount(Credentials
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

  private static String extractFirstAccount() throws Exception {
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
