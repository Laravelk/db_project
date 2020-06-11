import GUI.mainwindow.MainController;
import Server.DataBaseServer;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        if (3 != args.length) {
            System.out.println("Write ip, login and password in argument");
        }
        String ip = args[0];
        String user = args[1];
        String password = args[2];
        DataBaseServer dataBaseDriver = new DataBaseServer(ip, user, password);
        MainController mainWindow = new MainController(dataBaseDriver);
    }
}
