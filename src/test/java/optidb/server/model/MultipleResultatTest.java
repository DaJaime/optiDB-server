package optidb.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MultipleResultatTest {
    MultipleResultat res;

    @Before
    public void setUp() throws Exception {
        ArrayList listeInsert = new ArrayList();
        Resultat resuUn = new Resultat("Test",1, 1, 1, listeInsert, 1,
                1, 1, 1,1, 1);
        ArrayList<Resultat> array = new ArrayList<>();
        array.add(resuUn);
        res = new MultipleResultat(array);
    }

    @Test
    public void getListResu() throws Exception {
        ArrayList listeInsert = new ArrayList();
        Resultat newResu = new Resultat("Test",1, 1, 1, listeInsert,
                1, 1, 1, 1,1, 1);
        assertEquals(newResu.getClass(), res.getListResu().get(0).getClass());
    }

}