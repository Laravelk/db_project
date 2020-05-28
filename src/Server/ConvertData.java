package Server;

import Data.AirplaneData;
import Data.ExcursionData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Vector;

public class ConvertData {
    final private DataBaseServer server;
    ConvertData(DataBaseServer server) {
        this.server = server;
    }

    LinkedList<ExcursionData> parse_excursion_data(ResultSet resultSet) throws SQLException {
        assert null != resultSet;
        LinkedList<ExcursionData> list = new LinkedList<>();
        while (resultSet.next()) {
            ExcursionData excursionData = new ExcursionData(resultSet.getInt(1), resultSet.getInt(2) ,getAgencyNameByID(resultSet.getInt(2)) , resultSet.getString(3),
                    resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(7));
            list.add(excursionData);
        }
        return list;
    }

    Vector<Vector<String>> parse_trip_data(ResultSet resultSet) throws  SQLException {
        assert null != resultSet;
        Vector<Vector<String>> table = new Vector<>();
        while (resultSet.next()) {
            Vector<String> row = new Vector<>();
            row.add(resultSet.getString(1));
            row.add(resultSet.getString(2));
            row.add(resultSet.getString(3));
            table.add(row);
        }
        return table;
    }

    private String getAgencyNameByID(int id) {
        return server.getAgencyNameByID(id);
    }
}
