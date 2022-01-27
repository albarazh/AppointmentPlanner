package com.example.appointmentplanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import android.os.StrictMode;
import android.util.Log;
public class DatenBank {
    private static String ip = "159.69.106.242";
    private static String port = "1433";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "appointment_planner";
    private static String username = "user@bala.de";
    private static String password = "0955698392Zoro";
    private static final String LOG = "DEBUG1";

    protected Connection connection()
    {
        String ConnURL = null;
        Connection connection = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";"
                    + "databaseName=" + database + ";user=" + username + ";password="
                    + password + ";";
            connection = DriverManager.getConnection(ConnURL);
            Log.d(LOG, "Connected to DB");
        } catch (SQLException e) {
            Log.d(LOG, "can´t Connected to DB");
        } catch (ClassNotFoundException e) {
            Log.d(LOG, e.getMessage());
        }
        return connection;
    }
}