package Compulsory.Classes;

import Compulsory.Interfaces.CargoCapable;

public class Airport {
    private String name;
    private Aircraft[] registeredAircrafts;
    private int aircraftCount;
    private final int MAX_AIRCRAFT_COUNT = 100;

    public Airport(String name) {
        this.name = name;
        this.registeredAircrafts = new Aircraft[MAX_AIRCRAFT_COUNT];
        this.aircraftCount = 0;
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
}
