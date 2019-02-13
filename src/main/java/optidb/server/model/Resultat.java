package optidb.server.model;

import java.util.ArrayList;

public class Resultat {
    private final String platformName;
    private final int nbCol;
    private final int nbLine;
    private final long tempsCreate;
    private final ArrayList listeInsert;
    private final long tempsUpdate;
    private final long tempsAlter;
    private final long tempsDelete;
    private final long tempsSelectAll;
    private final long tempsSelect;
    private final long tempsDrop;

    public Resultat(String platformName, int nbCol, int nbLine, long tempsCreate, ArrayList listeInsert, long tempsUpdate, long tempsSelect, long tempsSelectAll, long tempsAlter, long tempsDelete, long tempsDrop) {
        this.platformName = platformName;
        this.nbCol = nbCol;
        this.nbLine = nbLine;
        this.tempsCreate = tempsCreate;
        this.listeInsert = listeInsert;
        this.tempsUpdate = tempsUpdate;
        this.tempsAlter = tempsAlter;
        this.tempsDelete = tempsDelete;
        this.tempsSelect = tempsSelect;
        this.tempsSelectAll = tempsSelectAll;
        this.tempsDrop = tempsDrop;
    }

    public String getPlatformName() {
        return platformName;
    }

    public int getNbCol() { return nbCol; }

    public int getNbLine() { return nbLine; }

    public long getTempsCreate() {
        return tempsCreate;
    }

    public ArrayList getListeInsert() {
        return listeInsert;
    }

    public long getTempsUpdate() {
        return tempsUpdate;
    }

    public long getTempsSelect() { return tempsSelect; }

    public long getTempsSelectAll() { return tempsSelectAll; }

    public long getTempsDelete() { return tempsDelete; }

    public long getTempsAlter() { return tempsAlter; }

    public long getTempsDrop() { return tempsDrop; }
}
