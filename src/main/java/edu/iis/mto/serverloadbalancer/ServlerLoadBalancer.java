package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServlerLoadBalancer {
    public void balance(Server[] servers, Vm[] vms) {
        if(vms.length > 0){
            servers[0].currentLoadPercentage = (double) vms[0].size / (double)servers[0].capacity * 100.0d;
        }
    }
}
