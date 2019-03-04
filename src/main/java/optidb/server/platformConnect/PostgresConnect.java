package optidb.server.platformConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PostgresConnect implements InterfaceConnect {
    private static Logger myLog = Logger.getLogger("WarningLogging");
    @Override
    public void dockerRun() {
        try {
            Process process = Runtime.getRuntime().exec("docker run -p 5432:5432 --name postgres -e POSTGRES_USER=root -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=test -d postgres:11.2");
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
            cx = DriverManager.getConnection("jdbc:postgresql://172.17.0.2:5432/test","root","pass");
        }
        catch (SQLException e) { // accès à la base refusé
            myLog.warning(e.toString());
        }
        return cx;
    }

    @Override
    public void dockerClose(Connection cx) {
        try {
            if (cx != null) cx.close();
            Process processClose = Runtime.getRuntime().exec("docker stop postgres");
            processClose.waitFor();
            Thread.sleep(4000);
            Process processRm = Runtime.getRuntime().exec("docker rm postgres");
            processRm.waitFor();
            Thread.sleep(4000);
        } catch (IOException|SQLException e) {
            myLog.warning(e.toString());
        } catch (InterruptedException e) {
            myLog.warning(e.toString());
            Thread.currentThread().interrupt();
        }
    }
}
