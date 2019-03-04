package optidb.server.model;

import java.util.ArrayList;

public class MultipleResultat {
    private final ArrayList<Resultat> listResu;

    public MultipleResultat(ArrayList<Resultat> listResu) {
        this.listResu = listResu;
    }

    public ArrayList<Resultat> getListResu() {
        return listResu;
    }
}
