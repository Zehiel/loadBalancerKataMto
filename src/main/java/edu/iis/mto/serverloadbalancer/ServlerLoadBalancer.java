package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServlerLoadBalancer {
    public void balance(Server[] servers, Vm[] vms) {

        for(Vm vm : vms){
            servers[0].addVm(vm);
        }
    }
}
