package net.platon.bid;

import java.io.File;
import java.math.BigInteger;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.gas.DefaultWasmGasProvider;
import org.web3j.utils.Files;
import org.web3j.utils.Numeric;

public class BidMain {

	public static void main(String[] args) throws Exception {
        String walletPath = "./UTC--2019-02-20T11-47-25.598605318Z--588fa184b404f9c0e54ce8ce81086293fc08f432";
        String walletPass = "123456";
        String url = "http://127.0.0.1:6789";
        String data = "2,4,6,6";
        String code = "./bidding.wasm";
        String contractAddress = "0x4c1e1f56bb4bab4c22bf0a6955ec37d73dfa8cfb";
		String result = cacl(url, walletPath, walletPass,code,contractAddress, data);
		System.out.println("get result:" + result);
	}
	
	private static String cacl(String url, String walletPath,String walletPass, String codePath,String contractAddress,String data) throws Exception{
		String[] input = data.split(",");
		//申明web3j和credentials
		Web3j web3j = Web3j.build(new HttpService(url));
		Credentials credentials = WalletUtils.loadCredentials(walletPass, walletPath);
		byte[] dataBytes = Files.readBytes(new File(codePath));
		String binData = Hex.toHexString(dataBytes);
		//初始化对象
		Bidding contract = new Bidding(binData, contractAddress, web3j, credentials, new DefaultWasmGasProvider());
		String result = contract.calcData(new BigInteger(input[0]), new BigInteger(input[1]), new BigInteger(input[2]), new BigInteger(input[3]));
		//对结果进行解码
		RlpList rl = RlpDecoder.decode(Numeric.hexStringToByteArray(result));
        RlpType rt = rl.getValues().get(0);
        RlpType rt2 = ((RlpList) rt).getValues().get(0);
        BigInteger sss = ((RlpString) rt2).asPositiveBigInteger();
        //得到返回值
        result = sss.toString(10);
		return result;
	}
	
}
