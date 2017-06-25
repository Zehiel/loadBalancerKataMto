package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServerLoadBalancer {
    public void balance(Server[] servers, Vm[] vms) {

        for(Vm vm :vms){
            AddToLeastLoadedServer(servers, vm);
        }
    }

    private void AddToLeastLoadedServer(Server[] servers, Vm vm) {
        Server lessLoadedServer = findLeastLoadedServer(servers);
        lessLoadedServer.addVm(vm);
    }

    private Server findLeastLoadedServer(Server[] servers) {
        Server lessLoadedServer = null;
        for(Server server : servers){
            if(lessLoadedServer == null || server.currentLoadPercentage < lessLoadedServer.currentLoadPercentage){
                lessLoadedServer = server;
            }
        }
        return lessLoadedServer;
    }
}
