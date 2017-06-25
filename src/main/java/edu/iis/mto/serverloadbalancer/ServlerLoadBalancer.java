package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServlerLoadBalancer {
    public void balance(Server[] servers, Vm[] vms) {
        if(vms.length > 0){
            servers[0].addVm(vms[0]);
        }
    }
}
