package edu.iis.mto.serverloadbalancer;

/**
 * Created by grusz on 25.06.2017.
 */
public class Server {
    public double currentLoadPercentage;
    public int capacity;

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return true;
    }
}
