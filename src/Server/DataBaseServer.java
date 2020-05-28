package Server;

import Data.*;

import java.sql.*;
import java.util.*;

public class DataBaseServer {
    private final String DATA_BASE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER_NAME = "system";
    private final String PASSWORD = "oracle";

    private final Connection connection;
    private final String CLIENT_NAME_TABLE = "CLIENTS";

    public int ALL = 2;
    public int CARGO = 1;
    public int NONE = 0;

    private ConvertData  convertData = new ConvertData(this);

    public DataBaseServer() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String dataBaseURL = "jdbc:oracle:thin:@localhost:1521:xe";
        Properties props = new Properties();
        props.setProperty("user", "system");
        props.setProperty("password", "oracle");

        String userName = "system";
        String password = "oracle";

        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);
        connection = DriverManager.getConnection(dataBaseURL, userName, password);
    }

    public int getChildCount() {
        return getCountOfElementsInTable(CLIENT_NAME_TABLE);
    }

    /* @return  count_of_clients */
    public int getClientsCount() {
        return getCountOfElementsInTable(CLIENT_NAME_TABLE);
    }

    /* @return count element in table with this name */
    private int getCountOfElementsInTable(String table_name) {
        try {
            String sql = "select count (*) from " + table_name;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getInt(1);

        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void editClientInDataBase(int ID, String columnName, String newString) {
        try {
            String sql = "UPDATE " + CLIENT_NAME_TABLE + " SET " +
                    columnName + " = '" + newString + "' WHERE " + "ID = " + ID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addClientInDataBase(String name, String lastName, String lastLastName, String documentID,
                                    String age, String childrenID, String travelTarget) {
        try {
            String g = "\'";
            String sql = "INSERT INTO " + CLIENT_NAME_TABLE + "(" + "NAME" +
                    "," + "LAST_NAME" + "," + "LAST_LAST_NAME" + "," + "DOCUMENT_NUMBER" +
                    "," + "AGE" + "," + "CHILDREN_ID" +  "," + "TRAVEL_TARGET" + ") " + "VALUES " + "(" + g  +
                    name + g + "," + g + lastName + g + "," + g + lastLastName + g + "," + g + documentID +
                    g +  "," + age + "," + childrenID + "," + g + travelTarget + g + ")";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addClientInDataBase(String name, String lastName, String lastLastName, String documentID,
                                    String age, String travelTarget) {
        try {
            String g = "\'";
            String sql = "INSERT INTO " + CLIENT_NAME_TABLE + "(" + "NAME" +
                    "," + "LAST_NAME" + "," + "LAST_LAST_NAME" + "," + "DOCUMENT_NUMBER" +
                    "," + "AGE" + "," + "CHILDREN_ID" +  "," + "TRAVEL_TARGET" + ") " + "VALUES " + "(" + g  +
                    name + g + "," + g + lastName + g + "," + g + lastLastName + g + "," + g + documentID +
                    g +  "," + age + "," + null + "," + g + travelTarget + g + ")";
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* @return client data by id in db */
    public ClientData getClientData(int id) {
        ClientData data = null;
        try {
            String sql = "select * from " + CLIENT_NAME_TABLE + " WHERE CLIENTS.ID = " + id;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            data = getClientFromResultat(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    /* @return list of warehouse */
    public LinkedList<String> getWarehouse() {
        LinkedList<String> warehouse = new LinkedList<>();
        try {
            String sql = "select * from WAREHOUSE";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                warehouse.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    /* @return name of hostels */
    public LinkedList<String> getHotels() {
        LinkedList<String> hostels = new LinkedList<>();
        try {
            String sql = "select * from HOTELS";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
         while(result.next()) {
              hostels.add(result.getString(2));
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hostels;
    }

    /* @return id by name */
    public int getIdHostel(String name) {
        System.out.println("HOTEL NAME " + name);
        try {
            String sql = "select ID from HOTELS WHERE HOTELS.NAME = '" + name + "'";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* @return flight data of board by arrival date and board type */
    public LinkedList<FlightData> getFlightByDataByType(String date, int type) {
        LinkedList<FlightData> list = new LinkedList<>();
        try {
            String sql = "select FLIGHT.ID, FLIGHT.FLY_DATE, FLIGHT.AIRPLANE_ID from FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                    " WHERE FLIGHT.FLY_DATE = to_date('" + date + "', 'dd.mm.yyyy') AND IS_CARGOPLANE LIKE '" + type +  "'";
            if (ALL == type) {
                sql = "select FLIGHT.ID, FLIGHT.FLY_DATE, FLIGHT.AIRPLANE_ID from FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                        " WHERE FLIGHT.FLY_DATE = to_date('" + date + "', 'dd.mm.yyyy')";
            }

            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                FlightData flightData = getFlightDataFromResultal(result);
                list.add(flightData);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return all excursion */
    public LinkedList<ExcursionData> getExcursion() {
        try {
            String sql = "select * from EXCURSION";
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
             LinkedList<ExcursionData> list = convertData.parse_excursion_data(result);
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return all flight by data */
    public LinkedList<FlightData> getFlightByData(String date) {
        LinkedList<FlightData> list = new LinkedList<>();
        System.out.println(date);
        try {
            String sql = "select * from FLIGHT WHERE FLIGHT.FLY_DATE = to_date('" + date +"', 'dd.mm.yyyy')";
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                FlightData flightData = getFlightDataFromResultal(result);
                list.add(flightData);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return all clients */
    public LinkedList<ClientData> getClientsData() {
        LinkedList<ClientData> list = new LinkedList<>();
        try {
            String sql = "select * from CLIENTS";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                ClientData clientData = getClientFromResultat(result);
                list.add(clientData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public LinkedList<ClientData> getClientsDataByCategory(String category) {
        LinkedList<ClientData> list = new LinkedList<>();
        if (!category.equals("WORK") && !category.equals("TRAVEL")) {
            return list;
        }
        try {
            String sql = "select * from CLIENTS WHERE CLIENTS.TRAVEL_TARGET = '" + category + "'";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                ClientData clientData = getClientFromResultat(result);
                list.add(clientData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /* @return cargo flight in db by data */
    public LinkedList<FlightData> getCargoFlight(String date) {
        LinkedList<FlightData> list = new LinkedList<>();
        try {
            String sql = "select FLIGHT.ID, FLIGHT.FLY_DATE, FLIGHT.AIRPLANE_ID from FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                    " WHERE FLIGHT.FLY_DATE = to_date('" + date + "', 'dd.mm.yyyy') AND IS_CARGOPLANE LIKE '0'";
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                FlightData flightData = getFlightDataFromResultal(result);
                list.add(flightData);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return flight with max height */
    private int getMaxHeightPerDay(String flightDate, int maybeCargo) {
        try {
            String sql;

            if (ALL == maybeCargo) {
                sql = "select max(CARGO_HEIGHT) FROM FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                        " WHERE FLIGHT.FLY_DATE = to_date('+ flightData" + "', 'dd.mm.yyyy')";
            } else {
                sql = sql = "select max(CARGO_HEIGHT) FROM FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                        " WHERE FLIGHT.FLY_DATE = to_date('" + flightDate + "', 'dd.mm.yyyy')" + " AND IS_CARGOPLANE = '0'";
            }

            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /* @return flight with max volume */
    private int getMaxVolumePerDay(String flightDate, int maybeCargo) {
        try {
            String sql;

            if (ALL == maybeCargo) {
                sql = "select max(VOLUME_WEIGHT) FROM FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                        " WHERE FLIGHT.FLY_DATE = to_date('+ flightData" + "', 'dd.mm.yyyy')";
            } else {
                sql = sql = "select max(VOLUME_WEIGHT) FROM FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                        " WHERE FLIGHT.FLY_DATE = to_date('" + flightDate + "', 'dd.mm.yyyy')" + " AND IS_CARGOPLANE = '0'";
            }

            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /* @return flight by params */
    public LinkedList<FlightData> getFlightDataWithGoodParameters(String flightDate, int weight, int MaybeCargo) {
        LinkedList<FlightData> list = new LinkedList<>();
        try {
            String sql;

            if (ALL == MaybeCargo) {
                sql = "select FLIGHT.ID, FLIGHT.FLY_DATE, FLIGHT.AIRPLANE_ID from FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                " WHERE FLIGHT.FLY_DATE = to_date('" + flightDate + "', 'dd.mm.yyyy') AND CARGO_WEIGHT > " + weight/* + " AND VOLUME_WEIGHT > "
                + volume*/;
            } else {
                sql = "select FLIGHT.ID, FLIGHT.FLY_DATE, FLIGHT.AIRPLANE_ID from FLIGHT JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID" +
                        " WHERE FLIGHT.FLY_DATE = to_date('" + flightDate + "', 'dd.mm.yyyy') AND IS_CARGOPLANE =" + "0" + " AND CARGO_WEIGHT > " + weight/* + " AND VOLUME_WEIGHT > "
                        + volume*/;
            }

            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                FlightData flightData = getFlightDataFromResultal(result);
                list.add(flightData);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return agency name by agency id in db */
    String getAgencyNameByID(int id) {
        try {
            String sql = "select NAME from " + "AGENCY" + " WHERE AGENCY.ID = " + id;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return board data by id */
    public AirplaneData getAirplaneDataByID(int airplane_ID) {
        try {
            System.out.println(airplane_ID);
            String sql = "select * from " + "AIRPLANE" + " WHERE AIRPLANE.ID = " + airplane_ID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            AirplaneData data = new AirplaneData(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5));
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Vector<Vector<String>> getClientsTrip(int clientID) {
        try {
            String sql = "select * from " + "TRIP" + " WHERE ID_CLIENT = " + clientID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            return convertData.parse_trip_data(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Vector<Vector<String>> getTrips() {
        try {
            String sql = "select * from " + "TRIP";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            return convertData.parse_trip_data(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return flight data
     *  parse flight data from result */
    private FlightData getFlightDataFromResultal(ResultSet result) throws  SQLException {
        FlightData data = new FlightData();
        data.setID(result.getInt(1));
        data.setData(result.getString(2));
        data.setAirplaneData(getAirplaneDataByID(result.getInt(3)));
        return data;
    }

    /* @return client data
    *   parse client data from result */
    private ClientData getClientFromResultat(ResultSet result) throws SQLException {
        ClientData clientData = new ClientData(result.getString(1),
                result.getString(2), result.getString(3),
                result.getString(4), result.getString(5),
                result.getInt(6), result.getString(7),
                result.getString(8));
        return  clientData;
    }

    /* main function for insert new trip */
    public void insertTrip(LinkedList<CargoData> cargoList, TicketData ticket,
                           ClientData clientData, LinkedList<ExcursionData> excursionList, HotelBookingInfo hotelInfo) {
//        System.out.println("start insert");
        insertTripAndHotel(hotelInfo, clientData);
//        System.out.println("after hotel");
        insertCargoAndChoseFlight(cargoList, ticket, clientData);
//        System.out.println("after cargo");
        insertPassenger(ticket, clientData);
//        System.out.println("after passenger");
        insertExcursion(excursionList, clientData);
//        System.out.println("it's all");
    }

    /* insert booking room and main trip
    * 1/4 part of big insert
    * */
    private void insertTripAndHotel(HotelBookingInfo hotelInfo, ClientData client) {
        try {
            String sql = "DECLARE\n" +
                    "    trans_name VARCHAR(500) := 'trans for " + hotelInfo.getName() + "';\n" +
                    "    hotel_id int := " + getIdHostel(hotelInfo.getName()) +";\n" +
                    "    price_for_hotel int := " + hotelInfo.getPrice() + ";\n" +
                    "    id_client_val int := " + client.getId() + ";\n" +
                    "    ident_trans_id integer;\n" +
                    "    ident_booking_id integer;\n" +
                    "    date_in_val date := to_date(' " + hotelInfo.getDateIn() + "', 'dd.mm.yyyy');\n" +
                    "    date_out_val date := to_date('" + hotelInfo.getDateOut() + "', 'dd.mm.yyyy');\n" +
                    "    d int;\n" +
                    "begin\n" +
                    "    INSERT INTO TRANSACTIONS (NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', price_for_hotel, id_client_val) " +
                    "returning ID into ident_trans_id;\n" +
                    "    INSERT INTO BOOKING_ROOM (ID_HOTEL, ID_TRANS) VALUES (hotel_id, ident_trans_id) returning ID into ident_booking_id;\n" +
                    "    INSERT INTO TRIP (DATE_IN, DATE_OUT, BOOKING_ROOM_ID, ID_CLIENT) VALUES  (date_in_val, date_out_val, " +
                    "ident_booking_id, id_client_val);\n" +
                    "end;";
//            System.out.println(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* insert cargo 2/4 */
    private void insertCargoAndChoseFlight(LinkedList<CargoData> cargoList, TicketData ticketData, ClientData client) {
        LinkedList<FlightData> flightDataIn = ticketData.getIn();
        LinkedList<FlightData> flightDataOut = ticketData.getOut();
        LinkedList<LinkedList<FlightData>> all = new LinkedList<LinkedList<FlightData>>();
        all.add(flightDataIn);
        all.add(flightDataOut);
        if (1 != flightDataIn.size()) {
                for (FlightData flightData : flightDataIn) {
                    if (flightData.getAirplaneData().isCargoPlane()) {
                        for (CargoData cargo : cargoList) {
                            try {
                                String sql = "DECLARE\n" +
                                        "    trans_name VARCHAR(500) := 'trans for cargo';\n" +
                                        "    date_in_val date :=to_date(' " + cargo.getDateIn() + "', 'dd.mm.yyyy');\n" +
                                        "    date_out_val date := to_date(' " + cargo.getDateOut() + "', 'dd.mm.yyyy');\n" +
                                        "    kind_val varchar(100) := '" + cargo.getKind() + "';\n" +
                                        "    id_trip_val int;\n" +
                                        "    id_client_val int := " + client.getId() + ";\n" +
                                        "    id_warehouse_val int :=" + Integer.parseInt(cargo.getWarehouseID()) + ";\n" +
                                        "    id_flight_val int := " + flightData.getID() + ";\n" +
                                        "    count_val int :=" + cargo.getCount() + ";\n" +
                                        "    weight_val int :=" + cargo.getWeight() + ";\n" +
                                        "    wrap_val int :=" + cargo.getReal_wrap() + ";\n" +
                                        "    cost_insurance_val int := " + cargo.getCost_insurance() + ";\n" +
                                        "    r_statement_id int;\n" +
                                        "    r_cargo_id int;\n" +
                                        "    r_transaction_id int;\n" +
                                        "begin\n" +
                                        "     SELECT MAX(ID) into id_trip_val FROM TRIP;\n" +
                                        "     INSERT INTO STATEMENT (COUNT, COST_WRAP, COST_INSURANCE, WEIGHT) VALUES (count_val, wrap_val, cost_insurance_val, weight_val) returning ID\n" +
                                        "         into r_statement_id;\n" +
                                        "     INSERT INTO CARGO (ID_WAREHOUSE, ID_STATEMENT, ID_FLIGHT, DATE_IN, DATE_OUT, KIND)\n" +
                                        "        VALUES (id_warehouse_val, r_statement_id, id_flight_val, date_in_val, date_out_val, kind_val) returning ID into r_cargo_id;\n" +
                                        "     INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', wrap_val, id_client_val) returning ID into r_transaction_id;\n" +
                                        "     INSERT INTO CARGO_TOURIST(ID_TRIP, ID_CARGO, ID_TRANSACTION) VALUES (id_trip_val, r_cargo_id, r_transaction_id);\n" +
                                        "end;";
                                System.out.println(sql);
                                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                ResultSet result = preparedStatement.executeQuery();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
        } else {
                FlightData data = flightDataIn.getFirst();
                for (CargoData cargo : cargoList) {
                    try {
                        String sql = "DECLARE\n" +
                                "    trans_name VARCHAR(500) := 'trans for cargo';\n" +
                                "    date_in_val date :=to_date(' " + cargo.getDateIn() + "', 'dd.mm.yyyy');\n" +
                                "    date_out_val date := to_date(' " + cargo.getDateOut() + "', 'dd.mm.yyyy');\n" +
                                "    kind_val varchar(100) := '" + cargo.getKind() + "';\n" +
                                "    id_trip_val int;\n" +
                                "    id_client_val int := " + client.getId() + ";\n" +
                                "    id_warehouse_val int :=" + cargo.getWarehouseID() + ";\n" +
                                "    id_flight_val int := " + data.getID() + ";\n" +
                                "    count_val int :=" + cargo.getCount() + ";\n" +
                                "    weight_val int :=" + cargo.getWeight() + ";\n" +
                                "    wrap_val int :=" + cargo.getReal_wrap() + ";\n" +
                                "    cost_insurance_val int := " + cargo.getCost_insurance() + ";\n" +
                                "    r_statement_id int;\n" +
                                "    r_cargo_id int;\n" +
                                "    r_transaction_id int;\n" +
                                "begin\n" +
                                "     SELECT MAX(ID) into id_trip_val FROM TRIP;\n" +
                                "     INSERT INTO STATEMENT (COUNT, COST_WRAP, COST_INSURANCE, WEIGHT) VALUES (count_val, wrap_val, cost_insurance_val, weight_val) returning ID\n" +
                                "         into r_statement_id;\n" +
                                "     INSERT INTO CARGO (ID_WAREHOUSE, ID_STATEMENT, ID_FLIGHT, DATE_IN, DATE_OUT, KIND)\n" +
                                "        VALUES (id_warehouse_val, r_statement_id, id_flight_val, date_in_val, date_out_val, kind_val) returning ID into r_cargo_id;\n" +
                                "     INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', wrap_val, id_client_val) returning ID into r_transaction_id;\n" +
                                "     INSERT INTO CARGO_TOURIST(ID_TRIP, ID_CARGO, ID_TRANSACTION) VALUES (id_trip_val, r_cargo_id, r_transaction_id);\n" +
                                "end;";
//                                System.out.println(sql);
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        ResultSet result = preparedStatement.executeQuery();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
        }
    }

    /* insert passenger 3/4 */
    private void insertPassenger(TicketData ticketData, ClientData client) {
        LinkedList<FlightData> flightDataIn = ticketData.getIn();
        LinkedList<FlightData> flightDataOut = ticketData.getOut();
        LinkedList<LinkedList<FlightData>> all = new LinkedList<>();
        all.add(flightDataIn);
        all.add(flightDataOut);
        for (LinkedList<FlightData> flightDataLinkedList : all) {
            for (FlightData flightData : flightDataLinkedList) {
                if (!flightData.getAirplaneData().isCargoPlane()) {
                    try {
                        String sql = "DECLARE\n" +
                                "    trans_name varchar(500) := 'trans for passenger flight';\n" +
                                "    id_flight_val int :=" + flightData.getID() + ";\n" +
                                "    price_for_flight int :=" + 250 + ";\n" +
                                "    id_client_val int :=" + client.getId() + ";\n" +
                                "    r_transaction_id int;\n" +
                                "begin\n" +
                                "    INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', price_for_flight, id_client_val) returning ID into r_transaction_id;\n" +
                                "    INSERT INTO TICKETS(ID_FLIGHT, ID_TRANS, ID_TOURIST) VALUES (id_flight_val, r_transaction_id, id_client_val);\n" +
                                "end;";
//                        System.out.println(sql);
                        ResultSet resultSet = makeSql(sql);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }
    }

    /* insert excursion 4/4 */
    private void insertExcursion(LinkedList<ExcursionData> excursionList, ClientData client) {
        for (ExcursionData excursionData : excursionList) {
            try {
                String sql = "DECLARE\n" +
                        "    client_id int := " + client.getId() +";\n" +
                        "    excursion_id int :="  + excursionData.getExcursionID() + ";\n" +
                        "    trans_name varchar(500) := 'trans for excursion ';\n" +
                        "    trip_id int;\n" +
                        "    r_sum int;\n" +
                        "    r_trans_id int;\n" +
                        "begin\n" +
                        "    SELECT PRICE INTO r_sum FROM EXCURSION WHERE EXCURSION.ID = excursion_id;\n" +
                        "    SELECT MAX(ID) INTO trip_id FROM TRIP;\n" +
                        "    INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', r_sum, client_id) returning ID into r_trans_id;\n" +
                        "    INSERT INTO REST_TOURIST(ID_EXCURSION, ID_TRIP, ID_TRANS) VALUES (excursion_id, trip_id, r_trans_id);\n" +
                        "end;";
//                System.out.println(sql);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet result = preparedStatement.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /*
    * @return ResultSet by sql
    * */
    private ResultSet makeSql(String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet result = preparedStatement.executeQuery();
        return result;
    }
}