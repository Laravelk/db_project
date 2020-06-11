package Server;

import Data.*;
import GUI.requests.dataaboutflight.AboutFlightData;
import GUI.requests.dataaboutflight.AboutPassengerData;
import GUI.requests.financeaboutclient.TransactionData;
import GUI.requests.infoaboutcargo.DataAboutCargo;
import GUI.requests.infoabouttrip.AboutCargo;
import GUI.requests.infoabouttrip.AboutExcursion;
import GUI.requests.infoabouttrip.AboutTripDate;
import GUI.requests.infoaboutwarehouse.DataAboutFlightsWithWarehouse;
import GUI.requests.infoaboutwarehouse.DataAboutWarehouse;
import GUI.requests.paymentinfo.PaymentData;

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

    public DataBaseServer(String ip, String userName, String password) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String dataBaseURL = "jdbc:oracle:thin:@" + ip +":1521:xe";
        Properties props = new Properties();
        props.setProperty("user", "system");
        props.setProperty("password", "oracle");

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
            int res = result.getInt(1);
            result.close();
            return res;

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
            result.close();
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
            result.close();
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
            result.close();
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
            resultSet.close();
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
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    /* @return name of hotel */
    public String getHotelByTrip(int idTrip) {
        try {
            String sql = "SELECT HOTELS.NAME FROM TRIP INNER JOIN BOOKING_ROOM ON TRIP.BOOKING_ROOM_ID = BOOKING_ROOM.ID\n" +
                    "INNER JOIN HOTELS ON BOOKING_ROOM.ID_HOTEL = HOTELS.ID WHERE TRIP.ID = " + idTrip;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            String name = result.getString(1);
            result.close();
            return name;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
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
            result.close();
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
            int res = result.getInt(1);
            result.close();
            return res;
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
            result.close();
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    /* remove excursion */
    public void removeExcursion(ExcursionData data) {
        try {
            String sql = "DECLARE\n" +
                    "    id_ex int := " + data.getExcursionID() +";\n" +
                    "begin\n" +
                    "DELETE REST_TOURIST WHERE ID_EXCURSION = id_ex;\n" +
                    "DELETE EXCURSION WHERE ID = id_ex;\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* @return all excursion */
    public LinkedList<ExcursionData> getAllExcursion() {
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
            result.close();
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return all excursion */
    public LinkedList<ExcursionData> getExcursion(String dateIn, String dateOut) {
        try {
            String sql = "select * from EXCURSION WHERE DATE_EX between to_date('" + convertData.convertDate(dateIn) + "', 'dd.mm.yyyy') and to_date('" + convertData.convertDate(dateOut) + "', 'dd.mm.yyyy')";
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
             LinkedList<ExcursionData> list = convertData.parse_excursion_data(result);
            result.close();
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
            result.close();
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
            result.close();
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
            result.close();
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
            result.close();
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
            int res = result.getInt(1);
            result.close();
            return res;
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
            int res = result.getInt(1);
            result.close();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /* @return вес груза в определенном путешествии */
    public int getWeightCargoByTripId(int tripID) {
        int weight = 0;
        try {
            String sql = "select SUM(WEIGHT * COUNT) FROM TRIP INNER JOIN CARGO_TOURIST ON TRIP.ID = CARGO_TOURIST.ID_TRIP\n" +
                    "       INNER JOIN CARGO ON CARGO_TOURIST.ID_CARGO = CARGO.ID INNER JOIN STATEMENT ON CARGO.ID_STATEMENT = STATEMENT.ID\n" +
                    "        WHERE TRIP.ID = " +  tripID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                weight = result.getInt(1);
            }
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return weight;
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
            result.close();
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
            String res = result.getString(1);
            result.close();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return flight data for fligh admin */
    public LinkedList<FlightData> getAllFlightData() {
        LinkedList<FlightData> list = new LinkedList<>();
        try {
            String sql = "SELECT * FROM FLIGHT";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                FlightData data = new FlightData();
                data.setID(result.getInt(1));
                data.setData(convertData.convertDate(result.getString(2)));
                data.getAirplaneData().setID(result.getInt(3));
                data.setIdTrans(result.getInt(4));
                list.add(data);
            }
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /* insert airplane */
    public void insertAirplane(AirplaneData airplaneData) {
        try {
            int b = 0;
            if (airplaneData.isCargoPlane()) {
                b = 1;
            }
            String sql = "DECLARE\n" +
                    "    seat int := " + airplaneData.getSeatCount() +";\n" +
                    "    weight int := " + airplaneData.getCargoWeight() + ";\n" +
                    "    volume int := " + airplaneData.getVolumeWeight() + ";\n" +
                    "    is_cargo varchar(1) := '" + b +"';\n" +
                    "    begin\n" +
                    "    INSERT INTO AIRPLANE (SEAT_COUNT, CARGO_WEIGHT, VOLUME_WEIGHT, IS_CARGOPLANE) VALUES (seat, weight, volume, is_cargo);\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* get all agency name */
    public LinkedList<String> getAllAgency() {
        LinkedList<String> names = new LinkedList<>();
        try {
            String sql = "Select AGENCY.NAME FROM AGENCY";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                names.add(result.getString(1));
            }
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return names;
    }

    public void addExcursionData(ExcursionData data) {
        try {
            String sql = "DECLARE\n" +
                    "    sum int := " + data.getPrice() +";\n" +
                    "    date_ex date := to_date('" + data.getDate() +"', 'dd.mm.yyyy');\n" +
                    "    agency_id int := " + data.getAgencyID() +";\n" +
                    "    r_last_trans int;\n" +
                    "    rate int := " + data.getRate() + ";\n" +
                    "    title varchar(500) := '" + data.getTitle() + "';\n" +
                    "begin\n" +
                    "    INSERT INTO TRANSACTIONS (NAME, IS_INCOME, SUM) VALUES ('Расходы на организацию', '0', sum) returning ID into r_last_trans;\n" +
                    "    INSERT INTO EXCURSION(ID_AGENCY, DATE_EX, TITLE, RATE, TRANS_ID, PRICE) VALUES (agency_id, date_ex, title, \n" +
                    "                                                                                    rate, r_last_trans, sum / 4);\n" +
                    "\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getIdAgency(String name) {
        int id = 0;
        try {
            String sql = "Select AGENCY.ID FROM AGENCY WHERE AGENCY.NAME = '" + name +"'";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            id = result.getInt(1);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    /* insert a flight */
    public void insertAFlight(FlightData data) {
        try {
            String sql = "DECLARE\n" +
                    "    r_transaction_id int;\n" +
                    "    start_date date := to_date(' " + data.getData() +"', 'dd.mm.yyyy');\n" +
                    "    plain_id int := " + data.getAirplaneData().getID() + ";\n" +
                    "begin\n" +
                    "    INSERT INTO TRANSACTIONS (NAME, IS_INCOME, SUM) VALUES ('Buy a flight', '0', 15000)\n" +
                    "    returning ID into r_transaction_id;\n" +
                    "    INSERT INTO FLIGHT(FLY_DATE, AIRPLANE_ID, ID_TRANS) VALUES (start_date, plain_id, r_transaction_id);\n" +
                    "end;\n";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* get all airplanes*/
    public LinkedList<AirplaneData> getAllAirplane() {
        LinkedList<AirplaneData> airplaneData = new LinkedList<>();
        try {
            String sql = "SELECT * from AIRPLANE";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                AirplaneData data = new AirplaneData();
                data.setID(result.getInt(1));
                data.setSeatCount(result.getInt(2));
                data.setCargoWeight(result.getInt(3));
                data.setVolumeWeight(result.getInt(4));
                if ("0".equals(result.getString(5))) {
                    data.setCargoPlane(false);
                } else {
                    data.setCargoPlane(true);
                }
                airplaneData.add(data);
            }
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airplaneData;
    }

    /* remove flight by id */
    public void removeFlight(int flightID) {
        try {
            String sql = "DELETE FLIGHT WHERE FLIGHT.ID = " + flightID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* @return board data by id */
    public AirplaneData getAirplaneDataByID(int airplane_ID) {
        try {
            String sql = "select * from " + "AIRPLANE" + " WHERE AIRPLANE.ID = " + airplane_ID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            AirplaneData data = new AirplaneData(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5));
            result.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return clientTripById */
    public Vector<Vector<String>> getClientsTrip(int clientID) {
        try {
            String sql = "select * from " + "TRIP" + " WHERE ID_CLIENT = " + clientID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            Vector<Vector<String>> data = convertData.parse_trip_data(result);
            result.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /* @return all trips */
    public Vector<Vector<String>> getTrips() {
        try {
            String sql = "select * from " + "TRIP";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            Vector<Vector<String>> data = convertData.parse_trip_data(result);
            result.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* @return trip by client id */
    public TripData getTripDataByID(int id) {
        try {
            String sql = "select * from " + "TRIP" + " WHERE ID = " + id;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            TripData data = convertData.parse_trip_one_data(result);
            result.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* */

    /* @return flight data
     *  parse flight data from result */
    private FlightData getFlightDataFromResultal(ResultSet result) throws  SQLException {
        FlightData data = new FlightData();
        data.setID(result.getInt(1));
        data.setData(convertData.convertDate(result.getString(2)));
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
        insertTripAndHotel(hotelInfo, clientData);
        insertCargoAndChoseFlight(cargoList, ticket, clientData);
        insertPassenger(ticket, clientData);
        insertExcursion(excursionList, clientData);
    }

/* удаляем номера рейсов, которыми полетит груз клиента в данном путешествии */
    public void removeTicketToFlightByTrip(int tripID, boolean isIn) {
        String nameField;
        if (isIn) {
            nameField =  "ID_FLIGHT_IN";
        } else {
            nameField = "ID_FLIGHT_OUT";
        }
        try {
            String sql = "select FLIGHT.ID FROM TRIP INNER JOIN CARGO_TOURIST ON TRIP.ID = CARGO_TOURIST.ID_TRIP\n" +
                    "    INNER JOIN CARGO ON CARGO_TOURIST.ID_CARGO = CARGO.ID INNER JOIN FLIGHT\n" +
                    "        ON CARGO." + nameField + " = FLIGHT.ID WHERE TRIP.ID = + " + tripID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void changeFlightClientsInformation(int tripID, int clientID, TicketData ticketData) {
        LinkedList<FlightData> in = ticketData.getIn();
        LinkedList<FlightData> out = ticketData.getOut();
        int passengerFlightID = 0;
        int cargoFlightID = 0;
        String date = "";

        for (FlightData data : in) {
            if (data.getAirplaneData().isCargoPlane()) {
                cargoFlightID = data.getID();
            } else {
                passengerFlightID = data.getID();
            }
            date = convertData.convertDate(data.getData());
        }

        LinkedList<Integer> cargos = getCargoID(tripID, date, true);
            if (-1 == cargoFlightID) {
                cargoFlightID = passengerFlightID;
        }

        if (null != cargos) {
            changeFlightCargo(cargoFlightID, cargos, true);
        }

        changePeopleTicket(tripID, passengerFlightID, date);
        for (FlightData data : out) {
            if (data.getAirplaneData().isCargoPlane()) {
                cargoFlightID = data.getID();
            } else {
                passengerFlightID = data.getID();
            }
            date = convertData.convertDate(data.getData());
        }
        if (-1 == cargoFlightID) {
            cargoFlightID = passengerFlightID;
        }

        if (null != cargos) {
            changeFlightCargo(cargoFlightID, cargos, false);
        }

        changePeopleTicket(tripID, passengerFlightID, date);

    }

    private void changePeopleTicket(int tripID, int newPassengerFlightID, String date) {
        try {
                String sql = "UPDATE TICKETS SET ID_FLIGHT = " + newPassengerFlightID + " WHERE ID_TRIP = " + tripID +" AND to_date('" + date  + "', 'dd.mm.yyyy') =\n" +
                        "(SELECT FLY_DATE FROM FLIGHT WHERE FLIGHT.ID = TICKETS.ID_FLIGHT)";
            System.out.println(sql);
                Statement statement = null;
                statement = connection.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE
                );
                ResultSet result = statement.executeQuery(sql);
                result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void changeFlightCargo(int newCargoFlightID, LinkedList<Integer> cargos, boolean isIn) {
        String nameField;
        if (isIn) {
            nameField =  "ID_FLIGHT_IN";
        } else {
            nameField = "ID_FLIGHT_OUT";
        }

        try {
            for (Integer cargo : cargos) {
                String sql = "UPDATE CARGO SET " + nameField  +  " = "  + newCargoFlightID + "  WHERE ID = " +  cargo;
                System.out.println(sql);
                Statement statement = null;
                statement = connection.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE
                );
                ResultSet result = statement.executeQuery(sql);
                result.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private LinkedList<Integer> getCargoID(int tripID, String date, boolean isIn) {
        String nameField;
        if (isIn) {
            nameField =  "ID_FLIGHT_IN";
        } else {
            nameField = "ID_FLIGHT_OUT";
        }
        LinkedList<Integer> cargo = new LinkedList<>();
        try {
            String sql = "select CARGO.ID FROM TRIP INNER JOIN CARGO_TOURIST ON TRIP.ID = CARGO_TOURIST.ID_TRIP\n" +
                    "     INNER JOIN CARGO ON CARGO_TOURIST.ID_CARGO = CARGO.ID\n" +
                    "    INNER JOIN FLIGHT ON CARGO."+ nameField +" = FLIGHT.ID WHERE FLIGHT.FLY_DATE = to_date('" + date + "','dd.mm.yyyy')\n" +
                    "    AND TRIP.ID =" + tripID ;
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("CARGO " + result.getString(1));
                cargo.add(result.getInt(1));
            }
            result.close();
            return  cargo;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }




    /* insert booking room and main trip
    * 1/4 part of big insert
    * */
    private void insertTripAndHotel(HotelBookingInfo hotelInfo, ClientData client) {
        try {
            int shopTour = 0;
            if (hotelInfo.isShopTour()) {
                shopTour = 1;
            }
            String sql = "DECLARE\n" +
                    "    trans_name VARCHAR(500) := 'trans for hotel';\n" +
                    "    is_shop_tour VARCHAR(1) := '" + shopTour +  "';\n" +
                    "    hotel_id int := " + getIdHostel(hotelInfo.getName()) +";\n" +
                    "    price_for_hotel int := " + hotelInfo.getPrice() + ";\n" +
                    "    id_client_val int := " + client.getId() + ";\n" +
                    "    ident_trans_id integer;\n" +
                    "    ident_booking_id integer;\n" +
                    "    date_in_val date := to_date(' " + hotelInfo.getDateIn() + "', 'dd.mm.yyyy');\n" +
                    "    date_out_val date := to_date('" + hotelInfo.getDateOut() + "', 'dd.mm.yyyy');\n" +
                    "    id_group int := " + hotelInfo.getGroupNumber() + ";\n" +
                    "    d int;\n" +
                    "begin\n" +
                    "    INSERT INTO TRANSACTIONS (NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', price_for_hotel, id_client_val) " +
                    "returning ID into ident_trans_id;\n" +
                    "    INSERT INTO BOOKING_ROOM (ID_HOTEL, ID_TRANS) VALUES (hotel_id, ident_trans_id) returning ID into ident_booking_id;\n" +
                    "    INSERT INTO TRIP (DATE_IN, DATE_OUT, BOOKING_ROOM_ID, ID_CLIENT, ID_GROUP, IS_SHOPTUR) VALUES (date_in_val, date_out_val" +
                    ",ident_booking_id, id_client_val, id_group, is_shop_tour);\n" +
                    "end;";
//            System.out.println(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            result.close();
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
            int s = 0;
                for (FlightData flightData : flightDataIn) {
                    int out_flight_id = flightDataOut.get(s).getID();
                    s++;
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
                                        "    id_flight_val_in int := " + flightData.getID() + ";\n" +
                                        "    count_val int :=" + cargo.getCount() + ";\n" +
                                        "    weight_val int :=" + cargo.getWeight() + ";\n" +
                                         "   volume_val int := " + cargo.getVolume() + ";\n" +
                                        "    wrap_val int :=" + cargo.getReal_wrap() + ";\n" +
                                        "    id_flight_val_out int :=" + out_flight_id + ";\n" +
                                        "    cost_insurance_val int := " + cargo.getCost_insurance() + ";\n" +
                                        "    r_statement_id int;\n" +
                                        "    r_cargo_id int;\n" +
                                        "    r_transaction_id int;\n" +
                                        "begin\n" +
                                        "     SELECT MAX(ID) into id_trip_val FROM TRIP;\n" +
                                        "     INSERT INTO STATEMENT (COUNT, COST_WRAP, COST_INSURANCE, WEIGHT, VOLUME) VALUES (count_val, wrap_val, cost_insurance_val, weight_val, volume_val) returning ID\n" +
                                        "         into r_statement_id;\n" +
                                        "     INSERT INTO CARGO (ID_WAREHOUSE, ID_STATEMENT, ID_FLIGHT_IN, ID_FLIGHT_OUT, DATE_IN, DATE_OUT, KIND)\n" +
                                        "        VALUES (id_warehouse_val, r_statement_id, id_flight_val_in, id_flight_val_out, date_in_val, date_out_val, kind_val) returning ID into r_cargo_id;\n" +
                                        "     INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', wrap_val, id_client_val) returning ID into r_transaction_id;\n" +
                                        "     INSERT INTO CARGO_TOURIST(ID_TRIP, ID_CARGO, ID_TRANSACTION) VALUES (id_trip_val, r_cargo_id, r_transaction_id);\n" +
                                        "end;";
                                System.out.println(sql);
                                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                                ResultSet result = preparedStatement.executeQuery();
                                result.close();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
        } else {
                FlightData data = flightDataIn.getFirst();
            int out_flight_id = flightDataOut.get(0).getID();
            if (null != cargoList) {
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
                                "    id_flight_val_in int := " + data.getID() + ";\n" +
                                "    id_flight_val_out int:=" + out_flight_id + ";\n" +
                                "    count_val int :=" + cargo.getCount() + ";\n" +
                                "    weight_val int :=" + cargo.getWeight() + ";\n" +
                                "    volume_val int :=" + cargo.getVolume() + ";\n" +
                                "    wrap_val int :=" + cargo.getReal_wrap() + ";\n" +
                                "    cost_insurance_val int := " + cargo.getCost_insurance() + ";\n" +
                                "    r_statement_id int;\n" +
                                "    r_cargo_id int;\n" +
                                "    r_transaction_id int;\n" +
                                "begin\n" +
                                "     SELECT MAX(ID) into id_trip_val FROM TRIP;\n" +
                                "     INSERT INTO STATEMENT (COUNT, COST_WRAP, COST_INSURANCE, WEIGHT, VOLUME) VALUES (count_val, wrap_val, cost_insurance_val, weight_val, volume_val) returning ID\n" +
                                "         into r_statement_id;\n" +
                                "     INSERT INTO CARGO (ID_WAREHOUSE, ID_STATEMENT, ID_FLIGHT_IN, ID_FLIGHT_OUT, DATE_IN, DATE_OUT, KIND)\n" +
                                "        VALUES (id_warehouse_val, r_statement_id, id_flight_val_in, id_flight_val_out, date_in_val, date_out_val, kind_val) returning ID into r_cargo_id;\n" +
                                "     INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', wrap_val, id_client_val) returning ID into r_transaction_id;\n" +
                                "     INSERT INTO CARGO_TOURIST(ID_TRIP, ID_CARGO, ID_TRANSACTION) VALUES (id_trip_val, r_cargo_id, r_transaction_id);\n" +
                                "end;";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        ResultSet result = preparedStatement.executeQuery();
                        result.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
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
                                "    id_trip_val int;\n" +
                                 "begin\n" +
                                "    SELECT MAX(ID) into id_trip_val FROM TRIP;\n" +
                                "    INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', price_for_flight, id_client_val) returning ID into r_transaction_id;\n" +
                                "    INSERT INTO TICKETS(ID_FLIGHT, ID_TRANS, ID_TOURIST, ID_TRIP) VALUES (id_flight_val, r_transaction_id, id_client_val, id_trip_val);\n" +
                                "end;";
                        ResultSet resultSet = makeSql(sql);
                        resultSet.close();
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
                        "    trans_name varchar(500) := 'trans for excursion';\n" +
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
                result.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public LinkedList<CargoData> getCargoFromTrip(int idTrip) {
        LinkedList<CargoData> cargoList = new LinkedList<>();
        try {
            String sql = "SELECT CARGO.ID, CARGO.ID_STATEMENT, CARGO.KIND, STATEMENT.COUNT, STATEMENT.COST_WRAP, STATEMENT.COST_INSURANCE,\n" +
                    "STATEMENT.WEIGHT, STATEMENT.VOLUME FROM TRIP INNER JOIN CARGO_TOURIST ON TRIP.ID = CARGO_TOURIST.ID_TRIP\n" +
                    "INNER JOIN CARGO ON CARGO_TOURIST.ID_CARGO = CARGO.ID INNER JOIN STATEMENT ON CARGO.ID_STATEMENT = STATEMENT.ID\n" +
                    "WHERE TRIP.ID = " + idTrip;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println("CARGO");
                CargoData cargoData = new CargoData();
                cargoData.setID(result.getInt(1));
                cargoData.setStatementID(result.getInt(2));
                cargoData.setKind(result.getString(3));
                cargoData.setCount(result.getInt(4));
                cargoData.setReal_wrap(result.getInt(5));
                cargoData.setCost_insurance(result.getInt(6));
                cargoData.setWeight(result.getInt(7));
                cargoData.setVolume(result.getInt(8));
                cargoList.add(cargoData);
            }
            result.close();
            return cargoList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cargoList;
    }

    public LinkedList<ExcursionData> getExcursionFromTrip(int idTrip) {
        LinkedList<ExcursionData> excursionData = new LinkedList<>();
        try {
            String sql = "SELECT EXCURSION.ID, EXCURSION.ID_AGENCY, EXCURSION.DATE_EX, EXCURSION.TITLE, EXCURSION.RATE, EXCURSION.PRICE\n" +
                    "FROM TRIP INNER JOIN REST_TOURIST ON TRIP.ID = REST_TOURIST.ID_TRIP INNER JOIN EXCURSION\n" +
                    "ON REST_TOURIST.ID_EXCURSION = EXCURSION.ID WHERE TRIP.ID = " + idTrip;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                ExcursionData data = new ExcursionData();
                data.setExcursionID(result.getInt(1));
                data.setAgencyID(result.getInt(2));
                data.setDate(result.getString(3));
                data.setTitle(result.getString(4));
                data.setRate(result.getInt(5));
                data.setPrice(result.getInt(6));
                excursionData.add(data);
            }
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return excursionData;
    }

    /*
    * EDIT ZONE
    * EDIT ZONE
    * EDIT ZONE
    * EDIT ZONE
    * EDIT ZONE
    * EDIT ZONE
    * */

    public void updateStatement(String columnName, String newValue, int id) {
        try {
            String sql = "UPDATE " + "STATEMENT" + " SET " +
                    columnName + " = '" + newValue + "' WHERE " + "ID = " + id;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateKindInCargo(String newValue, int id) {
        try {
            String sql = "UPDATE " + "CARGO" + " SET KIND = '" + newValue + "' WHERE " + "ID = " + id;
            System.out.println(sql);
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeCargo(int cargoID, int statementID) {
        try {
            String sql = "DECLARE\n" +
                    "    delete_id int :=  " + cargoID +";\n" +
                    "    delete_st_id int := " + statementID +";\n" +
                    "begin\n" +
                    "DELETE CARGO WHERE ID = delete_id;\n" +
                    "DELETE STATEMENT WHERE ID = delete_st_id;\n" +
                    "DELETE CARGO_TOURIST WHERE ID_CARGO = delete_id;\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeExcursion(int tripID, int excursionID) {
        try {
            String sql = "DECLARE\n" +
                    "    deleted_trip_id int := " + tripID  + ";\n" +
                    "    deleted_excursion_id int := " + excursionID +";\n" +
                    "    r_trans int;\n" +
                    "begin\n" +
                    "SELECT REST_TOURIST.ID_TRANS into r_trans FROM REST_TOURIST WHERE ID_TRIP = deleted_trip_id AND ID_EXCURSION = deleted_excursion_id;\n" +
                    "DELETE REST_TOURIST WHERE ID_TRIP = deleted_trip_id AND ID_EXCURSION = deleted_excursion_id;\n" +
                    "DELETE TRANSACTIONS WHERE TRANSACTIONS.ID = r_trans;\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addExcursion(int tripID, int excursionID, int clientID) {
        try {
            String sql = "DECLARE\n" +
                    "    trip_id int := " + tripID + ";\n" +
                    "    client_id int := " + clientID + ";\n" +
                    "    excursion_id int := " + excursionID + ";\n" +
                    "    trans_name varchar(500) := 'trans for excursion';\n" +
                    "    r_sum int;\n" +
                    "    r_trans_id int;\n" +
                    "begin\n" +
                    "    SELECT PRICE INTO r_sum FROM EXCURSION WHERE EXCURSION.ID = excursion_id;\n" +
                    "    INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', r_sum, client_id) returning ID into r_trans_id;\n" +
                    "    INSERT INTO REST_TOURIST(ID_EXCURSION, ID_TRIP, ID_TRANS) VALUES (excursion_id, trip_id, r_trans_id);\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getBookingID(int tripID) {
        int id = 0;
        try {
            String sql = "SELECT BOOKING_ROOM_ID FROM TRIP WHERE TRIP.ID = " + tripID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            id = result.getInt(1);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public void updateBookingInfo(int clientID, int newHotelID, int bookingID, int price) {
        try {
            String sql = "DECLARE\n" +
                    "    trans_name VARCHAR(500) := 'trans for hotel';\n" +
                    "    client_id int := " + clientID +";\n" +
                    "    new_hotel_id int := " + newHotelID + ";\n" +
                    "    booking_id int := " + bookingID +";\n" +
                    "    r_sum int := " + price +";\n" +
                    "    old_trans_id int;\n" +
                    "    new_trans_id int;\n" +
                    "begin\n" +
                    "    SELECT BOOKING_ROOM.ID_TRIP into old_trans_id FROM BOOKING_ROOM WHERE BOOKING_ROOM.ID = booking_id;\n" +
                    "    INSERT INTO TRANSACTIONS(NAME, IS_INCOME, SUM, ID_CLIENT) VALUES (trans_name, '1', r_sum, client_id) returning ID into new_trans_id;\n" +
                    "    UPDATE BOOKING_ROOM SET ID_HOTEL = new_hotel_id, ID_TRANS = new_trans_id WHERE BOOKING_ROOM.ID = booking_id;\n" +
                    "end;";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
    * REQUEST ZONE
    * REQUEST ZONE
    * REQUEST ZONE
    * REQUEST ZONE
    * REQUEST ZONE
    * REQUEST ZONE
    * REQUEST ZONE
    * REQUEST ZONE
    * */

    /* request one in main window */

    /* second request */
    public Vector<Vector<String>> secondRequest(Vector<String> hotelsNames, boolean isOnlyWork, boolean isOnlyTravel) {
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        if (hotelsNames == null || 0 ==  hotelsNames.size()) {
            return data;
        }
        try {
            String sql = "SELECT CLIENTS.NAME, CLIENTS.LAST_NAME, \n" +
                    "       CLIENTS.DOCUMENT_NUMBER, HOTELS.NAME, CLIENTS.TRAVEL_TARGET FROM  BOOKING_ROOM\n" +
                    "        INNER JOIN HOTELS ON BOOKING_ROOM.ID_HOTEL = HOTELS.ID\n" +
                    "        INNER JOIN TRIP ON BOOKING_ROOM.ID = TRIP.BOOKING_ROOM_ID\n" +
                    "        INNER JOIN CLIENTS ON TRIP.ID_CLIENT = CLIENTS.ID";
            if (0 != hotelsNames.size()) {
                sql += " WHERE (";
                for (int i = 0; i < hotelsNames.size(); i++) {
                    sql +=  " HOTELS.NAME = '" + hotelsNames.elementAt(i) + "'";
                    if (i + 1 < hotelsNames.size()) {
                        sql += " OR ";
                    }
                }
                sql += ")";
            }

            if (isOnlyWork) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'WORK'";
            } else if (isOnlyTravel) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'TRAVEL'";
            }
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                Vector<String> row = new Vector<>();
                row.add(result.getString(1));
                row.add(result.getString(2));
                row.add(result.getString(3));
                row.add(result.getString(4));
                row.add(result.getString(5));
                data.add(row);
            }
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /* 3 request */
    public int getCountOfPeople(String dateIn, String dateOut, boolean isOnlyWork, boolean isOnlyTravel) {
        String sql = "";
        try {
            sql += "SELECT COUNT(DISTINCT CLIENTS.ID) FROM CLIENTS INNER JOIN TRIP ON CLIENTS.ID = TRIP.ID_CLIENT WHERE DATE_IN between\n" +
                    "    to_date('" + dateIn + "', 'dd.mm.yyyy')\n" +
                    "    and to_date('" + dateOut + "', 'dd.mm.yyyy') AND\n" +
                    " DATE_OUT between to_date('" + dateIn +"', 'dd.mm.yyyy')\n" +
                    "     and to_date('" + dateOut + "', 'dd.mm.yyyy')";
            if (isOnlyWork) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'WORK'";
            } else if (isOnlyTravel) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'TRAVEL'";
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

    /* 4 request */
    public LinkedList<AboutTripDate> getAllDates(int touristID) {
        LinkedList<AboutTripDate> dates = new LinkedList<>();
        try {
            String sql = "SELECT CARGO.DATE_IN, CARGO.DATE_OUT FROM CLIENTS INNER JOIN TRIP ON CLIENTS.ID = TRIP.ID_CLIENT\n" +
                    "INNER JOIN CARGO_TOURIST on TRIP.ID = CARGO_TOURIST.ID_TRIP INNER JOIN CARGO\n" +
                    "    on CARGO_TOURIST.ID_CARGO = CARGO.ID WHERE CLIENTS.ID = " + touristID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                AboutTripDate tripDate = new AboutTripDate(convertData.convertDate(result.getString(1)),
                        convertData.convertDate(result.getString(2)));
                dates.add(tripDate);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public AboutTripDate getDate(int tripId) {
        try {
            String sql = "SELECT TRIP.DATE_IN, TRIP.DATE_OUT FROM TRIP WHERE TRIP.ID = " + tripId;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            System.out.println(sql);
            ResultSet result = statement.executeQuery(sql);
            result.next();
            String dateIn = result.getString(1);
            String dateOut = result.getString(2);
            result.close();
            return new AboutTripDate(convertData.convertDate(dateIn),
                    convertData.convertDate(dateOut));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String getHostelInTrip(int tripID) {
        try {
            String sql = "SELECT HOTELS.NAME FROM TRIP INNER JOIN BOOKING_ROOM ON TRIP.BOOKING_ROOM_ID = BOOKING_ROOM.ID\n" +
                    "    INNER JOIN HOTELS ON BOOKING_ROOM.ID_HOTEL = HOTELS.ID WHERE TRIP.ID = " + tripID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            String name = result.getString(1);
            result.close();
            return name;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public LinkedList<AboutExcursion> getDataExcursionForRequest(int tripID) {
        LinkedList<AboutExcursion> dataExcursions = new LinkedList<>();
        try {
            String sql = "SELECT TITLE, AGENCY.NAME FROM TRIP INNER JOIN REST_TOURIST ON TRIP.ID = REST_TOURIST.ID_TRIP\n" +
                    "INNER JOIN EXCURSION ON REST_TOURIST.ID_EXCURSION = EXCURSION.ID\n" +
                    "INNER JOIN AGENCY ON EXCURSION.ID_AGENCY = AGENCY.ID WHERE TRIP.ID = " + tripID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                AboutExcursion dataExcursion = new AboutExcursion(result.getString(1), result.getString(2));
                dataExcursions.add(dataExcursion);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dataExcursions;
    }

    public LinkedList<AboutCargo> getAboutCargo(int tripID) {
        LinkedList<AboutCargo> cargos = new LinkedList<>();
        try {
            String sql = "SELECT CARGO.KIND, STATEMENT.WEIGHT, STATEMENT.COUNT FROM TRIP INNER JOIN CARGO_TOURIST  \n" +
                    "ON TRIP.ID = CARGO_TOURIST.ID_TRIP\n" +
                    "INNER JOIN CARGO ON CARGO_TOURIST.ID_CARGO = CARGO.ID \n" +
                    "INNER JOIN STATEMENT ON CARGO.ID_STATEMENT = STATEMENT.ID WHERE TRIP.ID = " + tripID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                AboutCargo aboutCargo = new AboutCargo
                        (result.getString(1), result.getInt(2), result.getInt(3));
                cargos.add(aboutCargo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cargos;
    }

    /* 5 */
    public LinkedList<Integer> getCountInHotelByTime(String dateIn, String dateOut) {
        LinkedList<Integer> count = new LinkedList<>();
        LinkedList<String> hotels = getHotels();
        try {
            for (String hotel : hotels) {
                int counter = 0;
                String sql = "SELECT COUNT(DISTINCT (CLIENTS.ID)), COUNT(CLIENTS.CHILDREN_ID) FROM CLIENTS INNER JOIN TRIP ON CLIENTS.ID = TRIP.ID_CLIENT\n" +
                        "INNER JOIN BOOKING_ROOM ON TRIP.BOOKING_ROOM_ID = BOOKING_ROOM.ID INNER JOIN HOTELS\n" +
                        "ON BOOKING_ROOM.ID_HOTEL = HOTELS.ID WHERE HOTELS.NAME = '" + hotel + "' AND DATE_IN between\n" +
                        "to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut + "', 'dd.mm.yyyy') AND\n" +
                        "DATE_OUT between to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut + "', 'dd.mm.yyyy')";
                Statement statement = null;
                statement = connection.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE
                );
                ResultSet result = statement.executeQuery(sql);
                result.next();
                counter = result.getInt(1) + result.getInt(2);
                result.close();
                count.add(counter);
            }

            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    /* 6 */

    public int getCountTouristInCountryPerPeriod(String start, String end) {
        try {
            String sql = "SELECT COUNT(DISTINCT(CLIENTS.ID)), COUNT(CLIENTS.CHILDREN_ID) FROM CLIENTS INNER JOIN TRIP ON CLIENTS.ID = TRIP.ID_CLIENT\n" +
                    "INNER JOIN REST_TOURIST ON TRIP.ID = REST_TOURIST.ID_TRIP INNER JOIN EXCURSION ON REST_TOURIST.ID_EXCURSION = EXCURSION.ID\n" +
                    "WHERE EXCURSION.DATE_EX between to_date('" + start + "', 'dd.mm.yyyy') and to_date('" + end + "', 'dd.mm.yyyy')";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            return result.getInt(1) + result.getInt(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /* 7 */

    public LinkedList<String> getGoodAgencyNames() {
        LinkedList<String> linkedList = new LinkedList<>();
        try {
            String sql = "SELECT AGENCY.NAME FROM EXCURSION INNER JOIN AGENCY ON EXCURSION.ID_AGENCY = AGENCY.ID\n" +
                    "WHERE EXCURSION.ID in (SELECT ID FROM EXCURSION WHERE EXCURSION.RATE = (SELECT MAX(RATE) FROM EXCURSION)) GROUP BY AGENCY.NAME\n" +
                    "ORDER BY COUNT(AGENCY.NAME) DESC";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                linkedList.add(result.getString(1));
            }
            result.close();
            return linkedList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return linkedList;
    }

    public String getMostPopularExcursion() {
        String mostPopularExcursion = "";
        try {
            String sql = "SELECT EXCURSION.TITLE FROM REST_TOURIST INNER JOIN EXCURSION ON ID_EXCURSION = EXCURSION.ID\n" +
                    "WHERE REST_TOURIST.ID = (SELECT MIN(ID) FROM REST_TOURIST) GROUP BY TITLE\n" +
                    "ORDER BY COUNT(EXCURSION.TITLE) DESC";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            mostPopularExcursion = result.getString(1);
            result.close();
            return mostPopularExcursion;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mostPopularExcursion;
    }

    /* 8 */

    public AboutFlightData getDataAboutFlight(int flightID, boolean isCargoPlane) {
        AboutFlightData data = null;
        try {
            String sql = "SELECT SUM(COUNT), SUM(WEIGHT), SUM(VOLUME) FROM CARGO INNER JOIN FLIGHT ON CARGO.ID_FLIGHT_IN = FLIGHT.ID\n" +
                    "    INNER JOIN STATEMENT ON CARGO.ID_STATEMENT = STATEMENT.ID WHERE FLIGHT.ID = " + flightID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            data = new AboutFlightData(result.getInt(2), result.getInt(3), 0);
            result.close();
            if (!isCargoPlane) {
                String sql2 = "SELECT COUNT(TICKETS.ID_FLIGHT) FROM TICKETS WHERE ID_FLIGHT = " + flightID;
                Statement statement2 = null;
                statement2 = connection.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE
                );
                ResultSet result2 = statement2.executeQuery(sql2);
                result.next();
                data.setCountForPassenger(result2.getInt(1));
            }
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    /* 9 */

    public DataAboutWarehouse getWarehouseData(int warehouseID, String dateIn, String dateOut) {
        DataAboutWarehouse dataAboutWarehouse = null;
        try {
            String sql = "SELECT SUM(COUNT), SUM(WEIGHT) FROM CARGO INNER JOIN STATEMENT ON CARGO.ID_STATEMENT = STATEMENT.ID\n" +
                    "WHERE CARGO.ID_WAREHOUSE = " + warehouseID + " AND DATE_IN between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy')";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            dataAboutWarehouse = new DataAboutWarehouse(result.getInt(2), result.getInt(1));
            result.close();
            return dataAboutWarehouse;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dataAboutWarehouse;
    }

    public DataAboutFlightsWithWarehouse getWarehouseDataAroundFlight(int warehouseID, String dateIn, String dateOut) {
        DataAboutFlightsWithWarehouse dataAboutFlightsWithWarehouse = null;
        try {
            int passenger = 0;
            int cargo = 0;
            String sqlPassenger = "SELECT COUNT(CARGO.ID) FROM CARGO INNER JOIN FLIGHT ON CARGO.ID_FLIGHT_IN = FLIGHT.ID\n" +
                    "INNER JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID WHERE CARGO.ID_WAREHOUSE = " + warehouseID + " AND DATE_IN\n" +
                    "between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy') AND IS_CARGOPLANE = '0'";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sqlPassenger);
            result.next();
            passenger = result.getInt(1);
            result.close();
            String sqlCargo = "SELECT COUNT(CARGO.ID) FROM CARGO INNER JOIN FLIGHT ON CARGO.ID_FLIGHT_IN = FLIGHT.ID\n" +
                    "INNER JOIN AIRPLANE ON FLIGHT.AIRPLANE_ID = AIRPLANE.ID WHERE CARGO.ID_WAREHOUSE = " + warehouseID + " AND DATE_IN\n" +
                    "between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy') AND IS_CARGOPLANE = '1'";
            ResultSet result2 = statement.executeQuery(sqlCargo);
            result2.next();
            cargo = result2.getInt(1);
            result2.close();
            dataAboutFlightsWithWarehouse = new DataAboutFlightsWithWarehouse(cargo, passenger);
            return dataAboutFlightsWithWarehouse;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dataAboutFlightsWithWarehouse;
    }

    /* 10 */

    public LinkedList<TransactionData> getExcursionClientsTransaction(int idGroup, boolean isOnlyWork, boolean isOnlyTravel) {
        LinkedList<TransactionData> transactionData =  new LinkedList<>();
        try {
            String sql = "SELECT TRANSACTIONS.SUM, TRANSACTIONS.NAME, TRANSACTIONS.IS_INCOME FROM TRIP INNER JOIN CLIENTS ON TRIP.ID_CLIENT = CLIENTS.ID\n" +
                    "INNER JOIN TRANSACTIONS ON CLIENTS.ID = TRANSACTIONS.ID_CLIENT WHERE TRANSACTIONS.NAME = 'trans for excursion '";
            if (-1 != idGroup) {
                sql += " AND TRIP.ID_GROUP = " + idGroup;
            }
            if (isOnlyWork) {
                    sql += " AND CLIENTS.TRAVEL_TARGET = 'WORK'";
            } else if (isOnlyTravel) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'TRAVEL'";
            }

            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                boolean isIncome;
                if ("0".equals(result.getString(3))) {
                    isIncome = false;
                } else {
                    isIncome = true;
                }
                TransactionData transaction = new TransactionData(result.getInt(1), result.getString(2), isIncome);
                transactionData.add(transaction);
            }
            result.close();
            return transactionData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactionData;
    }

    public LinkedList<TransactionData> getTicketTransaction(int idGroup, boolean isOnlyWork, boolean isOnlyTravel) {
        LinkedList<TransactionData> transactionData =  new LinkedList<>();
        try {
            String sql = "SELECT TRANSACTIONS.SUM, TRANSACTIONS.NAME, TRANSACTIONS.IS_INCOME FROM TRIP INNER JOIN CLIENTS ON TRIP.ID_CLIENT = CLIENTS.ID\n" +
                    "INNER JOIN TRANSACTIONS ON CLIENTS.ID = TRANSACTIONS.ID_CLIENT WHERE TRANSACTIONS.NAME = 'trans for passenger flight'";
            if (-1 != idGroup) {
                sql += " AND TRIP.ID_GROUP = " + idGroup;
            }
            if (isOnlyWork) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'WORK'";
            } else if (isOnlyTravel) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'TRAVEL'";
            }
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                boolean isIncome;
                if ("0".equals(result.getString(3))) {
                    isIncome = false;
                } else {
                    isIncome = true;
                }
                TransactionData transaction = new TransactionData(result.getInt(1), result.getString(2), isIncome);
                transactionData.add(transaction);
            }
            result.close();
            return transactionData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactionData;
    }

    public LinkedList<TransactionData> getTransForHotel(int idGroup, boolean isOnlyWork, boolean isOnlyTravel) {
        LinkedList<TransactionData> transactionData =  new LinkedList<>();
        try {
            String sql = "SELECT TRANSACTIONS.SUM, TRANSACTIONS.NAME, TRANSACTIONS.IS_INCOME FROM TRIP INNER JOIN CLIENTS ON TRIP.ID_CLIENT = CLIENTS.ID\n" +
                    "INNER JOIN TRANSACTIONS ON CLIENTS.ID = TRANSACTIONS.ID_CLIENT WHERE TRANSACTIONS.NAME = 'trans for hotel'";
            if (-1 != idGroup) {
                sql += " AND TRIP.ID_GROUP = " + idGroup;
            }
            if (isOnlyWork) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'WORK'";
            } else if (isOnlyTravel) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'TRAVEL'";
            }
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                boolean isIncome;
                if ("0".equals(result.getString(3))) {
                    isIncome = false;
                } else {
                    isIncome = true;
                }
                TransactionData transaction = new TransactionData(result.getInt(1), result.getString(2), isIncome);
                transactionData.add(transaction);
            }
            result.close();
            return transactionData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactionData;
    }

    public LinkedList<TransactionData> getTransForCargo(int idGroup, boolean isOnlyWork, boolean isOnlyTravel) {
        LinkedList<TransactionData> transactionData =  new LinkedList<>();
        try {
            String sql = "SELECT TRANSACTIONS.SUM, TRANSACTIONS.NAME, TRANSACTIONS.IS_INCOME FROM TRIP INNER JOIN CLIENTS ON TRIP.ID_CLIENT = CLIENTS.ID\n" +
                    "INNER JOIN TRANSACTIONS ON CLIENTS.ID = TRANSACTIONS.ID_CLIENT WHERE TRANSACTIONS.NAME = 'trans for cargo'";
            if (-1 != idGroup) {
                sql += " AND TRIP.ID_GROUP = " + idGroup;
            }
            if (isOnlyWork) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'WORK'";
            } else if (isOnlyTravel) {
                sql += " AND CLIENTS.TRAVEL_TARGET = 'TRAVEL'";
            }
            System.out.println(sql);

            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                boolean isIncome;
                if ("0".equals(result.getString(3))) {
                    isIncome = false;
                } else {
                    isIncome = true;
                }
                TransactionData transaction = new TransactionData(result.getInt(1), result.getString(2), isIncome);
                transactionData.add(transaction);
            }
            result.close();
            return transactionData;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return transactionData;
    }

    /* 11 */

    public PaymentData getAirplanePaymentData(String dateIn, String dateOut) {
        PaymentData data = new PaymentData(0,0);
        try {
            String sql = "SELECT SUM(TRANSACTIONS.SUM) FROM TICKETS INNER JOIN FLIGHT ON TICKETS.ID_FLIGHT = FLIGHT.ID\n" +
                    "INNER JOIN TRANSACTIONS ON TICKETS.ID_TRANS = TRANSACTIONS.ID WHERE FLIGHT.FLY_DATE between\n" +
                    "to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut + "', 'dd.mm.yyyy')";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            data.setPlus(result.getInt(1));
            result.close();

            String sql2 = "SELECT SUM(TRANSACTIONS.SUM) FROM TICKETS INNER JOIN FLIGHT ON TICKETS.ID_FLIGHT = FLIGHT.ID\n" +
                    "INNER JOIN TRANSACTIONS ON FLIGHT.ID_TRANS = TRANSACTIONS.ID WHERE FLIGHT.FLY_DATE between\n" +
                    "to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut + "', 'dd.mm.yyyy')";
            ResultSet result2 = statement.executeQuery(sql2);
            result2.next();
            data.setMinus(result2.getInt(1));
            result2.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    public PaymentData getHostelPaymentData(String dateIn, String dateOut) {
        PaymentData data = new PaymentData(0,0);
        try {
            String sql = "SELECT SUM(TRANSACTIONS.SUM) FROM TRIP INNER JOIN BOOKING_ROOM ON TRIP.BOOKING_ROOM_ID = BOOKING_ROOM.ID\n" +
                    "INNER JOIN TRANSACTIONS ON BOOKING_ROOM.ID_TRANS = TRANSACTIONS.ID WHERE TRIP.DATE_IN\n" +
                    "between to_date('" +  dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut + "', 'dd.mm.yyyy')\n" +
                    "OR TRIP.DATE_OUT between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy')";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            data.setMinus(result.getInt(1));
            data.setPlus((int)((double)data.getMinus() * 1.15f));
            result.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    public PaymentData getExcursionPaymentData(String dateIn, String dateOut) {
        PaymentData data = new PaymentData(0,0);
        try {
            String sql = "SELECT SUM(TRANSACTIONS.SUM) FROM REST_TOURIST INNER JOIN EXCURSION ON REST_TOURIST.ID_EXCURSION = EXCURSION.ID\n" +
                    "INNER JOIN TRANSACTIONS ON REST_TOURIST.ID_TRANS = TRANSACTIONS.ID WHERE\n" +
                    "EXCURSION.DATE_EX between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('"+ dateOut + "', 'dd.mm.yyyy')";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            data.setPlus(result.getInt(1));
            result.close();

            String sql2 = "SELECT SUM(TRANSACTIONS.SUM) FROM REST_TOURIST INNER JOIN EXCURSION ON REST_TOURIST.ID_EXCURSION = EXCURSION.ID\n" +
                    "INNER JOIN TRANSACTIONS ON EXCURSION.TRANS_ID = TRANSACTIONS.ID WHERE\n" +
                    "EXCURSION.DATE_EX between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy')";
            ResultSet result2 = statement.executeQuery(sql2);
            result2.next();
            data.setMinus(result2.getInt(1));
            result2.close();
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    public int getVisaProfit(String dateIn, String dateOut) {
        try {
            String sql = "SELECT COUNT(TRIP.ID), COUNT(CLIENTS.CHILDREN_ID) FROM CLIENTS INNER JOIN TRIP ON CLIENTS.ID = TRIP.ID_CLIENT\n" +
                    "WHERE TRIP.DATE_IN between to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy')\n" +
                    "   OR TRIP.DATE_OUT between to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy')";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int sum = result.getInt(1) * 100 + result.getInt(2) * 120;
             result.close();
            return sum;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    /* 12 */

    private LinkedList<String> getName() {
        LinkedList<String> names = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT CARGO.KIND FROM CARGO";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                names.add(result.getString(1));
            }
            result.close();
            return names;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return names;
    }

    private DataAboutCargo getInfoAboutStream() {
        DataAboutCargo aboutCargo = new DataAboutCargo("all",0.0f,0.0f, 0.0f);
        try {
            String sql = "SELECT SUM(STATEMENT.COUNT), SUM(STATEMENT.WEIGHT), SUM(STATEMENT.VOLUME) FROM STATEMENT";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            aboutCargo.setCount(result.getInt(1));
            aboutCargo.setWeight(result.getInt(2));
            aboutCargo.setVolume(result.getInt(3));
            result.close();
            return aboutCargo;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return aboutCargo;
    }

    public LinkedList<DataAboutCargo> getDataAboutCargosStream() {
        LinkedList<DataAboutCargo> cargos = new LinkedList<>();
        LinkedList<String> cargoName = getName();
        DataAboutCargo infoAboutAllStream = getInfoAboutStream();
        try {
            for (String kind : cargoName) {
                DataAboutCargo dataAboutCargo = new DataAboutCargo(kind, 0, 0, 0);
                String sql = "SELECT SUM(STATEMENT.COUNT), SUM(STATEMENT.WEIGHT), SUM(STATEMENT.VOLUME) FROM CARGO INNER JOIN STATEMENT\n" +
                        "ON CARGO.ID_STATEMENT = STATEMENT.ID WHERE KIND = '" + kind +"'";
                Statement statement = null;
                statement = connection.createStatement(
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_UPDATABLE
                );
                ResultSet result = statement.executeQuery(sql);
                result.next();
                dataAboutCargo.setCount((double)result.getInt(1) / infoAboutAllStream.getCount());
                dataAboutCargo.setWeight((double)result.getInt(2) / infoAboutAllStream.getWeight());
                dataAboutCargo.setVolume((double)result.getInt(3) / infoAboutAllStream.getVolume());
                result.close();
                cargos.add(dataAboutCargo);
            }
            return cargos;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cargos;
    }

    /* 13 */

    private int getSumOfPlus() {
        try {
            String sql = "SELECT SUM(TRANSACTIONS.SUM) FROM TRANSACTIONS WHERE TRANSACTIONS.IS_INCOME = '1'";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int count = result.getInt(1);
            result.close();
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 1;
    }

    private int getSumOfMinus() {
        try {
            String sql = "SELECT SUM(TRANSACTIONS.SUM) FROM TRANSACTIONS WHERE TRANSACTIONS.IS_INCOME = '0'";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int count = result.getInt(1);
            result.close();
            return count;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 1;
    }

    public int getRent() {
        double income = getSumOfPlus();
        double noIncome = getSumOfMinus();
        return (int)((income/ noIncome) * 100);
    }

    /* 14 */
    public int getPercentOfShopTourToAnother(String dateIn, String dateOut) {
        int percent = 0;
        try {
            String sql = "SELECT COUNT(TRIP.ID) FROM TRIP WHERE IS_SHOPTUR = '1' AND\n(" +
                    "TRIP.DATE_IN between to_date('" + dateIn + "', 'dd.mm.yyyy') and to_date('" +  dateOut + "', 'dd.mm.yyyy')\n" +
                    "OR TRIP.DATE_OUT between to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy'))\n" +
                    "UNION\n" +
                    "SELECT COUNT(TRIP.ID) FROM TRIP WHERE IS_SHOPTUR = '0' AND\n(" +
                    "TRIP.DATE_IN between to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy')\n" +
                    "OR TRIP.DATE_OUT between to_date('" + dateIn +"', 'dd.mm.yyyy') and to_date('" + dateOut +"', 'dd.mm.yyyy'))";
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            double countShopTourist = 0;
            double countJustTourist = 0;
            for (int i = 0; i < 2; i++) {
                result.next();
                if (0 == i) {
                    countShopTourist = result.getInt(1);
                } else {
                    countJustTourist = result.getInt(1);
                }
            }
            return (int) (countJustTourist / countShopTourist * 100);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return percent;
    }

    /* 15 */

    public LinkedList<AboutPassengerData> getAboutPassengerData(int flightID) {
        LinkedList<AboutPassengerData> data = new LinkedList<>();
        try {
            String sql = "SELECT CLIENTS.NAME, CLIENTS.LAST_NAME, TRIP.ID_GROUP, CARGO.ID, CARGO.KIND, STATEMENT.ID, HOTELS.NAME FROM TICKETS INNER JOIN FLIGHT ON TICKETS.ID_FLIGHT = FLIGHT.ID\n" +
                    "INNER JOIN TRIP ON TICKETS.ID_TRIP = TRIP.ID INNER JOIN CARGO_TOURIST ON TRIP.ID = CARGO_TOURIST.ID_TRIP\n" +
                    "INNER JOIN CARGO ON CARGO_TOURIST.ID_CARGO = CARGO.ID INNER JOIN STATEMENT ON CARGO.ID_STATEMENT = STATEMENT.ID\n" +
                    "INNER JOIN BOOKING_ROOM ON TRIP.BOOKING_ROOM_ID = BOOKING_ROOM.ID INNER JOIN HOTELS ON BOOKING_ROOM.ID_HOTEL = HOTELS.ID INNER JOIN\n" +
                    "CLIENTS ON TRIP.ID_CLIENT = CLIENTS.ID WHERE\n" +
                    "FLIGHT.ID = " + flightID;
            Statement statement = null;
            statement = connection.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE
            );
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                AboutPassengerData aboutPassengerData = new AboutPassengerData();
                aboutPassengerData.setName(result.getString(1));
                aboutPassengerData.setLastName(result.getString(2));
                aboutPassengerData.setGroupNumber(result.getInt(3));
                aboutPassengerData.setMark(result.getInt(4));
                aboutPassengerData.setKind(result.getString(5));
                aboutPassengerData.setBirk(result.getInt(6));
                aboutPassengerData.setHotelName(result.getString(7));
                data.add(aboutPassengerData);
            }
            return data;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
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
