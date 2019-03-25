package client;


import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterParser {
    private static final Logger logger = LoggerFactory.getLogger(ParameterParser.class.getName());

    public boolean sample = false;

    public String walletPath = "";
    public String walletPass = "";
    public String url = "";

    public String contractAddress = "";
    public int threadNum = 1;
    public String api = "";
    public String method = "";
    public String returnType = "";


    private void Helper() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================== Usage =====================");
        sb.append("\n-h             --help: Show this help");
        sb.append("\n");
        sb.append("\n-s           --sample: Is run mpc sample");
        sb.append("\n");
        sb.append("\n-w       --walletPath: Wallet path");
        sb.append("\n-p       --walletPass: Wallet password");
        sb.append("\n-c              --url: The url connect to PlatON node");
        sb.append("\n");
        sb.append("\n-a  --contractAddress: MPC contract address");
        sb.append("\n-n        --threadNum: Thread number, default 1, for performance test");
        sb.append("\n-i              --api: MPC client api");
        sb.append("\n                       =startCalc(method)");
        sb.append("\n                       =getTaskId(transactionHash)");
        sb.append("\n                       =getResultByTaskId(taskId)");
        sb.append("\n                       =getResultByTransactionHash(transactionHash)");
        sb.append("\n                       =getPlainText(cipher)");
        sb.append("\n-m           --method: MPC method");
        sb.append("\n-r       --returnType: return type");

        System.out.println(sb.toString());
    }

    public ParameterParser(String[] args) {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption("h", "help", false, "Show this help");
        options.addOption("s", "sample", false, "Is run sample");
        options.addOption("w", "walletPath", true, "Wallet Path");
        options.addOption("p", "walletPass", true, "Wallet Password");
        options.addOption("c", "url", true, "The url connect to platon node");
        options.addOption("a", "contractAddress", true, "MPC contract address");
        options.addOption("n", "threadNum", true, "Thread number, default 1, for performance test");
        options.addOption("i", "api", true, "MPC Client API");
        options.addOption("m", "method", true, "MPC method");
        options.addOption("r", "returnType", true, "MPC method return type");

        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            //e.printStackTrace();
            logger.error("Exception: ", e);
            System.exit(1);
        }

        if (commandLine.hasOption("help")) {
            Helper();
            System.exit(0);
        }
        if (commandLine.hasOption("sample")) {
            sample = true;
        }
        if (commandLine.hasOption("walletPath")) {
            walletPath = commandLine.getOptionValue("walletPath");
        }
        if (commandLine.hasOption("walletPass")) {
            walletPass = commandLine.getOptionValue("walletPass");
        }
        if (commandLine.hasOption("url")) {
            url = commandLine.getOptionValue("url");
        }
        if (commandLine.hasOption("contractAddress")) {
            contractAddress = commandLine.getOptionValue("contractAddress");
        }
        if (commandLine.hasOption("threadNum")) {
            threadNum = Integer.parseInt(commandLine.getOptionValue("threadNum"));
            if (threadNum <= 1) threadNum = 1;
        }
        if (commandLine.hasOption("api")) {
            api = commandLine.getOptionValue("api");
        }
        if (commandLine.hasOption("method")) {
            method = commandLine.getOptionValue("method");
        }
        if (commandLine.hasOption("returnType")) {
            returnType = commandLine.getOptionValue("returnType");
        }

        logger.info(toString());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=============== Parameters ===============");
        sb.append("\n         sample: ").append(sample);
        sb.append("\n");
        sb.append("\n     walletPath: ").append(walletPath);
        sb.append("\n     walletPass: ********");
        sb.append("\n            url: ").append(url);
        sb.append("\n");
        sb.append("\ncontractAddress: ").append(contractAddress);
        sb.append("\n      threadNum: ").append(threadNum);
        sb.append("\n            api: ").append(api);
        sb.append("\n         method: ").append(method);
        sb.append("\n     returnType: ").append(returnType);

        return sb.toString();
    }


}
