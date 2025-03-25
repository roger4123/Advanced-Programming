package Bonus;

import Compulsory.Classes.*;
import Homework.*;
import java.time.LocalTime;
import java.util.*;

public class TestEquitableSchedule {
    public static void main(String[] args) {

        Airliner a1 = new Airliner("Boeing 737", "AB123", 35.8, 180);
        Airliner a2 = new Airliner("Boeing 679", "CD456", 35.8, 150);
        Freighter f1 = new Freighter("Boeing 737F", "EF789", 64.8, 217.8);
        Freighter f2 = new Freighter("Boeing 679F", "GH123", 60.8, 220.1);
        Drone d1 = new Drone("Amazon Prime", "DR001", 2.8, 0.4, 80);
        Drone d2 = new Drone("Medical Delivery", "DR002", 2.5, 0.3, 60);

        List<Aircraft> aircrafts = Arrays.asList(a1, a2, f1, f2, d1, d2);

        System.out.println("TEST 1: Original Flight Schedule");
        Airport airport = new Airport("International Iasi Airport", 4);

        Set<Flight> flights = new HashSet<>();
        flights.add(new Flight(a1, 1, LocalTime.of(9,15), LocalTime.of(9,40)));
        flights.add(new Flight(a2, 2, LocalTime.of(9,10), LocalTime.of(9,25)));
        flights.add(new Flight(f1, 3, LocalTime.of(9,20), LocalTime.of(9,40)));
        flights.add(new Flight(f2, 4, LocalTime.of(9,5), LocalTime.of(9,20)));
        flights.add(new Flight(d2, 5, LocalTime.of(9,30), LocalTime.of(9,35)));
        flights.add(new Flight(d1, 6, LocalTime.of(9,25), LocalTime.of(9,30)));
        flights.add(new Flight(a1, 7, LocalTime.of(10,15), LocalTime.of(10,25)));
        flights.add(new Flight(a2, 8, LocalTime.of(10,10), LocalTime.of(10,30)));

        EquitableSchedule scheduler = new EquitableSchedule(airport, flights);
        boolean success = scheduler.createEquitableSchedule();
        System.out.println("Equitable schedule created: " + success);
        scheduler.printSchedule();

        System.out.println("\n\nTEST 2: Random Flight Schedule (10 flights, 3 runways)");
        EquitableSchedule randomScheduler = EquitableSchedule.generateRandomSchedule(3, 10, aircrafts);
        success = randomScheduler.createEquitableSchedule();
        System.out.println("Equitable schedule created: " + success);
        randomScheduler.printSchedule();

        System.out.println("\n\nTEST 3: Larger Scale Test (20 flights, 5 runways)");
        EquitableSchedule largeScheduler = EquitableSchedule.generateRandomSchedule(5, 20, aircrafts);
        success = largeScheduler.createEquitableSchedule();
        System.out.println("Equitable schedule created: " + success);
        largeScheduler.printSchedule();

        // too many flights in same time slot
        System.out.println("\n\n==== TEST 4: Challenging Schedule ====");
        Airport smallAirport = new Airport("Small Airport", 2);

        Set<Flight> conflictingFlights = new HashSet<>();
        // 6 flights all between 9:00 and 9:30 => impossible to schedule equitably on 2 runways
        conflictingFlights.add(new Flight(a1, 1, LocalTime.of(9, 0), LocalTime.of(9,15)));
        conflictingFlights.add(new Flight(a2, 2, LocalTime.of(9, 5), LocalTime.of(9,20)));
        conflictingFlights.add(new Flight(f1, 3, LocalTime.of(9,10), LocalTime.of(9,25)));
        conflictingFlights.add(new Flight(f2, 4, LocalTime.of(9,15), LocalTime.of(9,30)));
        conflictingFlights.add(new Flight(d1, 5, LocalTime.of(9, 0), LocalTime.of(9,10)));
        conflictingFlights.add(new Flight(d2, 6, LocalTime.of(9,20), LocalTime.of(9,30)));

        EquitableSchedule challengingScheduler = new EquitableSchedule(smallAirport, conflictingFlights);
        success = challengingScheduler.createEquitableSchedule();
        System.out.println("Equitable schedule created: " + success);
        challengingScheduler.printSchedule();
    }
}