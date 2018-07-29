import com.ray.utils.DbUtils;
import org.junit.Test;

import java.sql.*;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-14 上午1:44
 **/
public class DbTest {

	@Test
	public void dbTest() throws SQLException {
		Connection c = DbUtils.getConnection();
		String sql = "select uUsername from user where uId = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1,1);
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			System.out.println(rs.getString(1));
		}

		rs.close();
		ps.close();
		c.close();
	}

	@Test
	public void getPath(){
		System.out.println(System.getProperty("user.dir"));
	}
}
