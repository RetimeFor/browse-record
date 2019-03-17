package jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {
   
    private static final String driver = "com.mysql.jdbc.Driver"; //数据库驱动
    //连接数据库的URL地址
    private static final String url="jdbc:mysql://localhost:3306/db_comic_picture?useUnicode=true&characterEncoding=UTF-8"; 
    private static final String username="root";//数据库的用户名
    private static final String password="root";//数据库的密码
    
    private static Connection conn=null;
    
    //静态代码块负责加载驱动
    static 
    {
        try
        {
            Class.forName(driver);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //单例模式返回数据库连接对象
    public static Connection getConnection() throws Exception
    {
        if(conn==null)//连接为空
        {
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        return conn;//连接不为空，说明这个conn曾经被实例化过。被实例化也是通过DriverManager实例化了。
        //由于这是单例模式，意味着这个连接对象在整个服务中只有一份拷贝
    }
    
    //测试DBHelper类
    public static void main(String[] args) {
        try
        {
           Connection conn = DBHelper.getConnection();
           if(conn!=null)
           {
               System.out.println("数据库连接正常！");
           }
           else
           {
               System.out.println("数据库连接异常！");
           }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
}