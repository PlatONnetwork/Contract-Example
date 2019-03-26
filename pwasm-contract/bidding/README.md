# PlatON WASM Bidding Example

## Second weighted average bid evaluation
Suppose there are three bidders A/B/C, which are quoted a/b/c respectively, and the bidder's P bid base price is p, and the bid evaluation is carried out by Dapp. The bid evaluation method is as follows:
Average price of y = y = ( a + b + c ) / 3
Bid evaluation value z = ( y + p) / 2
The bid price is lower than (including equal to) z value, and the closest one is the winning bidder.

## Dependency description
`Wasm` compilation toolset needs to be downloaded:

[pwasm-windows-x86_64-0.3.0.zip](https://download.platon.network/0.3/pwasm-windows-x86_64-0.3.0.zip): `PlatON` `WASM` Development kit

[Ptool-1.0-capsule.jar](https://download.platon.network/Ptool-1.0-capsule.jar): Tools for generating wallet and publishing contract

[client-sdk-java-0.4.0.zip](https://github.com/PlatONnetwork/client-sdk-java/releases/download/client_sdk_v0.4.0/client-sdk-java-0.4.0.zip) : Java development kit for compiled contract

## Building contract development environment
### Installing third-party toolkits
Windows systems need to install the following third-party toolkits.
- **cmake 3.0+**

  Building tool used to control the project compilation.

- **mingw 6.0.0+**

  Dependency development environment for WASM contract compilation.

Use the Chocolatey to easily install the above tools. Open the cmd window and execute the following command:
```
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey .org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
```

Install `CMake `:
```
choco install cmake --version=3.13.0
```

Install `mingw` :
```
choco install mingw
```

### PlatON Development Kit
The dependent file `pwasm-windows-x86_64-0.3.0.zip` is the `PlatON` wasm contract development kit. After decompressing, all files are located in the `pWASM` directory. The directory file structure is as follows:
```txt
├── bin
├──boost
├── CMakeModules
├── external
├── libc
├── libc++
├── platonlib
├── rapidjson
├── docs
├── script
├── user
├── CMakeLists.txt
└── README.md
```
Description:
* `external` is used to store the binary packages needed in the compilation process, mainly including the generation toolchain of `abi` and `bin` files.
* `user` is an example of a contract and is for reference only. Also used to store scripts to automatically generate contract template files
* `platonlib` is a library dedicated to interacting with blockchains. Specific reference: [Wasm contract built-in library](https://pwasmdoc.platon.network/)

### Generating a local wallet
Use the downloaded tool `Ptool-1.0-capsule.jar` to execute the following command to generate a local wallet:
```
java -jar Ptool-1.0-capsule.jar --method=genWallet --walletPath=.\ --walletPass=123456
```
After the command is executed, the command line returns the wallet address and generates the wallet file in the bin directory. If it is tested on `PlatON TestNet`, you need to go to [PlatON official website](https://developer.platon.network/#/?lang=en) Apply for a test coin.

Subsequent use of the generated wallet address and file, assuming this is returned here:
- Wallet address: `0x588fa184b404f9c0e54ce8ce81086293fc08f432`
- Wallet file: `UTC--2019-02-20T11-47-25.598605318Z--588fa184b404f9c0e54ce8ce81086293fc08f432`

## bidding contract
### Creating a contract project
Use the following command in `pWASM` for project configuration
```
.\script\autoproject.bat . bidding
```
At this point, the build folder will be generated in the pWASM directory, and the bidding directory will be generated in the user directory. The bidding directory will automatically generate the empty code file biding.cpp.

### Writing contract code
Modify the bidding.cpp file and replace the code with `contract\bidding.cpp`.

### Contract compilation
Compile with the following command in `pWASM\build`
```
mingw32-make.exe
```
After the compilation is successful, the subdirectory `user\bidding` will be created in the `pWASM\biuild` directory, and the bidding.wasm and bidding.cpp.abi.json files will be generated in the directory.
Bidding.cpp.abi.json: interface description file corresponding to the contract;
Bidding.wasm: the compiled binary file, bytecode instruction set;

### Generating Java wrapper class
To facilitate the development of dapp, PlatON provides the `client-sdk` tool to generate the java wrapper class from bidding.wasm.
Unzip the downloaded `client-sdk-java-0.4.0.zip`, enter the `client-sdk-java-0.4.0/bin` directory, and compile the generated bidding.wasm and bidding.cpp.abi.json files. Copy to this directory and use the following command to generate the java wrapper class.
```
client-sdk wasm generate --javaTypes .\bidding.wasm .\bidding.cpp.abi.json -p net.platon -o .\ -t WASM
```

Will generate bidding.java under net\platon

### Publishing contract
Use the downloaded tool `Ptool-1.0-capsule.jar` to post the bidding contract to Platton TestNet with the following command:
```
java -jar Ptool-1.0-capsule.jar --method=deployContract --url=http://test-va.platon.network:6789 --walletPath=.\UTC--2019-02-20T11-47-25.598605318 Z--588fa184b404f9c0e54ce8ce81086293fc08f432 --walletPass=123456 --code=.\bidding.wasm
```
After the release is successful, return the contract address, here it is assumed that the return contract address is `0x4c1e1f56bb4bab4c22bf0a6955ec37d73dfa8cfb`.

note:
- If you need to publish to other Plat chain, you need to modify the url command line parameters.
- The parameters walletPath and walletPass need to be replaced according to the actual generated result of the `Generate local wallet` step.

## Bidding Dapp
Dapp can be written in Java in PlatON. The bidding Dapp source code can be found in the directory `dapp`, which can be opened directly or developed according to the following steps.
### Creating a Java project
Create a Gradle project using Eclipse or other Java development tools.

### Adding a dependency package
Add the following dependency to the default configuration file build.gradle under Gradle project:
```
repositories {
    Maven { url "https://sdk.platon.network/nexus/content/groups/public/" }
}
compile "com.platon.client:core:0.3.0"
```

### Importing contract wrapper class
Import the java file `client-sdk-0.4.0\bin\net\platon\bidding.java` into the java project.

### Dapp code writing
The contract code is called as follows:
```
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
        String url = "http://test-va.platon.network:6789";
        String data = "2,4,6,6";
        String code = "./bidding.wasm";
        String contractAddress = "0x4c1e1f56bb4bab4c22bf0a6955ec37d73dfa8cfb";
		String result = cacl(url, walletPath, walletPass,code,contractAddress, data);
		System.out.println("get result:" + result);
	}
	
	private static String cacl(String url, String walletPath,String walletPass, String codePath,String contractAddress,String data) throws Exception{
		String[] input = data.split(",");
		//declare web3j credentials
		Web3j web3j = Web3j.build(new HttpService(url));
		Credentials credentials = WalletUtils.loadCredentials(walletPass, walletPath);
		byte[] dataBytes = Files.readBytes(new File(codePath));
		String binData = Hex.toHexString(dataBytes);
		//initiate
		Bidding contract = new Bidding(binData, contractAddress, web3j, credentials, new DefaultWasmGasProvider());
		String result = contract.calcData(new BigInteger(input[0]), new BigInteger(input[1]), new BigInteger(input[2]), new BigInteger(input[3]));
		//decode
		RlpList rl = RlpDecoder.decode(Numeric.hexStringToByteArray(result));
        RlpType rt = rl.getValues().get(0);
        RlpType rt2 = ((RlpList) rt).getValues().get(0);
        BigInteger sss = ((RlpString) rt2).asPositiveBigInteger();
        //result back
        result = sss.toString(10);
		return result;
	}
}
```

Note that in the code:
- The variable contractAddress represents the contract address, note the actual contract address returned by the `Publish Contract` step.
- The variable code is the wasm file path. Note that the wasm file path generated by the `Contract Compilation` step is modified.
- If you need to publish to other Plat chain, you need to modify the url variable value
- Variable walletPath and walletPass need to be replaced according to the actual generated result of the `Generate local wallet` step

### Dapp compile and run
Compile and run the `BidMain` class directly in the IDE.
