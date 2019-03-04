package optidb.server.model;

import optidb.server.platformConnect.InterfaceConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SqlTest {
    private static Logger myLog = Logger.getLogger("WarningLogging");

    private long executeUpdate (Connection cx, String requete, boolean sleep){
        Statement stmt = null;
        long debut = 0;
        long fin = 0;
        try {
            stmt = cx.createStatement();
            debut = System.currentTimeMillis();
            stmt.executeUpdate(requete);
            fin = System.currentTimeMillis();
        } catch (SQLException e) {
            myLog.warning(e.toString());
        }
        finally {
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                myLog.warning(e.toString());
            }
        }
        this.sleepDemiSec(sleep);
        return fin - debut;
    }

    private long executeQuery (Connection cx, String requete){
        Statement stmt = null;
        ResultSet rs = null;
        long debut = 0;
        long fin = 0;
        try {
            stmt = cx.createStatement();
            debut = System.currentTimeMillis();
            rs = stmt.executeQuery(requete);
            fin = System.currentTimeMillis();
            rs.next();
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            myLog.warning(e.toString());
        }
        finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    myLog.warning(e.toString());
                }
            }
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
                myLog.warning(e.toString());
            }
        }
        this.sleepDemiSec(true);
        return fin - debut;
    }

    private long createTable(Connection cx, int nbCol, int cle){
        StringBuilder sb = new StringBuilder();
        // On crer autant de collones que demandé
        if(cle == 0) {
            sb.append("CREATE TABLE table_test (id INT");
        }
        else {
            sb.append("CREATE TABLE table_test (id INT PRIMARY KEY NOT NULL");
        }
        if(nbCol>1) {
            for (int i = 1; i < nbCol; i++) {
                sb.append(", col").append(i).append(" VARCHAR(100)");
            }
        }
        sb.append(")");
        // Execution
        long temps = executeUpdate(cx, sb.toString(), true);

        return temps;
    }

    private ArrayList insertAllLine(Connection cx, int nbCol, int nbLine){
        ArrayList listeInsert = new ArrayList();
        for (int y = 1; y<nbLine+1;y++) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO table_test values('").append(y).append("'");
            if (nbCol > 1) {
                for (int i = 1; i < nbCol; i++) {
                    sb.append(",'info").append(i).append("'");
                }
            }
            sb.append(")");
            //On ajoute le temps à la liste tout les 10% pour voir l'évolution
            if(nbLine>=10){
                int interval = nbLine/10;
                if(y % interval == 0) {
                    long temps = executeUpdate(cx, sb.toString(), false);
                    listeInsert.add(temps);
                }
                else{
                    executeUpdate(cx, sb.toString(), false);
                }
            }
            else{
                executeUpdate(cx, sb.toString(), false);
            }
        }
        return listeInsert;
    }

    public Resultat test(InterfaceConnect platform, String nameBD, int nbCol, int nbLine, int cle){
        // Init
        platform.dockerRun();
        Connection cx = platform.connect();

        //On commence les tests
        // Create
        long tempsCreate = this.createTable(cx, nbCol, cle);
        // Isert
        ArrayList listeInsert = this.insertAllLine(cx, nbCol, nbLine);
        // Update
        String requeteUpdate = "UPDATE table_test SET id = '"+nbLine+1+"' WHERE id=1";
        long tempsUpdate = executeUpdate(cx, requeteUpdate, true);
        // Select
        String requeteSelect = "SELECT * FROM table_test WHERE id = '1'";
        long tempsSelectOne = executeQuery(cx, requeteSelect);
        // Select All table
        String requeteSelectAll = "SELECT * FROM table_test";
        long tempsSelectAll = executeQuery(cx, requeteSelectAll);
        // Alter
        String requeteAlter = "ALTER TABLE table_test ADD newCol VARCHAR(255)";
        long tempsAlter = executeUpdate(cx, requeteAlter, true);
        // Delete
        String requeteDelete = "DELETE FROM table_test WHERE id = '1'";
        long tempsDelete = executeUpdate(cx, requeteDelete, true);
        // Drop
        String requeteDrop = "DROP TABLE table_test";
        long tempsDrop = executeUpdate(cx, requeteDrop, true);

        //On ferme la connection
        platform.dockerClose(cx);

        // on récup le résultat
        return new Resultat(nameBD, nbCol, nbLine, tempsCreate, listeInsert, tempsUpdate, tempsSelectOne,
                tempsSelectAll, tempsAlter, tempsDelete ,tempsDrop);
    }

    private void sleepDemiSec(boolean sleep){
        if (sleep) {
            // On attend une demi seconde
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                myLog.warning(e.toString());
                Thread.currentThread().interrupt();
            }
        }
    }
}
