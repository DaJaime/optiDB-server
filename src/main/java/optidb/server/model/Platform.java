package optidb.server.model;

public class Platform {
    private String name;
    private String docker;
    private String currentVersion;
    private String description;
    private String typeModel;
    private String logo;
    private String website;
    private String developer;
    private String initialRelease;
    private String license;
    private String requetage;

    public Platform(String docker, String curr) {
        this.docker = docker;
        this.currentVersion = curr;
    }

    public Platform(String docker, String name, String currentVersion, String description, String typeModel,
                    String logo, String website, String developer, String initialRelease, String license,
                    String requetage) {
        this.docker = docker;
        this.name = name;
        this.currentVersion = currentVersion;
        this.description = description;
        this.typeModel = typeModel;
        this.logo = logo;
        this.website = website;
        this.developer = developer;
        this.initialRelease = initialRelease;
        this.license = license;
        this.requetage = requetage;
    }

    public String getDocker() {
        return docker;
    }

    public void setDocker(String docker) {
        this.docker = docker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeModel() {
        return typeModel;
    }

    public void setTypeModel(String typeModel) {
        this.typeModel = typeModel;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getInitialRelease() {
        return initialRelease;
    }

    public void setInitialRelease(String initialRelease) {
        this.initialRelease = initialRelease;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getRequetage() {
        return requetage;
    }

    public void setRequetage(String requetage) {
        this.requetage = requetage;
    }

}
