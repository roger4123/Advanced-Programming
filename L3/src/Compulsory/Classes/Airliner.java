package Compulsory.Classes;

import Compulsory.Interfaces.PassengerCapable;

public class Airliner extends Aircraft implements PassengerCapable {
    private int passengerCapacity;

    public Airliner(String model, String callSign, double wingspan, int passengerCapacity) {
        super(model, callSign, wingspan);
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    @Override
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public String toString() {
        return "Airliner{" +
                "passengerCapacity=" + passengerCapacity +
                '}';
    }
}
