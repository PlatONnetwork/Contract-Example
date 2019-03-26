package client;

import net.platon.mpc.proxy.sdk.ProxyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import platon.mpc.proxy.ProxyTool;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This demo is show how to use command parameters.
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class.getName());

    private ParameterParser paras;

    private Client(ParameterParser paras) {
        this.paras = paras;
    }

    public static void main(String[] args) {
//        String walletPath = "--walletPath=" + System.getProperty("user.dir") + "/samples/config/60ceca9c1290ee56b98d4e160ef0453f7c40d219";
//        String walletPass = "--walletPass=11111111";
//        String url = "--url=http://192.168.18.31:7789";
//        String api = "--api=getResultByTaskId(d988b4f4a9f269b8a60d828a2eeca0eec2a446b1d2519818042ef712a8dc9a63)";
//        String method = "TestAdd";
//        String returnType = "--returnType=int";
//        String[] aas = {walletPath, walletPass, url, api, method,returnType};
//
//        Client client0 = new Client(new ParameterParser(aas));
//        client0.doNothing();

        Client client = new Client(new ParameterParser(args));
        client.run();
    }

    private ArrayList<String> getApi(String api) {
        ArrayList<String> apiFuncArgs = new ArrayList<>();
        String pattern = "(\\w+)\\((\\w+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(api);
        if (m.find()) {
            for (int i = 1; i <= m.groupCount(); i++) {
                apiFuncArgs.add(m.group(i));
            }
        }
        return apiFuncArgs;
    }

    private void doNothing() {    }

    private void run() {

        ArrayList<String> apiFuncArgs = getApi(paras.api);
        if (apiFuncArgs.size() < 2) {
            logger.warn("apiFuncArgs.size():{}", apiFuncArgs.size());
            return;
        }
        String funcName = apiFuncArgs.get(0);
        String arg1 = apiFuncArgs.get(1);

        /*
         * 0. you should provide follow arguments
         */
        String walletPath = paras.walletPath;
        String walletPass = paras.walletPass;
        String url = paras.url;

        if (funcName.equals("getPlainText")) {
            logger.info("Cipher Hex String: {}", arg1);
            String plainHexString = ProxyClient.getPlainText(arg1, walletPath, walletPass);
            logger.info("Plain Hex String: {}", plainHexString);
            return;
        }


        /*
         * 1. new a proxy client, and set gasPrice/gasLimit/contractAddress
         */
        ProxyTool client = new ProxyTool(url, walletPath, walletPass);
        {
            // or, you can set contract-address only, price and limit will use default
            client.setContractAddress(paras.contractAddress);
        }

        String transactionHash;
        if (funcName.equals("startCalc")) {
            /*
             * 2. start calc, by pass a method
             */
            transactionHash = client.startCalc(arg1, 3);
            logger.info("transactionHash:{}", transactionHash);
            if (transactionHash == null) {
                logger.warn("transaction hash is null");
            }

            client.close();
            return;
        }

        if (funcName.equals("getTaskId")) {
            String taskId = client.getTaskId(arg1);
            logger.info("taskId:{}", taskId);
            if (taskId == null) {
                logger.warn("task id is null");
            }
            client.close();
            return;
        }

        String cipher;
        switch (funcName) {
            case "getResultByTaskId":
                cipher = client.getResultByTaskId(arg1);
                break;
            case "getResultByTransactionHash":
                cipher = client.getResultByTransactionHash(arg1);
                break;
            default:
                logger.warn("function [{}] not exist!", funcName);
                client.close();
                return;
        }

        logger.info("cipher:{}", cipher);
        if (cipher == null) {
            logger.warn("cipher is null");
            client.close();
            return;
        }

        parseResult(client, cipher);

        client.close();
    }

    private void parseResult(ProxyTool client, String cipher) {
        String returnType = paras.returnType;
        switch (returnType) {
            case "boolean": {
                boolean ret = client.getBool(cipher);
                logger.info("result boolean: {}", ret);
                break;
            }
            case "int32": {
                int ret = client.getInt32(cipher);
                logger.info("result int: {}", ret);
                break;
            }
            case "int64": {
                long ret = client.getInt64(cipher);
                logger.info("result long: {}", ret);
                break;
            }
            case "float": {
                float ret = client.getFloat(cipher);
                logger.info("result float: {}", ret);
                break;
            }
            case "double": {
                double ret = client.getDouble(cipher);
                logger.info("result double: {}", ret);
                break;
            }
            default:
                logger.warn("unknow return type [{}]", paras.returnType);
                break;
        }
    }

}