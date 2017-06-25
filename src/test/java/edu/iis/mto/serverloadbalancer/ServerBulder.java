package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServerBulder implements Builder<Server>{
    private int capacity;
    private double initalLoad;

    public ServerBulder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        Server server = new Server(capacity);
        if(initalLoad > 0){
            int initialVmSize = (int) (initalLoad /(double)capacity * 100.0d);
            Vm initalVm = VmBuilder.vm().ofSize(initialVmSize).build();
            server.addVm(initalVm);
        }
        return server;
    }

    public static ServerBulder server() {
        return new ServerBulder();
    }

    public Server a(ServerBulder serverBulder) {
        return serverBulder.build();
    }

    public ServerBulder withCurrentLoadOf(double initalLoad) {
        this.initalLoad = initalLoad;
        return this;
    }
}
