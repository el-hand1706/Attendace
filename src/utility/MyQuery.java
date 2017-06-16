package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyQuery {
	public static void main(String arg[]){
		String msg = "";
	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	      msg = "ドライバのロードに成功しました";
	    }catch (ClassNotFoundException e){
	      msg = "ドライバのロードに失敗しました";
	    }catch (Exception e){
	      msg = "ドライバのロードに失敗しました";
	    }
	    System.out.println(msg);
		   
	}
}
