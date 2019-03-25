package net.platon.bid;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Int32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.PlatOnUtil;
import org.web3j.utils.TXTypeEnum;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 0.4.0.
 */
public class Bidding extends PlatOnContract {
    private static final String ABI = "[{\"name\":\"calc\",\"inputs\":[{\"name\":\"a\",\"type\":\"uint64\"},{\"name\":\"b\",\"type\":\"uint64\"},{\"name\":\"c\",\"type\":\"uint64\"},{\"name\":\"p\",\"type\":\"uint64\"}],\"outputs\":[{\"name\":\"\",\"type\":\"int32\"}],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"bidding\",\"inputs\":[{\"type\":\"int32\"}],\"type\":\"event\"}]";

    public static final String FUNC_CALC = "calc";

    public static final Event BIDDING_EVENT = new Event("bidding", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int32>() {}));
    ;

    @Deprecated
    protected Bidding(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Bidding(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Bidding(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Bidding(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> calc(BigInteger a, BigInteger b, BigInteger c, BigInteger p) {
        final Function function = new Function(
                FUNC_CALC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(a), 
                new org.web3j.abi.datatypes.generated.Uint64(b), 
                new org.web3j.abi.datatypes.generated.Uint64(c), 
                new org.web3j.abi.datatypes.generated.Uint64(p)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String calcData(BigInteger a, BigInteger b, BigInteger c, BigInteger p) {
        final Function function = new Function(
                FUNC_CALC, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(a), 
                new org.web3j.abi.datatypes.generated.Uint64(b), 
                new org.web3j.abi.datatypes.generated.Uint64(c), 
                new org.web3j.abi.datatypes.generated.Uint64(p)), 
                Collections.<TypeReference<?>>emptyList());
        return PlatOnUtil.invokeEncode(function,customTransactionType(function));
    }

    public static BigInteger calcGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, BigInteger a, BigInteger b, BigInteger c, BigInteger p) throws IOException {
        String ethEstimateGasData = calcData(a, b, c, p);
        return PlatOnUtil.estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public List<BiddingEventResponse> getBiddingEvents(TransactionReceipt transactionReceipt) {
        List<PlatOnContract.EventValuesWithLog> valueList = extractEventParametersWithLog(BIDDING_EVENT, transactionReceipt);
        ArrayList<BiddingEventResponse> responses = new ArrayList<BiddingEventResponse>(valueList.size());
        for (PlatOnContract.EventValuesWithLog eventValues : valueList) {
            BiddingEventResponse typedResponse = new BiddingEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BiddingEventResponse> biddingEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BiddingEventResponse>() {
            public BiddingEventResponse call(Log log) {
                PlatOnContract.EventValuesWithLog eventValues = extractEventParametersWithLog(BIDDING_EVENT, log);
                BiddingEventResponse typedResponse = new BiddingEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<BiddingEventResponse> biddingEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BIDDING_EVENT));
        return biddingEventObservable(filter);
    }

    public static RemoteCall<Bidding> deploy(Web3j web3j, Credentials credentials, String contractBinary, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Bidding.class, web3j, credentials, contractGasProvider, contractBinary, ABI, "");
    }

    @Deprecated
    public static RemoteCall<Bidding> deploy(Web3j web3j, Credentials credentials, String contractBinary, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Bidding.class, web3j, credentials, gasPrice, gasLimit, contractBinary, ABI, "");
    }

    public static RemoteCall<Bidding> deploy(Web3j web3j, TransactionManager transactionManager, String contractBinary, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Bidding.class, web3j, transactionManager, contractGasProvider, contractBinary, ABI, "");
    }

    @Deprecated
    public static RemoteCall<Bidding> deploy(Web3j web3j, TransactionManager transactionManager, String contractBinary, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Bidding.class, web3j, transactionManager, gasPrice, gasLimit, contractBinary, ABI, "");
    }

    @Deprecated
    public static Bidding load(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bidding(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Bidding load(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Bidding(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Bidding load(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Bidding(contractBinary, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Bidding load(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Bidding(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getDeployData(String contractBinary) {
        return PlatOnUtil.deployEncode(contractBinary, ABI);
    }

    public static BigInteger getDeployGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String contractBinary) throws IOException {
        return PlatOnUtil.estimateGasLimit(web3j, estimateGasFrom, estimateGasTo, getDeployData(contractBinary));
    }

    private static long customTransactionType(Function function) {
        return TXTypeEnum.WASM.type;
    }

    protected long getTransactionType(Function function) {
        return customTransactionType(function);
    }

    public static class BiddingEventResponse {
        public Log log;

        public BigInteger param1;
    }
}
