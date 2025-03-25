package Homework;

import Compulsory.Classes.Aircraft;

import java.util.ArrayList;
import java.util.List;

public class Runway {
    private String runwayId;
    private List<Flight> scheduledFlights;

    public Runway(String runwayId) {
        this.runwayId = runwayId;
        this.scheduledFlights = new ArrayList<Flight>();
    }

    public String getRunwayId() {
        return runwayId;
    }

    public List<Flight> getScheduledFlights() {
        return scheduledFlights;
    }

    public void setRunwayId(String runwayId) {
        this.runwayId = runwayId;
    }

    public void setScheduledFlights(List<Flight> scheduledFlights) {
        this.scheduledFlights = scheduledFlights;
    }

    public boolean addFlight(Flight flight) {
        for (Flight f : scheduledFlights) {
            if (flight.inConflict(f)) {
                return false;
            }
        }

        scheduledFlights.add(flight);
        flight.setAssignedRunway(this);
        return true;
    }
}
