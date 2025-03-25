package Compulsory;

import Compulsory.Classes.*;
import Homework.Airport;
import Homework.Flight;
import Homework.Runway;
import Homework.Schedule;

import java.time.LocalTime;
import java.util.*;


public class Main {
    public static <List> void main(String[] args) {
        Airport airport = new Airport("International Iasi Airport", 4);

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

        java.util.List<Flight> flights = new ArrayList<>();
        flights.add(new Flight(a1, 1, LocalTime.of(9,15), LocalTime.of(9,40)));
        flights.add(new Flight(a2, 2, LocalTime.of(9,10), LocalTime.of(9,25)));
        flights.add(new Flight(f1, 3, LocalTime.of(9,20), LocalTime.of(9,40)));
        flights.add(new Flight(f2, 4, LocalTime.of(9,5), LocalTime.of(9,20)));
        flights.add(new Flight(d2, 5, LocalTime.of(9,30), LocalTime.of(9,35)));
        flights.add(new Flight(d1, 6, LocalTime.of(9,25), LocalTime.of(9,30)));
        flights.add(new Flight(a1, 7, LocalTime.of(10,15), LocalTime.of(10,25)));
        flights.add(new Flight(a2, 8, LocalTime.of(10,10), LocalTime.of(10,30)));


        Set<Flight> flightSet = new HashSet<>(flights);
        Schedule problem = new Schedule(airport, flightSet);
        for (Flight flight : flights) {
            problem.addFlight(flight);
        }

        boolean solved = problem.solve();
        if (solved) {
            System.out.println("\nSuccesfully created a schedule!");
            System.out.println("\nValidating solution: " + (problem.validateSolution() ? "Valid\n" : "Invalid\n"));

            problem.printSolution();
            Map<Flight, Runway> solution = problem.getSolution();
            for(Map.Entry<Flight, Runway> entry : solution.entrySet()) {
                System.out.println("Flight: " + entry.getKey().getFlightNumber() + " -> Runway: " + entry.getValue().getRunwayId());
            }
        }
        else {
            System.out.println("No solution found!");
        }

    }
}