package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServerBulder implements Builder<Server>{
    private int capacity;

    public ServerBulder withCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Server build() {
        return new Server();
    }

    public static ServerBulder server() {
        return new ServerBulder();
    }

    public Server a(ServerBulder serverBulder) {
        return serverBulder.build();
    }
}
