package db;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  

public class DBManager {
	// 加载的驱动    
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";    
    // 数据库URL    
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db_test?useUnicode=true&characterEncoding=utf8";    
    // 数据库连接用户名    
    private static final String DATABASE_USERNAME = "root";    
    // 数据库连接密码    
    private static final String DATEBASE_PASSWORD = "1234567890";    
    public static Connection getConnection() throws SQLException {    
        try {    
            Class.forName(DRIVER_CLASS);    
        } catch (ClassNotFoundException e) {    
            e.printStackTrace();    
        }    
        Connection con = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME, DATEBASE_PASSWORD);    
        return con;    
    }    
}
