import GUI.mainwindow.MainController;
import Server.DataBaseServer;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        DataBaseServer dataBaseDriver = new DataBaseServer();
        MainController mainWindow = new MainController(dataBaseDriver);
    }
}
