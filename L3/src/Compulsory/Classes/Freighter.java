package Compulsory.Classes;

import Compulsory.Interfaces.CargoCapable;

public class Freighter extends Aircraft implements CargoCapable{
    private double loadCapacity;

    public Freighter(String model, String callSign, double wingspan, double loadCapacity) {
        super(model, callSign, wingspan);
        this.loadCapacity = loadCapacity;
    }


    @Override
    public double getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public void setLoadCapacity(double loadCapacity){
        this.loadCapacity = loadCapacity;
    }

    @Override
    public String toString() {
        return "Freighter{" +
                "loadCapacity=" + loadCapacity +
                '}';
    }
}
