package co.hodler;

import co.hodler.boundaries.DeployPrinter;
import co.hodler.boundaries.Printer;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PrinterIntegrationTest {
  private static String BLOCKCHAIN_URL = "http://localhost:8545";
  private static Credentials credentials;
  private static Web3j web3;
  private static Printer printer;
  private static Process testRpcProcess;

  @BeforeClass
  public static void initialize_contract() throws Exception {
    testRpcProcess = new ProcessBuilder("testrpc").start();
    Thread.sleep(2000);
    credentials = generateSampleWallet();

    send100EtherToWeb3CreatedAccount(credentials, extractFirstAccount());

    web3 = Web3j.build(new HttpService());
    DeployPrinter deployPrinter = new DeployPrinter(web3);
    printer = deployPrinter.deployWith(credentials);
  }

  @AfterClass
  public static void kill_testrpc() {
    testRpcProcess.destroy();
  }

  @Test
  public void be_able_to_buy_a_print_job() throws Exception {
    Bytes32 firstDeliverableHash = new Bytes32(hexStringToByteArray(web3.web3Sha3
      ("some-gcode").send().getResult().replace("0x", "")));
    Address usersAddress = new Address(credentials.getAddress());

    printer.buyRightToPrintOnce(firstDeliverableHash, BigInteger.valueOf(10000))
      .get();

    BigInteger result = printer.timesUserIsAllowedToPrint
      (firstDeliverableHash, usersAddress).get().getValue();
    assertThat(result, is(BigInteger.valueOf(1)));
  }

  private static Credentials generateSampleWallet() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
    new File("wallets").mkdir();
    String fileName = WalletUtils.generateLightNewWalletFile("axel", new File("wallets"));
    return WalletUtils.loadCredentials("axel", "wallets/" + fileName);
  }

  private static void send100EtherToWeb3CreatedAccount(Credentials credentials, String firstAccount) throws Exception {
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
    JsonNode responseBody = Unirest.post(BLOCKCHAIN_URL).body(ethAccountsRpcPayload).asJson().getBody();
    JsonNode ethAccounts = new JsonNode(responseBody.getObject().get("result").toString());
    return (String) ethAccounts.getArray().get(0);
  }

  private byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
        + Character.digit(s.charAt(i+1), 16));
    }
    return data;
  }
}
