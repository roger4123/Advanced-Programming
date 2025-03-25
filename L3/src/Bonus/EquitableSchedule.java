package Bonus;

import Compulsory.Classes.Aircraft;
import Homework.*;

import java.time.LocalTime;
import java.util.*;

public class EquitableSchedule {
    private final Airport airport;
    private final Set<Flight> flights;
    private final Map<Runway, List<Flight>> runwayAssignments;
    private boolean isSolved;

    public EquitableSchedule(Airport airport, Set<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
        this.runwayAssignments = new HashMap<>();
        this.isSolved = false;

        for (Runway runway : airport.getRunways()) {
            runwayAssignments.put(runway, new ArrayList<>());
        }
    }

    public boolean createEquitableSchedule() {
        int totalRunways = airport.getRunways().size();
        int totalFlights = flights.size();

        // calculate the ideal number of flights per runway
        int baseFlightsPerRunway = totalFlights / totalRunways;
        int remainingFlights = totalFlights % totalRunways;

        System.out.println("Creating equitable schedule with " + totalFlights + " flights on " + totalRunways + " runways");
        System.out.println("Target: " + baseFlightsPerRunway + " flights per runway" + (remainingFlights > 0 ? " (with " + remainingFlights + " runways handling one extra)" : ""));

        for (List<Flight> runwayFlights : runwayAssignments.values()) {
            runwayFlights.clear();
        }

        // sort flights by duration
        List<Flight> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparingLong(f -> java.time.Duration.between(f.getStartTime(), f.getEndTime()).toMinutes()));

