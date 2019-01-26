package optidb.server.model;

public class Platform {
    private final String name;
    private final String currentVersion;

    public Platform(String name, String version) {
        this.name = name;
        this.currentVersion = version;
    }

    public String getName() {
        return name;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

}
