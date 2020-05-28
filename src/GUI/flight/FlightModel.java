package GUI.flight;

import Data.CargoData;
import Data.FlightData;
import Data.TicketData;
import Server.DataBaseServer;

import java.util.Comparator;
import java.util.LinkedList;

public class FlightModel {
    private final DataBaseServer server;
    private LinkedList<TicketData> ticketData = new LinkedList<>();
    private TicketData ticket;
    private final String dateIn;
    private final String dateOut;

    FlightModel(DataBaseServer server, String dateIn,
                String dateOut, LinkedList<CargoData> cargoList) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.server = server;

        int cargoWeight = 0;
        if (null != cargoList) {
            for (CargoData cargo : cargoList) {
                cargoWeight += cargo.getWeight() * cargo.getCount();
            }
        }

        ticketData = createVariantOfFlight(dateIn, dateOut, cargoWeight);
    }

    FlightModel(DataBaseServer server, String dateIn,
                String dateOut, int weight) {
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.server = server;
        ticketData = createVariantOfFlight(dateIn, dateOut, weight);
    }

    private LinkedList<TicketData> createVariantOfFlight(String dateIn, String dateOut, int weight) {
        System.out.println(dateIn + "    " + dateOut);
        LinkedList<LinkedList<FlightData>> flightListIn = getVariantByData(dateIn, weight);
        LinkedList<LinkedList<FlightData>> flightListOut = getVariantByData(dateOut, weight);

        LinkedList<TicketData> variant_of_tickets = new LinkedList<>();

        for (LinkedList<FlightData> flightIn : flightListIn) {
            for (LinkedList<FlightData> flightOut : flightListOut) {
                variant_of_tickets.add(new TicketData(flightIn, flightOut));
            }
        }

        return variant_of_tickets;
    }

    private LinkedList<LinkedList<FlightData>>getVariantByData(String date, int weight) {
        LinkedList<FlightData> flightsIn = server.getFlightDataWithGoodParameters(date, weight, server.NONE);
        assert flightsIn != null;
        boolean isInData = false;
        if (date.equals(dateIn)) {
            isInData = true;
        } else if (date.equals(dateOut)) {
            isInData = false;
        }
        LinkedList<LinkedList<FlightData>> flightList;
        if (flightsIn.isEmpty()) {         // если нет пассажирских самолетов, которые подходят по параметрам, то нам нужен будет еще и грузовой борт
            LinkedList<FlightData> flightDataPassenger = server.getFlightByDataByType(date, server.NONE);
            LinkedList<FlightData> flightDataCargo = server.getFlightByDataByType(date, server.CARGO);
            assert flightDataPassenger != null;
            assert flightDataCargo != null;

            flightDataCargo = sort_flight_by_weight_in_descending_order(flightDataCargo);
            flightDataPassenger = sort_flight_by_weight_in_descending_order(flightDataPassenger);
            flightList = getFlightList(flightDataPassenger, flightDataCargo, weight);
        } else {
            flightList = new LinkedList<LinkedList<FlightData>>();
            for (FlightData data : flightsIn) {
                LinkedList<FlightData> flightData = new LinkedList<>();
                flightData.add(data);
                flightList.add(flightData);
            }
        }
        return flightList;
    }

    private LinkedList<FlightData> sort_flight_by_weight_in_descending_order(LinkedList<FlightData> data) {
        data.sort(new Comparator<FlightData>() {
            @Override
            public int compare(FlightData flightData, FlightData t1) {
                return t1.getAirplaneData().getCargoWeight() - flightData.getAirplaneData().getCargoWeight();
            }
        });
        return data;
    }

    private LinkedList<LinkedList<FlightData>> getFlightList(LinkedList<FlightData> passenger, LinkedList<FlightData> cargo,
                                                             int cargoWeight) {
        LinkedList<LinkedList<FlightData>> flightList = new LinkedList<LinkedList<FlightData>>();
        for (FlightData passenger_plain : passenger) {
            LinkedList<FlightData> oneFlightData = new LinkedList<>();
            int summaryWeight = 0;
            summaryWeight += passenger_plain.getAirplaneData().getCargoWeight();
            oneFlightData.add(passenger_plain);
            if (summaryWeight > cargoWeight) {  // если у нас достаточно грузоподъемности пассажирского
                flightList.add(oneFlightData);
                continue;
            }
            for (FlightData cargo_plain : cargo) { // если недостаточно, то следует добавлять грузовые, пока не будет достаточно
                summaryWeight += cargo_plain.getAirplaneData().getCargoWeight();
                oneFlightData.add(cargo_plain);
                if (summaryWeight > cargoWeight) { // если мы собираем сочитание бортов, общий перевозимый вес которых удовлетворяет
                    flightList.add(oneFlightData);  // требованиям, то добавляем их
                    oneFlightData = new LinkedList<>();
                    oneFlightData.add(passenger_plain);
                    summaryWeight = passenger_plain.getAirplaneData().getCargoWeight();
                }
            }
        }
        return flightList;
    }

    LinkedList<TicketData> getTicketData() {
        return ticketData;
    }

    public void setTicket(TicketData ticket) {
        this.ticket = ticket;
    }

    public TicketData getTicket() {
        return ticket;
    }
}
