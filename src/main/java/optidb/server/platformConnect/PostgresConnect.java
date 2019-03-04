package optidb.server.platformConnect;

import java.sql.Connection;

public class PostgresConnect implements InterfaceConnect {
    private Connect connect = new Connect();

    @Override
    public void dockerRun() {
        connect.dockerRun("docker run -p 5432:5432 --name postgres -e POSTGRES_USER=root -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=test -d postgres:11.2");
    }

    @Override
    public Connection connect() {
        Connection cx = connect.connect("jdbc:postgresql://172.17.0.2:5432/test");
        return cx;
    }

    @Override
    public void dockerClose(Connection cx) {
        connect.dockerClose("postgres", cx);
    }
}
