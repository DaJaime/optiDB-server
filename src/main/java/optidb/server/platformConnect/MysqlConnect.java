package optidb.server.platformConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnect {

    public void dockerRun () {
        try {
            Process process = Runtime.getRuntime().exec("docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mysql:8.0.14");
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public void connect () {
        String driver = "com.mysql.jdbc.Driver";
        Connection cx = null;
        Statement stmt = null;
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection("jdbc:mysql://0.0.0.0:3306/test","root","pass");
            stmt = cx.createStatement();
            String requete = "CREATE TABLE table_test (nom VARCHAR(100), prenom VARCHAR(100))";
            int nb = stmt.executeUpdate(requete);

        }
        catch (ClassNotFoundException e) { // classe du pilote introuvable
            System.out.println(e);
        }
        catch (SQLException e) { // accès à la base refusé
            System.out.println(e);
        }
        finally {
            try { if (cx != null) cx.close(); }
            catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public void dockerClose () {
        try {
            Process processClose = Runtime.getRuntime().exec("docker stop mysql");
            processClose.waitFor();
            Process processRm = Runtime.getRuntime().exec("docker rm mysql");
            processRm.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }


}
