package sqlinjectiontests;

import dev.begell.utilities.ConnectionUtil;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InjectionTests {

    @Test
    void injection() throws SQLException {

        Connection conn = ConnectionUtil.createConnection();
        assert conn != null;
        Statement statement = conn.createStatement();
        String first = "Timmy";
        String last = "Turner";
        String sql = "insert into employee values(default, " + "'" + first + "'," + "'" + last+ "');";
        statement.execute(sql);
    }
}
