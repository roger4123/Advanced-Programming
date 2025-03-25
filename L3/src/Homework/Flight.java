package Homework;

import Compulsory.Classes.Aircraft;

import java.time.LocalTime;
import java.util.Objects;

public class Flight {
    private int flightNumber;
    private Aircraft aircraft;
    private LocalTime startTime;
    private LocalTime endTime;
    private Runway assignedRunway;

    public Flight(Aircraft aircraft, int flightNumber, LocalTime startTime, LocalTime endTime) {
        this.aircraft = aircraft;
        this.flightNumber = flightNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignedRunway = null;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean inConflict(Flight flight) {
        return !(this.endTime.isBefore(flight.startTime) || this.startTime.isAfter(flight.endTime));
    }


    public Runway getAssignedRunway() {
        return assignedRunway;
    }

    public void setAssignedRunway(Runway assignedRunway) {
        this.assignedRunway = assignedRunway;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flightNumber=" + flightNumber +
                ", aircraft=" + aircraft +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return flightNumber == flight.flightNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber, aircraft, startTime, endTime);
    }
}
