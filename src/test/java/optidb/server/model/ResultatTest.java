package optidb.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResultatTest {
    private Resultat resu;
    @Before
    public void setUp() throws Exception {
        ArrayList listeInsert = new ArrayList();
        resu = new Resultat("Test",1, 1, 1, listeInsert, 1,
                1, 1, 1,1, 1);
    }

    @Test
    public void getPlatformName() throws Exception {
        assertEquals("Test", resu.getPlatformName());
    }

    @Test
    public void getNbCol() throws Exception {
        assertEquals(1, resu.getNbCol());
    }

    @Test
    public void getNbLine() throws Exception {
        assertEquals(1, resu.getNbLine());

    }

    @Test
    public void getTempsCreate() throws Exception {
        assertEquals(1, resu.getTempsCreate());

    }

    @Test
    public void getListeInsert() throws Exception {
        ArrayList newArray = new ArrayList();
        assertEquals(newArray, resu.getListeInsert());
    }

    @Test
    public void getTempsUpdate() throws Exception {
        assertEquals(1, resu.getTempsUpdate());

    }

    @Test
    public void getTempsSelect() throws Exception {
        assertEquals(1, resu.getTempsSelect());

    }

    @Test
    public void getTempsSelectAll() throws Exception {
        assertEquals(1, resu.getTempsSelectAll());

    }

    @Test
    public void getTempsDelete() throws Exception {
        assertEquals(1, resu.getTempsDelete());

    }

    @Test
    public void getTempsAlter() throws Exception {
        assertEquals(1, resu.getTempsAlter());

    }

    @Test
    public void getTempsDrop() throws Exception {
        assertEquals(1, resu.getTempsDrop());

    }

}