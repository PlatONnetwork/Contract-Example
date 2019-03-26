
/*
 * may be some declaration here.
 */
package platon.mpc.proxy;

import net.platon.mpc.proxy.sdk.ProxyClient;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;


/**
 * Attention! This file was auto-generated, DO NOT EDIT!
 */

public class ProxyTool extends ProxyClient {
    /**
     * Constructor
     */
    public ProxyTool(Web3j web3j, Credentials credentials) {
        super(web3j, credentials);
    }

    public ProxyTool(String url, Credentials credentials) {
        super(url, credentials);
    }

    public ProxyTool(String url, String walletPath, String walletPass) {
        super(url, walletPath, walletPass);
    }

    public ProxyTool(Web3j web3j, String walletPath, String walletPass) {
        super(web3j, walletPath, walletPass);
    }

    /**
     * Public method
     */
    public String startCalc(String method) {
        return startCalc(method, 0);
    }

    public String startCalc(String method, int retry) {
        return super.startCalc(method, retry);
    }

}
