[TOC]
# Bidding privacy contract development on Linux


This example uses privacy contract to implement privacy bidding, including privacy contract implementation, data node implementation, and `Dapp` sample. The bidding method is: secondary weighted average bid evaluation.

> ***This example requires running on Ubuntu 16.04.***

## Second weighted average bid evaluation

Suppose there are three bidders A/B/C, which are quoted a/b/c respectively, and the bidder's P bid base price is p, and the bid evaluation is carried out by `Dapp`. The bid evaluation method is as follows:
Average price of y = ( a + b + c ) / 3
Bid evaluation value z = ( y + p) / 2
The bid price is lower than (including equal to) z value, and the closest one is the winning bidder.

## Environmental requirements

### Hardware requirements

- CPU architecture

At present, the official only supports the `Intel`2 generation and above `CPU` architecture. Before setting up the environment, it is necessary to check whether the machine meets the minimum requirements.

```bash
$ cat /proc/cpuinfo
```

For example, the following output :

```bash
Model name : Intel(R) Xeon(R) CPU E5-2620 v2 @ 2.10GHz
```

Among them, `E5-2620 v2` indicates that the `CPU` is Intel's third-generation architecture, which meets the requirements.

> **CPU architecture must meet Intel v2 or above, otherwise it will not work properly**

### Software requirements

- Ubuntu16.04

The specified operating system is Ubuntu 16.04.

- JDK1.8+

Please refer to: [oracle official website](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html), install and configure the environment variables `CLASSPATH`, `PATH`, `JAVA_HOME`.

- Maven3.3.9+

