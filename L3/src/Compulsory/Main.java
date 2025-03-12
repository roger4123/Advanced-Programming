package Compulsory;

import Compulsory.Classes.*;

public class Main {
    public static void main(String[] args) {
        Airport airport = new Airport("International Iasi Airport");

        Airliner a1 = new Airliner("Boeing 737", "AB123", 35.8, 180);
        Airliner a2 = new Airliner("Boeing 679", "CD456", 35.8, 150);
        Freighter f1 = new Freighter("Boeing 737F", "EF789", 64.8, 217.8);
        Freighter f2 = new Freighter("Boeing 679F", "GH123", 60.8, 220.1);
        Drone d1 = new Drone("Amazon Prime", "DR001", 2.8, 0.4, 80);
        Drone d2 = new Drone("Medical Delivery", "DR002", 2.5, 0.3, 60);

        airport.registerAircraft(a1);
        airport.registerAircraft(a2);
        airport.registerAircraft(f1);
        airport.registerAircraft(f2);
        airport.registerAircraft(d1);
        airport.registerAircraft(d2);

        Aircraft[] cargoAircrafts = airport.getCargoAircrafts();
        System.out.println("Cargo Aircrafts:");
        for (Aircraft aircraft : cargoAircrafts) {
            System.out.println("- " + aircraft);
        }
    }
}