package optidb.server.model;

public class Resultat {
    private final String platformName;
    private final long tempsCreate;
    private final long tempsInsert;
    private final long tempsDelete;

    public Resultat(String platformName, long tempsCreate, long tempsInsert, long tempsDelete) {
        this.platformName = platformName;
        this.tempsCreate = tempsCreate;
        this.tempsInsert = tempsInsert;
        this.tempsDelete = tempsDelete;
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

    public long getTempsDelete() {
        return tempsDelete;
    }
}
