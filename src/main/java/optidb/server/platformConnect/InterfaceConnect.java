package optidb.server.platformConnect;

import java.sql.Connection;

public interface InterfaceConnect {
    void dockerRun ();
    Connection connect ();
    void dockerClose(Connection cx);
}