        // try to assign flights equitably
        boolean success = assignFlightsEquitably(sortedFlights, baseFlightsPerRunway, remainingFlights);
        if (success) {
            isSolved = true;
            return true;
        } else {
            // determine minimum runways needed
            int minimumRunwaysNeeded = calculateMinimumRunwaysNeeded();
            if(minimumRunwaysNeeded != totalRunways) {
                System.out.println("Equitable assignment not possible with current runways.");
                System.out.println("Minimum runways needed: " + minimumRunwaysNeeded);
            } else {
                System.out.println("Too many conflicting flights with current runways.");
                System.out.println("Adjust landing intervals to minimum runways needed: " + minimumRunwaysNeeded);
            }

            if (minimumRunwaysNeeded > totalRunways) {
                System.out.println("Need " + (minimumRunwaysNeeded - totalRunways) + " additional runways for equitable assignment.");
            }

            // try to adjust flight landing times
            boolean adjustedSuccess = adjustLandingTimesAndSchedule();

            if (adjustedSuccess) {
                System.out.println("Successfully created equitable schedule by adjusting landing times.");
                isSolved = true;
                return true;
            }

            return false;
        }
    }

    private boolean assignFlightsEquitably(List<Flight> sortedFlights, int baseFlightsPerRunway, int extraFlights) {
        List<Runway> runways = new ArrayList<>(airport.getRunways());

        // create target counts for each runway
        Map<Runway, Integer> targetCounts = new HashMap<>();
        for (int i = 0; i < runways.size(); i++) {
            targetCounts.put(runways.get(i), baseFlightsPerRunway + (i < extraFlights ? 1 : 0));
        }

        // try to place flights on runways that haven't reached their target
        for (Flight flight : sortedFlights) {
            boolean placed = false;

            // sort runways by current assignment count
            runways.sort(Comparator.comparingInt(r -> runwayAssignments.get(r).size()));

            for (Runway runway : runways) {
                // skip runways that have reached their target
                if (runwayAssignments.get(runway).size() >= targetCounts.get(runway)) {
                    continue;
                }

                // check if flight conflicts with any already assigned flights
                boolean hasConflict = false;
                for (Flight assignedFlight : runwayAssignments.get(runway)) {
                    if (flight.inConflict(assignedFlight)) {
                        hasConflict = true;
                        break;
                    }
                }

                if (!hasConflict) {
                    runwayAssignments.get(runway).add(flight);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                // try any available runway
                for (Runway runway : runways) {
                    boolean hasConflict = false;
                    for (Flight assignedFlight : runwayAssignments.get(runway)) {
                        if (flight.inConflict(assignedFlight)) {
                            hasConflict = true;
                            break;
                        }
                    }

                    if (!hasConflict) {
                        runwayAssignments.get(runway).add(flight);
                        placed = true;
                        break;
                    }
                }

                if (!placed) {
                    return false;
                }
            }
        }

        // check if we achieved equitable distribution
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (List<Flight> runwayFlights : runwayAssignments.values()) {
            min = Math.min(min, runwayFlights.size());
            max = Math.max(max, runwayFlights.size());
        }

        boolean isEquitable = (max - min <= 1);

        System.out.println("Schedule created. Min flights per runway: " + min + ", Max flights per runway: " + max);

        return isEquitable;
    }

    private int calculateMinimumRunwaysNeeded() {
        int totalFlights = flights.size();
        int minRunways = totalFlights;

        // try each possible number of runways from current to totalFlights
        for (int testRunways = airport.getRunways().size(); testRunways <= totalFlights; testRunways++) {
            int flightsPerRunway = (int) Math.ceil((double) totalFlights / testRunways);

            boolean possible = simulateScheduleWithRunways(testRunways, flightsPerRunway);
            if (possible) {
                minRunways = testRunways;
                break;
            }
        }

        return minRunways;
    }

    private boolean simulateScheduleWithRunways(int numRunways, int maxFlightsPerRunway) {
        // create temporary runways
        List<List<Flight>> tempRunways = new ArrayList<>();
        for (int i = 0; i < numRunways; i++) {
            tempRunways.add(new ArrayList<>());
        }

        List<Flight> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparing(Flight::getStartTime));

        // try to assign each flight
        for (Flight flight : sortedFlights) {
            boolean placed = false;

            for (List<Flight> runway : tempRunways) {
                if (runway.size() >= maxFlightsPerRunway) {
                    continue; // this runway has reached its limit
                }

                boolean hasConflict = false;
                for (Flight assignedFlight : runway) {
                    if (flight.inConflict(assignedFlight)) {
                        hasConflict = true;
                        break;
                    }
                }

                if (!hasConflict) {
                    runway.add(flight);
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                return false;
            }
        }

        return true;
    }

    private boolean adjustLandingTimesAndSchedule() {
        // make a copy of flights
        List<Flight> adjustableFlights = new ArrayList<>();
        for (Flight f : flights) {
            Flight copy = new Flight(f.getAircraft(), f.getFlightNumber(),
                    f.getStartTime(), f.getEndTime());
            adjustableFlights.add(copy);
        }

        adjustableFlights.sort(Comparator.comparing(Flight::getStartTime));

        // group flights by time periods
        Map<Integer, List<Flight>> hourlyFlights = new HashMap<>();

        for (Flight flight : adjustableFlights) {
            int hour = flight.getStartTime().getHour();
            hourlyFlights.putIfAbsent(hour, new ArrayList<>());
            hourlyFlights.get(hour).add(flight);
        }

        for (List<Flight> flightsInHour : hourlyFlights.values()) {
            spreadFlightsEvenly(flightsInHour);
        }

        // try scheduling again with adjusted flight times
        int totalRunways = airport.getRunways().size();
        int totalFlights = adjustableFlights.size();
        int baseFlightsPerRunway = totalFlights / totalRunways;
        int remainingFlights = totalFlights % totalRunways;

        return assignFlightsEquitably(adjustableFlights, baseFlightsPerRunway, remainingFlights);
    }

    // spreads out flight landing times within a time period to reduce conflicts
    private void spreadFlightsEvenly(List<Flight> flightsInPeriod) {
        if (flightsInPeriod.size() <= 1) return;

        flightsInPeriod.sort(Comparator.comparing(Flight::getStartTime));

        LocalTime periodStart = flightsInPeriod.getFirst().getStartTime();
        LocalTime periodEnd = flightsInPeriod.getLast().getEndTime();

        long periodMinutes = java.time.Duration.between(periodStart, periodEnd).toMinutes();

        // ensure we have at least 5 minutes between flights
        long idealSpacing = Math.max(5, periodMinutes / flightsInPeriod.size());

        LocalTime currentTime = periodStart;

        for (Flight flight : flightsInPeriod) {
            // calculate duration of this flight
            long flightDuration = java.time.Duration.between(flight.getStartTime(), flight.getEndTime()).toMinutes();

            // adjust start time
            flight.setStartTime(currentTime);
            flight.setEndTime(currentTime.plusMinutes(flightDuration));

            // update for next flight
            currentTime = currentTime.plusMinutes(idealSpacing);
        }
    }

    public static EquitableSchedule generateRandomSchedule(
            int numRunways, int numFlights, List<Aircraft> aircrafts) {

        Airport airport = new Airport("Test Airport", numRunways);
        Set<Flight> flights = new HashSet<>();
        Random random = new Random();

        LocalTime startOfDay = LocalTime.of(9, 0);

        for (int i = 0; i < numFlights; i++) {
            int minuteOffset = random.nextInt(8 * 60);
            LocalTime startTime = startOfDay.plusMinutes(minuteOffset);

            int duration = 10 + random.nextInt(21);
            LocalTime endTime = startTime.plusMinutes(duration);

            Aircraft aircraft = aircrafts.get(random.nextInt(aircrafts.size()));

            Flight flight = new Flight(aircraft, i + 1, startTime, endTime);
            flights.add(flight);
        }

        return new EquitableSchedule(airport, flights);
    }

    public void printSchedule() {
        if (!isSolved) {
            System.out.println("No valid schedule available.");
            return;
        }

        System.out.println("\nEquitable Schedule for " + airport.getName() + ":");

        for (Map.Entry<Runway, List<Flight>> entry : runwayAssignments.entrySet()) {
            Runway runway = entry.getKey();
            List<Flight> runwayFlights = entry.getValue();

            System.out.println("\nRunway " + runway.getRunwayId() + " (" + runwayFlights.size() + " flights):");

            runwayFlights.sort(Comparator.comparing(Flight::getStartTime));

            for (Flight flight : runwayFlights) {
                System.out.println("  Flight " + flight.getFlightNumber() + " (" + flight.getAircraft().getModel() + ") from " + flight.getStartTime() + " to " + flight.getEndTime());
            }
        }
    }

    public Map<Runway, List<Flight>> getRunwayAssignments() {
        return isSolved ? runwayAssignments : null;
    }
}
