package optidb.server.platformConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

class Connect {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    void dockerRun(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            Thread.sleep(20000);
        } catch (IOException e) {
            myLog.warning(e.toString());

        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
    }

    Connection connect(String url) {
        Connection cx = null;
        try {
            cx = DriverManager.getConnection(url,"root","pass");
        }
        catch (SQLException e) { // accès à la base refusé
            myLog.warning(e.toString());
        }
        return cx;
    }

    void dockerClose(String dbName, Connection cx) {
        try {
            if (cx != null) cx.close();
            Process processClose = Runtime.getRuntime().exec("docker stop "+dbName);
            processClose.waitFor();
            Thread.sleep(4000);
            Process processRm = Runtime.getRuntime().exec("docker rm "+dbName);
            processRm.waitFor();
            Thread.sleep(4000);
        } catch (IOException | SQLException e) {
            myLog.warning(e.toString());
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
    }
}
