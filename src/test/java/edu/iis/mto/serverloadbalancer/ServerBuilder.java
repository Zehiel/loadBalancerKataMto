package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServerBuilder implements Builder<Server> {
    private int capacity;
    private double initalLoad;

    public ServerBuilder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        Server server = new Server(capacity);
        if(initalLoad > 0){
            int initalVmSize = (int) (initalLoad/(double)capacity * 100.0d);
            Vm initalVm = VmBuilder.vm().ofSize(initalVmSize).build();
            server.addVm(initalVm);

        }
        return server;
    }

    public static ServerBuilder server() {
        return new ServerBuilder();
    }

    public Builder<Server> withCurrentLoad(double initalLoad) {

        this.initalLoad = initalLoad;
        return this;
    }
}
