package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServlerLoadBalancer {
    public void balance(Server[] servers, Vm[] vms) {

        for(Vm vm : vms){
            Server lessLoadedServer = null;
            for(Server server : servers){
                if(lessLoadedServer == null || server.currentLoadPercentage < lessLoadedServer.currentLoadPercentage){
                    lessLoadedServer = server;
                }

            }
            System.out.println(vm);
            lessLoadedServer.addVm(vm);
        }
    }
}
