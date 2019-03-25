
# PlatON Dapp Example

## 二次加权平均评标
假设有三家投标方A/B/C，分别报价a/b/c，开标方P标底价为p，通过Dapp进行评标，评标方法如下：
报价平均值 y = ( a + b + c ) / 3 
评标基准值 z = ( y + p）/ 2
取投标报价中低于（含等于）z值，且最接近者为中标人。

## 依赖说明
wasm编译需要下载：

[pwasm-windows-x86_64-0.3.0.zip](https://download.platon.network/0.3/pwasm-windows-x86_64-0.3.0.zip)：PlatON开发工具包

[Ptool-1.0-capsule.jar](https://download.platon.network/Ptool-1.0-capsule.jar)：生成钱包和发布合约的工具

[client-sdk-java-0.4.0.zip](https://github.com/PlatONnetwork/client-sdk-java/releases/download/client_sdk_v0.4.0/client-sdk-java-0.4.0.zip)：生成合约对应Java Wapper类工具

## 搭建合约开发环境
### 安装第三方工具包
windows系统需安装以下第三方工具包。
- cmake 3.0+ 用于生成构建文件
- mingw 6.0.0+ 用于编译构建文件

使用Chocolatey便捷式安装以上工具。打开cmd窗口，执行以下命令：
```
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"
```

安装CMake
```
choco install cmake --version=3.13.0
```

安装mingw
```
choco install mingw
```

### PlatON开发工具包
依赖文件pwasm-windows-x86_64-0.3.0.zip为PlatON开发工具包，解压后所有文件均位于`pWASM`目录下，该目录文件结构如下：
```txt
├── bin
├── boost
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
部分文件夹功能概述：
* `external` 用于存放编译过程中需要的二进制包，主要包含`abi`与`bin`文件的生成工具链
* `user` 为合约范例，仅供参考。同时也用于存放脚本自动生成合约模板文件
* `platonlib` PlatON 专供的合约与区块链交互的库。具体参考：[Wasm合约内置库](https://pwasmdoc.platon.network/)

### 生成本地钱包
使用下载的工具`Ptool-1.0-capsule.jar`执行以下命令生成本地钱包：
```
java -jar Ptool-1.0-capsule.jar --method=genWallet --walletPath=.\ --walletPass=123456
```
命令执行完成，命令行返回钱包地址，同时在bin目录下生成钱包文件，如果是在PlatON TestNet上进行测试，则需要到[PlatON官网](https://developer.platon.network/#/?lang=en)进行申请测试币。

后续会使用到生成的钱包地址和文件，假设此处返回：
- 钱包地址：`0x588fa184b404f9c0e54ce8ce81086293fc08f432`
- 钱包文件：`UTC--2019-02-20T11-47-25.598605318Z--588fa184b404f9c0e54ce8ce81086293fc08f432`

## bidding合约
### 创建合约工程
在`pWASM`中使用以下命令进行项目配置
```
.\script\autoproject.bat . bidding
```
此时，pWASM目录下会生成build文件夹，同时在user目录下生成bidding目录，bidding目录会自动生成空代码文件biding.cpp。

### 编写合约代码
修改bidding.cpp文件，将代码替换为`bidding/contract/bidding.cpp`。

### 合约编译
在`pWASM\build`中使用以下命令进行编译
```
mingw32-make.exe
```
编译成功后，会在`pWASM\biuild`目录下新建子目录`user\bidding`，并在该目录下生成bidding.wasm和bidding.cpp.abi.json文件。
bidding.cpp.abi.json：合约对应的接口描述文件；
bidding.wasm：合约编译后的二进制文件，字节码指令集；

### 生成Java wrapper类
为方便dapp开发，PlatON提供`client-sdk`工具可将bidding.wasm生成java wrapper类。
解压下载的`client-sdk-java-0.4.0.zip`，进入`client-sdk-java-0.4.0/bin`目录，将合约编译生成的bidding.wasm和bidding.cpp.abi.json文件拷贝到该目录下，使用以下命令生成java wrapper类。
```
client-sdk wasm generate --javaTypes .\bidding.wasm .\bidding.cpp.abi.json -p net.platon -o .\ -t WASM
```

会在net\platon下生成bidding.java

### 发布合约
使用下载的工具`Ptool-1.0-capsule.jar`用以下命令可发布bidding合约到PlatON TestNet：
```
java -jar Ptool-1.0-capsule.jar --method=deployContract --url=http://test-va.platon.network:6789 --walletPath=.\UTC--2019-02-20T11-47-25.598605318Z--588fa184b404f9c0e54ce8ce81086293fc08f432 --walletPass=123456 --code=.\bidding.wasm
```
发布成功后，返回合约地址，此处假设返回合约地址为`0x4c1e1f56bb4bab4c22bf0a6955ec37d73dfa8cfb`。

注意：
- 如果需要发布到其他PlatON链，需要修改url命令行参数
- 参数walletPath和walletPass需要根据`生成本地钱包`步骤实际生成结果进行替换

## bidding Dapp
PlatON中可使用Java编写Dapp。目录`bidding\dapp`中可找到bidding Dapp源代码，可直接打开使用或按照以下步骤进行开发。
### 创建Java工程
使用Eclipse或其他Java开发工具创建Gradle工程。

### 添加依赖包
在Gradle工程下默认配置文件build.gradle中添加以下依赖：
```
repositories {
    maven { url "https://sdk.platon.network/nexus/content/groups/public/" }
}
compile "com.platon.client:core:0.3.0"
```

### 导入合约wrapper类
将java文件`client-sdk-0.4.0\bin\net\platon\bidding.java`导入到java项目中。

### Dapp代码编写
调用合约代码如下：
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
```
注意，代码中：
- 变量contractAddress表示合约地址，注意修改为`发布合约`步骤返回的实际合约地址
- 变量code为wasm文件路径，注意修改为`合约编译`步骤生成的wasm文件路径。
- 如果需要发布到其他PlatON链，需要修改url变量取值
- 变量walletPath和walletPass需要根据`生成本地钱包`步骤实际生成结果进行替换

### Dapp编译运行
直接在IDE中编译运行BidMain类。














