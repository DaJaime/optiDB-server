package optidb.server.platformConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MariadbConnect implements InterfaceConnect {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    @Override
    public void dockerRun() {
        try {
            Process process = Runtime.getRuntime().exec("docker run -p 3306:3306 --name mariadb -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mariadb:10.4");
            process.waitFor();
            Thread.sleep(20000);
        } catch (IOException e) {
            myLog.warning(e.toString());

        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public Connection connect() {
        Connection cx = null;
        try {
            cx = DriverManager.getConnection("jdbc:mariadb://172.17.0.2:3306/test","root","pass");
        }
        catch (SQLException e) { // accès à la base refusé
            System.out.println("SQL : " + e.getMessage());
        }
        return cx;
    }

    @Override
    public void dockerClose(Connection cx) {
        try {
            if (cx != null) cx.close();
            Process processClose = Runtime.getRuntime().exec("docker stop mariadb");
            processClose.waitFor();
            Thread.sleep(4000);
            Process processRm = Runtime.getRuntime().exec("docker rm mariadb");
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
