package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServlerLoadBalancer {
    public void balance(Server[] servers, Vm[] vms) {

        for(Vm vm : vms){
            addToLessLoadedServer(servers, vm);
        }
    }

    private void addToLessLoadedServer(Server[] servers, Vm vm) {
        Server lessLoadedServer = findLessLoadedServer(servers);
        lessLoadedServer.addVm(vm);
    }

    private Server findLessLoadedServer(Server[] servers) {
        Server lessLoadedServer = null;
        for(Server server : servers){
            if(lessLoadedServer == null || server.currentLoadPercentage < lessLoadedServer.currentLoadPercentage){
                lessLoadedServer = server;
            }

        }
        return lessLoadedServer;
    }
}
