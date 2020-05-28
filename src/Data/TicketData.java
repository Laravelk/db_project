package Data;

import Data.FlightData;

import java.util.LinkedList;

public class TicketData {
    private final LinkedList<FlightData> in;
    private final LinkedList<FlightData> out;
    public TicketData(LinkedList<FlightData> in, LinkedList<FlightData> out) {
        this.in = in;
        this.out = out;
    }

    public LinkedList<FlightData> getIn() {
        return in;
    }

    public LinkedList<FlightData> getOut() {
        return out;
    }
};