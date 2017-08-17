package co.hodler.boundaries;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.0.
 */
public final class Printer extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b5b60018054600160a060020a03191632600160a060020a03161790555b5b6101ce8061003c6000396000f300606060405263ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166317217d0d811461005e578063229450f4146100825780638da5cb5b146100b65780638da96965146100e5575b600080fd5b341561006957600080fd5b610080600435600160a060020a03602435166100f2565b005b341561008d57600080fd5b6100a4600435600160a060020a0360243516610134565b60405190815260200160405180910390f35b34156100c157600080fd5b6100c961015c565b604051600160a060020a03909116815260200160405180910390f35b61008060043561016b565b005b60015433600160a060020a0390811691161461010d57600080fd5b600082815260208181526040808320600160a060020a03851684529091528120555b5b5050565b600082815260208181526040808320600160a060020a03851684529091529020545b92915050565b600154600160a060020a031681565b61271034101561017a57600080fd5b600081815260208181526040808320600160a060020a03331684529091529020600190555b505600a165627a7a723058200e9240704584d0f6901afc4e6c17612c1dd08cfb95be7db6c17fdc5a1dce5e690029";

    private Printer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Printer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> resetPrints(Bytes32 gCodeHash, Address user) {
        Function function = new Function("resetPrints", Arrays.<Type>asList(gCodeHash, user), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> timesUserIsAllowedToPrint(Bytes32 gCodeHash, Address user) {
        Function function = new Function("timesUserIsAllowedToPrint", 
                Arrays.<Type>asList(gCodeHash, user), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Address> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> buyRightToPrintOnce(Bytes32 gCodeHash, BigInteger weiValue) {
        Function function = new Function("buyRightToPrintOnce", Arrays.<Type>asList(gCodeHash), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function, weiValue);
    }

    public static Future<Printer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Printer.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Printer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Printer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Printer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Printer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Printer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Printer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
