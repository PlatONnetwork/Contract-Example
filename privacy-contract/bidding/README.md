[TOC]
# Bidding privacy contract development on Linux

本范例实现了竞标隐私合约，竞标方法为： 二次加权平均评标。***本范例要求运行在Ubuntu16.04。***

## 二次加权平均评标

假设有三家投标方A/B/C，分别报价a/b/c，开标方P标底价为p，通过Dapp进行评标，评标方法如下：
报价平均值 y = ( a + b + c ) / 3 
评标基准值 z = ( y + p）/ 2
取投标报价中低于（含等于）z值，且最接近者为中标人。

## 环境要求

### 硬件要求

- CPU架构

  目前官方只支持`Intel`2代及以上`CPU`架构，搭建环境前需要检测机器是否满足最低要求。

  ```bash
  $ cat /proc/cpuinfo
  ```

  例如输出如下信息：

  ```bash
  model name      : Intel(R) Xeon(R) CPU E5-2620 v2 @ 2.10GHz
  ```

  其中`E5-2620 v2`表示`CPU`为Intel第三代架构，符合要求。

  > **CPU架构必须满足Intel v2或以上，否则将无法正常运行**

### 软件要求

- Ubuntu16.04

  指定操作系统为Ubuntu16.04。

- JDK1.8+

  请参考：[oracle官方网站](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)，安装并配置好环境变量`CLASSPATH`, `PATH`, `JAVA_HOME`。

- Maven3.3.9+

  请参考：[maven官方网站](http://maven.apache.org/download.cgi)完成安装。

## 搭建隐私计算网络
搭建具有两个计算节点的隐私计算网络，其中计算节点均为`PlatON`节点客户端。

### 安装PlatON节点客户端

安装带MPC功能的`platon`节点客户端：

```bash
# add PPA
$ sudo add-apt-repository ppa:platonnetwork/platon
$ sudo apt-get update

# install platon with mpc
$ sudo apt-get -f install platon-all
```
### 搭建私有网络

参考[PlatON集群环境](zh-cn/basics/[Chinese-Simplified]-%e7%a7%81%e6%9c%89%e7%bd%91%e7%bb%9c#PlatON+%e9%9b%86%e7%be%a4%e7%8e%af%e5%a2%83)搭建私有网络，将计算节点连接私有网络。

### 创建数据节点钱包

对隐私计算参与方双方，隐私计算请求者，分别创建2个钱包文件和对应账户：

```bash
$ mkdir -p data
$ ./platon --datadir ./data account new
Your new account is locked with a password. Please give a password. Do not forget this password.
Passphrase:
Repeat passphrase:
Address: {0x9a568e649c3a9d43b7f565ff2c835a24934ba447}Copy to clipboardErrorCopied
$ ./platon --datadir ./data account new
Your new account is locked with a password. Please give a password. Do not forget this password.
Passphrase:
Repeat passphrase:
Address: {0xce3a4aa58432065c4c5fae85106aee4aef77a115}Copy to clipboardErrorCopied
```

以上命令执行2次创建钱包操作，例如本次示例输出：：

> `0x9a568e649c3a9d43b7f565ff2c835a24934ba447`

> `0xce3a4aa58432065c4c5fae85106aee4aef77a115`

### 启用MPC功能

参考[启用MPC计算功能](zh-cn/basics/[Chinese-Simplified]-%e7%a7%81%e6%9c%89%e7%bd%91%e7%bb%9c#%e4%b8%ba%e8%8a%82%e7%82%b9%e5%90%af%e7%94%a8MPC%e5%8a%9f%e8%83%bd)，在重启`platon`时候，将`-mpc.actor` 对应配置为上一步生成的数据节点钱包地址：`0x9a568e649c3a9d43b7f565ff2c835a24934ba447`,`0xce3a4aa58432065c4c5fae85106aee4aef77a115`。

## 体验隐私bidding

### 下载源码
克隆仓库；
```bash
$ git clone https://github.com/PlatONnetwork/Contract-Example.git
```
### 安装隐私合约开发工具
#### 下载工具安装包
下载隐私合约开发工具[安装包](https://download.platon.network/0.5/platon-ubuntu-amd64-mpc-toolkit.tar.gz)：
```bash
$ wget https://download.platon.network/0.5/platon-ubuntu-amd64-mpc-toolkit.tar.gz
$ tar -xvzf platon-ubuntu-amd64-mpc-toolkit.tar.gz
```
执行完以上命令，隐私合约开发工具安装到`platon-mpc-toolkit`工作目录，目录结构如下：

```bash
platon-mpc-toolkit
├── compiler
├── config.json.template
├── create_testnet_wallet.sh
├── ctool
├── ctool.config.json.template
├── install-deps.sh
├── mpc
│   ....
├── pwasm
│   ....
├── readme.txt
├── sample
│   ├── SimpleAddProto.cpp
│   ├── SimpleAddProto.proto
│   └── YaoMillionairesProblem.cpp
├── web3j -> mpc/bin/web3j/bin/web3j
```
说明：
- **compiler**
  隐私合约编译脚本
- **ctool**
  隐私合约发布工具
- **install-deps.sh**
  第三方依赖安装脚本
- **create_testnet_wallet.sh**
  创建钱包工具脚本
- **mpc**
  mpc相关工具集
- **pwas**
  pWASM相关工具集
- **sample**
  隐私合约实例
- **config.json.template**
  隐私合约编译配置模板
- **ctool.config.json.template**
  隐私合约发布工具配置文件模板

切换到`platon-mpc-toolkit`目录，安装第三方集依赖：
```bash
$ bash install-deps.sh
```

#### compiler使用方法
**用法：**
```bash
$ bash ./compiler -c {your config file} -i {your privacy contract} -o {output directory}
```
**参数说明：**
* -c：隐私合约文件
* -o：输出目录
* -i：编译配置文件
* -p：`protobuf`定义文件

### 编译隐私合约

将克隆仓库的bidding隐私合约(`Contract-Example/privacy-contract/bidding/contract/BidEvaluation.cpp`)拷贝到`platon-mpc-toolkit/`，编译隐私合约:
```bash
$ cd platon-mpc-toolkit
$ bash compiler -c config.json.template -i BidEvaluation.cpp.cpp -o ./user
```
执行输出:
```bash
user/
├── MPCBidEvaluation.java         # MPC data node core class
├── MPCBidEvaluation-README.TXT
├── ProxyBidEvaluation.java       # dapp proxy core class
├── ProxyBidEvaluation-README.TXT
├── BidEvaluation.cpp.abi.json    # abi file of pwam 
└── BidEvaluation.wasm	          # compiled binary of pwasm
```

- **MPCBidEvaluation.java：**

数据节点核心类，与`Contract-Example/privacy-contract/bidding/bob-data-node/src/main/java/net/platon/vm/mpc/MPCBidEvaluation.java`相同，用于数据节点注册到计算节点参与隐私计算。

- **ProxyBidEvaluation.java：**

Dapp代理客户端类，与`Contract-Example/privacy-contract/bidding/dapp/src/main/java/platon/mpc/proxy/`ProxyBidEvaluation.java`相同, 用于发起隐私计算。

- **BidEvaluation.wasm：**

bidding隐私合约二进制，用于隐私合约发布。

- **BidEvaluation.cpp.abi.json**

bidding隐私合约abi，用于隐私合约发布。

### 发布隐私合约

切换工作目录到隐私合约开发工具包安装目录`platon-mpc-toolkit/`。执行以下步骤：

1. 编写配置文件
编写配置文件ctool.config.json如下：
```bash
{
  "url":"http://127.0.0.1:6789",
  "gas":"0x99999788888",
  "gasPrice":"0x333330",
  "from":"0x9a568e649c3a9d43b7f565ff2c835a24934ba447"
}
```
2. 临时解锁账户
```bash
$ platon attach http://localhost:6789
#enter rpc console
> personal.unlockAccount(eth.accounts[0])
```
3. 发布合约
```
./ctool deploy --abi ./user/BidEvaluation.cpp.abi.json --code ./user/BidEvaluation.wasm --config ./config.json
```
部署成功后，比如本次输出合约发布地址：
> 0x43355c787c50b647c425f594b441d4bd75198888

### 编译mvn工程

切换工作目录到：`Contract-Example/privacy-contract/bidding`，分别编译数据节点程序和`Dapp`服务，命令如下：

```bash
cd bob-data-node
mvn package

cd ../alice-data-node
mvn package

cd ../dapp
mvn package
```

执行命令，分别生成：

```bash
bob-data-node/target/bob-data-node-1.0.jar
alice-data-node/target/alice-data-node-1.0.jar
dapp/target/dapp-1.0.jar
```

### 开启Bob数据节点

切换到工作目录：`Contract-Example/privacy-contract/bidding`，修改配置文件如下：

```bash
#
# The client reads this property to create the reference to the
# "TaskCallback" object in the server.
#
TaskCallback.Proxy=tasksession:default -h localhost -p 10002
```

拷贝`0x9a568e649c3a9d43b7f565ff2c835a24934ba447`地址对应钱包文件，比如命名为：`9a568e649c3a9d43b7f565ff2c835a24934ba447`。

执行以下命令启动服务：

```
java -jar bob-data-node/target/bob-data-node-1.0.jar -iceCfgFile=./执行以下命令启动服务： -walletPath=60ceca9c1290ee56b98d4e160ef0453f7c40d219 -walletPass=11111111
```

### 开启Alice数据节点

切换到工作目录：`Contract-Example/privacy-contract/bidding`，修改配置文件如下：

```bash
#
# The client reads this property to create the reference to the
# "TaskCallback" object in the server.
#
TaskCallback.Proxy=tasksession:default -h localhost -p 10001
```

拷贝`0xce3a4aa58432065c4c5fae85106aee4aef77a115`地址对应钱包文件，比如命名为：`ce3a4aa58432065c4c5fae85106aee4aef77a115`。

执行以下命令启动服务：

```
java -jar alice-data-node/target/alice-data-node-1.0.jar -iceCfgFile=./执行以下命令启动服务： -walletPath=ce3a4aa58432065c4c5fae85106aee4aef77a115 -walletPass=11111111
```

### 发起bidding计算

切换到工作目录：`Contract-Example/privacy-contract/bidding`，执行以下命令：

```bash
java -jar dapp/target/dapp-1.0.jar --walletPath=ce3a4aa58432065c4c5fae85106aee4aef77a115 --walletPass=111111 --url="http://localhost:6789" --contractAddress="0x43355c787c50b647c425f594b441d4bd75198888" --api="startCalc(BidEvaluationResult)"
```

执行成功得到一个交易，例如：

```bash
0x45655c787c50b647c425f594b441d4bd75198888
```

由于隐私计算开始执行需要一定的时间，需要等待大约20s左右，然后查询结果：

```bash
java -jar dapp/target/dapp-1.0.jar --walletPath=ce3a4aa58432065c4c5fae85106aee4aef77a115 --walletPass=111111 --url="http://localhost:6789" --contractAddress="0x43355c787c50b647c425f594b441d4bd75198888" --api="getResultByTransactionID(45655c787c50b647c425f594b441d4bd75198888)" --returnType=int --method=BidEvaluationResult
```

