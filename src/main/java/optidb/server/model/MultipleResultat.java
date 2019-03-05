package optidb.server.model;

import java.util.List;

public class MultipleResultat {
    private final List<Resultat> listResu;

    public MultipleResultat(List<Resultat> listResu) {
        this.listResu = listResu;
    }

    public List<Resultat> getListResu() {
        return listResu;
    }
}
