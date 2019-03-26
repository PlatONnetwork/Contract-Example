package client;

import net.platon.vm.sdk.client.ConfigInfo;
import net.platon.vm.sdk.client.AppClient;

public class Client1 {

    public static void main(String[] args) {
        (new AppClient(new ConfigInfo(args))).start(args);
    }
}
