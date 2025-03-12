package Compulsory.Classes;

import Compulsory.Interfaces.CargoCapable;

import java.io.Serializable;

public class Drone extends Aircraft implements CargoCapable {
    private double loadCapacity;
    private int batteryCapacity;

    public Drone(String model, String callSign, double wingspan, double loadCapacity, int batteryCapacity) {
        super(model, callSign, wingspan);
        this.loadCapacity = loadCapacity;
        this.batteryCapacity = batteryCapacity;
    }


    @Override
    public double getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public String toString() {
        return "Drone{" +
                "loadCapacity=" + loadCapacity +
                ", batteryCapacity=" + batteryCapacity +
                '}';
    }
}
