package optidb.server.platformConnect;

import java.sql.Connection;

public class MysqlConnect implements InterfaceConnect {
    private Connect connect = new Connect();

    @Override
    public void dockerRun () {
        connect.dockerRun("docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=test -d mysql:8.0.14");
    }

    @Override
    public Connection connect () {
        Connection cx = connect.connect("jdbc:mysql://172.17.0.2:3306/test");
        return cx;
    }

    @Override
    public void dockerClose(Connection cx) {
        connect.dockerClose("mysql", cx);
    }

}
