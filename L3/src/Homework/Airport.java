package Homework;

import Compulsory.Classes.Aircraft;
import Compulsory.Interfaces.CargoCapable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Airport {
    private String name;
    private Aircraft[] registeredAircrafts;
    private int aircraftCount;
    private final int MAX_AIRCRAFT_COUNT = 100;
    private List<Runway> runways;
    private List<Flight> scheduledFlights;

    public Airport(String name, int runwayCount) {
        this.name = name;
        this.registeredAircrafts = new Aircraft[MAX_AIRCRAFT_COUNT];
        this.aircraftCount = 0;
        this.runways = new ArrayList<Runway>();
        for (int i = 1; i <= runwayCount; i++) {
            runways.add(new Runway("R" + i));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public void setRunways(List<Runway> runways) {
        this.runways = runways;
    }

    public boolean registerAircraft(Aircraft aircraft) {
        if(aircraftCount >= MAX_AIRCRAFT_COUNT) {
            return false;
        }
        registeredAircrafts[aircraftCount] = aircraft;
        aircraftCount++;
        return true;
    }

    public Aircraft[] getCargoAircrafts() {
        int cargoCount = 0;
        for(int i = 0; i < aircraftCount; i++) {
            if(registeredAircrafts[i] instanceof CargoCapable) {
                cargoCount++;
            }
        }

        Aircraft[] cargoAircrafts = new Aircraft[cargoCount];
        int cargoIndex = 0;
        for(int i = 0; i < aircraftCount; i++) {
            if(registeredAircrafts[i] instanceof CargoCapable) {
                cargoAircrafts[cargoIndex++] = registeredAircrafts[i];
            }
        }
        return cargoAircrafts;
    }

    public boolean scheduleFlight(Flight flight) {
        for (Runway runway : runways) {
            if (runway.addFlight(flight)) {
                scheduledFlights.add(flight);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", registeredAircrafts=" + Arrays.toString(registeredAircrafts) +
                ", runways=" + runways +
                '}';
    }
}
