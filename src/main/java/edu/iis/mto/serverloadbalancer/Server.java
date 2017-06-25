package edu.iis.mto.serverloadbalancer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grusz on 25.06.2017.
 */
public class Server {
    public static final double MAXIMUM_LOAD = 100.0d;
    public double currentLoadPercentage;
    public int capacity;
    private List<Vm> vms = new ArrayList<Vm>();

    public Server(int capacity) {
        this.capacity = capacity;
    }

    public boolean contains(Vm theVm) {
        return vms.contains(theVm);
    }

    public void addVm(Vm vm){
        currentLoadPercentage += calculateLoad(vm);
        vms.add(vm);
    }

    private double calculateLoad(Vm vm) {
        return (double) vm.size / (double) capacity * MAXIMUM_LOAD;
    }

    public int countVms() {
        return vms.size();
    }

    public boolean canFit(Vm vm) {
        if((currentLoadPercentage + calculateLoad(vm)) <= MAXIMUM_LOAD){
            return true;
        }else {
            return false;
        }
    }

    public double getCurrentLoadPercentage() {
        return currentLoadPercentage;
    }

    public void setCurrentLoadPercentage(double currentLoadPercentage) {
        this.currentLoadPercentage = currentLoadPercentage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
