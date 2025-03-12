package Compulsory.Classes;

import Compulsory.Interfaces.CargoCapable;
import Compulsory.Interfaces.PassengerCapable;

public abstract class Aircraft implements Comparable<Aircraft> {
    private String model;
    private String callSign;
    private double wingSpan;

    public Aircraft(String model, String callSign, double wingSpan) {
        this.model = model;
        this.callSign = callSign;
        this.wingSpan = wingSpan;
    }

    public String getModel() {
        return model;
    }

    public String getCallSign() {
        return callSign;
    }

    public double getWingSpan() {
        return wingSpan;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public void setWingSpan(double wingSpan) {
        this.wingSpan = wingSpan;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "model='" + model + '\'' +
                ", callSign='" + callSign + '\'' +
                ", wingSpan=" + wingSpan +
                '}';
    }

    @Override
    public int compareTo(Aircraft other) {
        return this.callSign.compareTo(other.callSign);
    }
}
