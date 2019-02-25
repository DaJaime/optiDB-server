package optidb.server.platformConnect;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class MysqlConnect implements InterfaceConnect {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    public void dockerRun () {
        try {
            Process process = Runtime.getRuntime().exec("docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mysql:8.0.14");
            process.waitFor();
            Thread.sleep(20000);
        } catch (IOException e) {
            myLog.warning(e.toString());

        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
    }

    public Connection connect () {
        Connection cx = null;
        try {
            cx = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/test","root","pass");
        }
        catch (SQLException e) { // accès à la base refusé
            System.out.println("SQL : " + e.getMessage());
        }
        return cx;
    }

    public void dockerClose(Connection cx) {
        try {
            if (cx != null) cx.close();
            Process processClose = Runtime.getRuntime().exec("docker stop mysql");
            processClose.waitFor();
            Thread.sleep(4000);
            Process processRm = Runtime.getRuntime().exec("docker rm mysql");
            processRm.waitFor();
            Thread.sleep(4000);
        } catch (IOException e) {
            myLog.warning(e.toString());
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            System.out.println("Close connection");
            myLog.warning(e.toString());
        }
    }


}
