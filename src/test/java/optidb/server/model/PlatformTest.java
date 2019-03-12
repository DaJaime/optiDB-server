package optidb.server.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlatformTest {
    private Platform platConstructUn;
    private Platform platConstructDeux;
    @Before
    public void setUp() throws Exception {
        platConstructUn = new Platform("Test", "1.0");
        platConstructDeux = new Platform("Docker", "Name", "Version", "Desc",
                "Type", "Logo", "Website", "Dev", "Release",
                "License", "Requ");
    }

    @Test
    public void getDocker() throws Exception {
        assertEquals("Docker", platConstructDeux.getDocker());
    }

    @Test
    public void setGetDocker() throws Exception {
        platConstructUn.setDocker("Docker");
        assertEquals("Docker", platConstructUn.getDocker());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Name", platConstructDeux.getName());
    }

    @Test
    public void setName() throws Exception {
        platConstructUn.setName("Name");
        assertEquals("Name", platConstructUn.getName());
    }

    @Test
    public void getCurrentVersion() throws Exception {
        assertEquals("Version", platConstructDeux.getCurrentVersion());

    }

    @Test
    public void setCurrentVersion() throws Exception {
        platConstructUn.setCurrentVersion("1.1");
        assertEquals("1.1", platConstructUn.getCurrentVersion());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("Desc", platConstructDeux.getDescription());

    }

    @Test
    public void setDescription() throws Exception {
        platConstructUn.setDescription("Desc");
        assertEquals("Desc", platConstructUn.getDescription());
    }

    @Test
    public void getTypeModel() throws Exception {
        assertEquals("Type", platConstructDeux.getTypeModel());

    }

    @Test
    public void setTypeModel() throws Exception {
        platConstructUn.setTypeModel("Model");
        assertEquals("Model", platConstructUn.getTypeModel());
    }

    @Test
    public void getLogo() throws Exception {
        assertEquals("Logo", platConstructDeux.getLogo());

    }

    @Test
    public void setLogo() throws Exception {
        platConstructUn.setLogo("https://logo");
        assertEquals("https://logo", platConstructUn.getLogo());
    }

    @Test
    public void getWebsite() throws Exception {
        assertEquals("Website", platConstructDeux.getWebsite());

    }

    @Test
    public void setWebsite() throws Exception {
        platConstructUn.setWebsite("www.test.com");
        assertEquals("www.test.com", platConstructUn.getWebsite());
    }

    @Test
    public void getDeveloper() throws Exception {
        assertEquals("Dev", platConstructDeux.getDeveloper());
    }

    @Test
    public void setDeveloper() throws Exception {
        platConstructUn.setDeveloper("Dev");
        assertEquals("Dev", platConstructUn.getDeveloper());
    }

    @Test
    public void getInitialRelease() throws Exception {
        assertEquals("Release", platConstructDeux.getInitialRelease());

    }

    @Test
    public void setInitialRelease() throws Exception {
        platConstructUn.setInitialRelease("1997");
        assertEquals("1997", platConstructUn.getInitialRelease());
    }

    @Test
    public void getLicense() throws Exception {
        assertEquals("License", platConstructDeux.getLicense());

    }

    @Test
    public void setLicense() throws Exception {
        platConstructUn.setLicense("License");
        assertEquals("License", platConstructUn.getLicense());
    }

    @Test
    public void getRequetage() throws Exception {
        assertEquals("Requ", platConstructDeux.getRequetage());

    }

    @Test
    public void setRequetage() throws Exception {
        platConstructUn.setRequetage("Req");
        assertEquals("Req", platConstructUn.getRequetage());
    }

}