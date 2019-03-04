package optidb.server.platformConnect;

import java.sql.Connection;

public class MariadbConnect implements InterfaceConnect {
    private Connect connect = new Connect();

    @Override
    public void dockerRun() {
        connect.dockerRun("docker run -p 3306:3306 --name mariadb -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mariadb:10.4");
    }

    @Override
    public Connection connect() {
        Connection cx = connect.connect("jdbc:mariadb://172.17.0.2:3306/test");
        return cx;
    }

    @Override
    public void dockerClose(Connection cx) {
        connect.dockerClose("mariadb", cx);
    }
}
