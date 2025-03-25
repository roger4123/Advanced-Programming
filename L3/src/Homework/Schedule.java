package Homework;

import Compulsory.Classes.Aircraft;

import java.time.LocalTime;
import java.util.*;

public class Schedule {
    private Airport airport;
    private Set<Flight> flights;
    private Map<Flight,Runway> solution;
    private boolean isSolution;

    public Schedule(Airport airport, Set<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
        this.solution = new HashMap<Flight,Runway>();
        this.isSolution = false;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
        if(isSolution) {
            solution.clear();
            isSolution = false;
        }
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public Airport getAirport() {
        return airport;
    }

    public Map<Flight, Runway> getSolution() {
        return isSolution ? solution : null;
    }

    public boolean solve() {
        solution.clear();
        isSolution = false;

        System.out.println("Starting to solve schedule for " + flights.size() + " flights on " +
                airport.getRunways().size() + " runways");

        List<Flight> flightList = new ArrayList<>(flights);
        flightList.sort(Comparator.comparing(Flight::getStartTime));

        List<Runway> runways = new ArrayList<>(airport.getRunways());

        if (runways.isEmpty()) {
            System.out.println("Error: No runways available");
            return false;
        }

        // Track which flights are scheduled on each runway
        Map<Runway,List<Flight>> runwayFlights = new HashMap<>();
        for(Runway runway : runways) {
            runwayFlights.put(runway, new ArrayList<>());
        }

        for(Flight flight : flightList) {
            boolean scheduled = false;

            for(Runway runway : runways) {
                List<Flight> scheduledFlights = runwayFlights.get(runway);
                boolean conflict = false;

                for(Flight scheduledFlight : scheduledFlights) {
                    if(flight.inConflict(scheduledFlight)) {
                        conflict = true;
                        break;
                    }
                }

                if(!conflict) {
                    runwayFlights.get(runway).add(flight);
                    solution.put(flight, runway);
                    scheduled = true;
                    break;
                }
            }

            if(!scheduled) {
                System.out.println("Failed to schedule flight " + flight.getFlightNumber() +
                        " from " + flight.getStartTime() + " to " + flight.getEndTime());
                solution.clear();
                return false;
            }
        }

        isSolution = true;
        System.out.println("Successfully scheduled all " + flights.size() + " flights");
        return true;
    }

    public boolean validateSolution() {
        if (!isSolution || solution.size() != flights.size())
            return false;

        Map<Runway,List<Flight>> runwayFlights = new HashMap<>();
        for(Map.Entry<Flight,Runway> entry: solution.entrySet()) {
            Flight flight = entry.getKey();
            Runway runway = entry.getValue();

            runwayFlights.putIfAbsent(runway, new ArrayList<>());
            List<Flight> flightsOnRunway = runwayFlights.get(runway);

            for(Flight anotherFlightOnRunway : flightsOnRunway) {
                if(flight.inConflict(anotherFlightOnRunway)) {
                    return false;
                }
            }

            flightsOnRunway.add(flight);
        }
        return true;
    }

    public void printSolution() {
        if(isSolution) {
            System.out.println("Schedule Solution for " + airport.getName() + ":");

            Map<Runway,List<Flight>> runwayFlights = new HashMap<>();
            for(Map.Entry<Flight,Runway> entry: solution.entrySet()) {
                runwayFlights.putIfAbsent(entry.getValue(), new ArrayList<>());
                runwayFlights.get(entry.getValue()).add(entry.getKey());
            }

            for(List<Flight> flightsOnRunway: runwayFlights.values()) {
                flightsOnRunway.sort(Comparator.comparing(Flight::getStartTime));
            }

            for(Runway runway: airport.getRunways()) {
                System.out.println("\nRunway " + runway.getRunwayId() + ":");
                List<Flight> flightsOnRunway = runwayFlights.getOrDefault(runway, Collections.emptyList());
                if(flightsOnRunway.isEmpty()) {
                    System.out.println("No flights found for runway " + runway.getRunwayId());
                }
                else {
                    for(Flight flight: flightsOnRunway) {
                        System.out.println("Flight " + flight.getFlightNumber() +
                                " from " + flight.getStartTime() + " to " + flight.getEndTime());
                    }
                }
            }
        }
        System.out.println();
    }

    public int minimumRunwaysRequired() {
        List<Flight> flightList = new ArrayList<>(flights);
        flightList.sort(Comparator.comparing(Flight::getStartTime));

        PriorityQueue<LocalTime> runwayQueue = new PriorityQueue<>();
        int maxRunways = 0;

        for (Flight flight : flightList) {
            while (!runwayQueue.isEmpty() && runwayQueue.peek().isBefore(flight.getStartTime())) {
                runwayQueue.poll();
            }
            runwayQueue.add(flight.getEndTime());
            maxRunways = Math.max(maxRunways, runwayQueue.size());
        }
        return maxRunways;
    }

    public void adjustLandingTimes() {
        List<Flight> flightList = new ArrayList<>(flights);
        flightList.sort(Comparator.comparing(Flight::getStartTime));

        for (int i = 1; i < flightList.size(); i++) {
            Flight prev = flightList.get(i - 1);
            Flight curr = flightList.get(i);
            if (curr.getStartTime().isBefore(prev.getEndTime())) {
                curr.setStartTime(prev.getEndTime().plusMinutes(5));
                curr.setEndTime(curr.getStartTime().plusMinutes(15));
            }
        }
    }

    public void generateRandomFlights(int numFlights, List<Aircraft> aircraftList) {
        Random random = new Random();
        for (int i = 0; i < numFlights; i++) {
            LocalTime startTime = LocalTime.of(9 + random.nextInt(3), random.nextInt(60));
            LocalTime endTime = startTime.plusMinutes(10 + random.nextInt(20));
            Aircraft aircraft = aircraftList.get(random.nextInt(aircraftList.size()));
            Flight flight = new Flight(aircraft, i + 1, startTime, endTime);
            addFlight(flight);
        }
    }
}
