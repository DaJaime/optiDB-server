package optidb.server.model;

public class Resultat {
    private final String platformName;
    private final long tempsCreate;
    private final long tempsInsert;
    private final long tempsUpdate;
    private final long tempsAlter;
    private final long tempsDelete;
    private final long tempsSelect;
    private final long tempsDrop;

    public Resultat(String platformName, long tempsCreate, long tempsInsert, long tempsUpdate, long tempsSelect, long tempsAlter, long tempsDelete, long tempsDrop) {
        this.platformName = platformName;
        this.tempsCreate = tempsCreate;
        this.tempsInsert = tempsInsert;
        this.tempsUpdate = tempsUpdate;
        this.tempsAlter = tempsAlter;
        this.tempsDelete = tempsDelete;
        this.tempsSelect = tempsSelect;
        this.tempsDrop = tempsDrop;
    }

    public String getPlatformName() {
        return platformName;
    }

    public long getTempsCreate() {
        return tempsCreate;
    }

    public long getTempsInsert() {
        return tempsInsert;
    }

    public long getTempsUpdate() {
        return tempsUpdate;
    }

    public long getTempsSelect() { return tempsSelect; }

    public long getTempsDelete() { return tempsDelete; }

    public long getTempsAlter() { return tempsAlter; }

    public long getTempsDrop() { return tempsDrop; }
}