Please refer to the [maven official website](http://maven.apache.org/download.cgi) to complete the installation.

## Building a privacy computing network
A privacy computing network with two computing nodes is built, where the computing nodes are all `PlatON` node clients.

### Installing the PlatON node client

Install the `platon` node client with MPC functionality:

```bash
# add PPA
$ sudo add-apt-repository ppa:platonnetwork/platon
$ sudo apt-get update

# install platon with mpc
$ sudo apt-get -f install platon-all
```
After the installation, the executable files such as  `platon`, `ethkey` will be installed to `/usr/bin`.

### Building a private network

Reference [PlatON Cluster Environment](zh-cn/basics/[Chinese-Simplified]-%e7%a7%81%e6%9c%89%e7%bd%91%e7%bb%9c#PlatON+%e9%9b% 86%e7%be%a4%e7%8e%af%e5%a2%83) to build a private network and connect compute nodes to the private network.

### Creating two wallet for data nodes

The privacy calculation consists of two computing participants, each of them creates a wallet file as follows:

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

The above commands perform two operations to create wallets,  and we got two wallet addresses as follows:

> `0x9a568e649c3a9d43b7f565ff2c835a24934ba447`

> `0xce3a4aa58432065c4c5fae85106aee4aef77a115`

### Turning on MPC function

Refer to [Enable MPC Calculation Function](zh-cn/basics/[Chinese-Simplified]-%e7%a7%81%e6%9c%89%e7%bd%91%e7%bb%9c#%e4%b8% Ba%e8%8a%82%e7%82%b9%e5%90%af%e7%94%a8MPC%e5%8a%9f%e8%83%bd), when restarting `platon`, will be `-mpc The .actor` corresponding configuration is the data node wallet address generated in the previous step: `0x9a568e649c3a9d43b7f565ff2c835a24934ba447`, `0xce3a4aa58432065c4c5fae85106aee4aef77a115`.

## Trying privacy bidding

### Downloading source
Clone [PlatON Contract Examples](https://github.com/PlatONnetwork/Contract-Example.git) `github` repository:
```bash
$ git clone https://github.com/PlatONnetwork/Contract-Example.git
```
### Installing privacy contract development tools
#### Downloading package
Download the privacy contract development tool [installation package](https://download.platon.network/0.5/platon-ubuntu-amd64-mpc-toolkit.tar.gz):
```bash
$ wget https://download.platon.network/0.5/platon-ubuntu-amd64-mpc-toolkit.tar.gz
$ tar -xvzf platon-ubuntu-amd64-mpc-toolkit.tar.gz
```
After executing the above command, the privacy contract development tool is installed into the `platon-mpc-toolkit` working directory. The directory structure is as follows:

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
Description:
- **compiler**
  Privacy contract compilation script
- **ctool**
  Privacy contract deploy tool
- **install-deps.sh**
  Third-party dependent installation script
- **create_testnet_wallet.sh**
  A  tool script to create a wallet
- **mpc**
  `MPC` related toolset
- **pwas**
  `pWASM` related toolset
- **sample**
  Privacy contract samples
- **config.json.template**
  Configuration template of `compiler`
- **ctool.config.json.template**
  Configuration template of `ctool`

Switch to the `platon-mpc-toolkit` directory and install the third-party set dependencies:
```bash
$ bash install-deps.sh
```

#### compiler usage
**usage:**
```bash
$ bash ./compiler -c {your config file} -i {your privacy contract} -o {output directory}
```
**Parameter Description:**
* -c: privacy contract file
* -o: output directory
* -i: compile the configuration file
* -p:`protobuf` definition file

### Compiling the privacy contract

Copy the bidding privacy contract (`Contract-Example/privacy-contract/bidding/contract/BidEvaluation.cpp`) of the clone repository to `platon-mpc-toolkit/` to compile the privacy contract:
```bash
$ cd platon-mpc-toolkit
$ bash compiler -c config.json.template -i BidEvaluation.cpp -o ./user
```
After execution, it will generate compiled contracts and Java core classes in the `user/`, which is as follows:
```bash
user/
├── MPCBidEvaluation.java         # MPC data node core class
├── MPCBidEvaluation-README.TXT
├── ProxyBidEvaluation.java       # dapp proxy core class
├── ProxyBidEvaluation-README.TXT
├── BidEvaluation.cpp.abi.json    # abi file of pwam 
└── BidEvaluation.wasm	          # compiled binary of pwasm
```

- **MPCBidEvaluation.java**

The data node core class `MPCBidEvaluation.java` is the same as `Contract-Example/privacy-contract/bidding/bob-data-node/src/main/java/net/platon/vm/mpc/MPCBidEvaluation.java`, which is  for registration and participating to MPC calculation.

- **ProxyBidEvaluation.java**

The `Dapp` proxy client class `ProxyBidEvaluation.java` is the same as `Contract-Example/privacy-contract/bidding/dapp/src/main/java/platon/mpc/proxy/ProxyBidEvaluation.java`, whchi is used to initiate privacy calculations.

- **BidEvaluation.wasm**

Bidding private contract binary for privacy contract.

- **BidEvaluation.cpp.abi.json**

The bidding privacy contract `abi`, which is the description of the `pWASM` is used for the release of privacy contracts.

### Publishing a privacy contract

Switch the working directory to the privacy contract development kit installation directory `platon-mpc-toolkit/`. Perform the following steps:

1. Write a configuration file
Create configuration file `ctool.config.json` as follows:
```bash
{
"url": "http://127.0.0.1:6789",
"gas": "0x99999788888",
"gasPrice": "0x333330",
"from":"0x9a568e649c3a9d43b7f565ff2c835a24934ba447"
}
```
2. Temporarily unlock the account
```bash
$ platon attach http://localhost:6789
#enter rpc console
> personal.unlockAccount('0x9a568e649c3a9d43b7f565ff2c835a24934ba447')
```
3. Release contract
```
./ctool deploy --abi ./user/BidEvaluation.cpp.abi.json --code ./user/BidEvaluation.wasm --config ./config.json
```

After the deployment,  for our example the deployed contract address is:

> 0x43355c787c50b647c425f594b441d4bd75198888

### Compiling mvn project

Switch the working directory to `Contract-Example/privacy-contract/bidding`, compile the data node program and the `Dapp` service respectively. The commands are as follows:

```bash
$ cd bob-data-node
$ mvn package

$ cd ../alice-data-node
$ mvn package

$ cd ../dapp
$ mvn package
```

After Executing the command, it will generates the following `Java jar`:

```bash
bob-data-node/target/bob-data-node-1.0.jar
alice-data-node/target/alice-data-node-1.0.jar
dapp/target/dapp-1.0.jar
```

### Running Bob data node

Switch the working directory  to `Contract-Example/privacy-contract/bidding`, modify the configuration file as follows:

```bash
#
# The client reads this property to create the reference to the
# "TaskCallback" object in the server.
# 10002 is the listenning port of the node
TaskCallback.Proxy=tasksession:default -h localhost -p 10002
```

Copy the wallet file of address `0x9a568e649c3a9d43b7f565ff2c835a24934ba447` , for example we named the file `9a568e649c3a9d43b7f565ff2c835a24934ba447`.

Execute the following command to start the `Bob data node` service:

```
java -jar bob-data-node/target/bob-data-node-1.0.jar -iceCfgFile=./Execute the following command to start the service: -walletPath=60ceca9c1290ee56b98d4e160ef0453f7c40d219 -walletPass=11111111
```

### Running Alice data node

Switch to the working directory: `Contract-Example/privacy-contract/bidding`, modify the configuration file as follows:

```bash
#
# The client reads this property to create the reference to the
# "TaskCallback" object in the server.
#
TaskCallback.Proxy=tasksession:default -h localhost -p 10001
```

Copy the wallet file of address `0xce3a4aa58432065c4c5fae85106aee4aef77a115`, for example we named the file `ce3a4aa58432065c4c5fae85106aee4aef77a115`.

Execute the following command to start the `Alice data node` service:

```
java -jar alice-data-node/target/alice-data-node-1.0.jar -iceCfgFile=./Execute the service by executing the following command: -walletPath=ce3a4aa58432065c4c5fae85106aee4aef77a115 -walletPass=11111111
```

### Initiating bidding calculation

Switch to the working directory `Contract-Example/privacy-contract/bidding` and execute the following command:

```bash
java -jar dapp/target/dapp-1.0.jar --walletPath=ce3a4aa58432065c4c5fae85106aee4aef77a115 --walletPass=11111111 --url = "http:// localhost:6789" --contractAddress="0x43355c787c50b647c425f594b441d4bd75198888" --api="startCalc(BidEvaluationResult)"
```

Successfully get a transaction, for example:

```bash
0x45655c787c50b647c425f594b441d4bd75198888
```

Since the privacy calculation take a certain amount of time to get start, it takes about 20s to wait, and then we try to get the result as follows:

```bash
java -jar dapp /target/dapp-1.0.jar --walletPath=ce3a4aa58432065c4c5fae85106aee4aef77a115 --walletPass=11111111 --url="http://localhost:6789" --contractAddress="0x43355c787c50b647c425f594b441d4bd75198888" --api = "getResultByTransactionID (45655c787c50b647c425f594b441d4bd75198888 )" --returnType=int --method=BidEvaluationResult
```

Finally we get bidding result, for example:
```bash
18000
```

During the bidding, the bidders' input are secure and will not reveal to any peers by the power of MPC protocol. Here comes the ending privacy bidding. Enjoy :)