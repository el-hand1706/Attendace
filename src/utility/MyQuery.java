package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import tbl.Tbl_Account;
import tbl.Tbl_Attendance;
import tbl.Tbl_PrintMenu;



public class MyQuery{
	
	private static Connection con = null;
	
	
	/**
	 * DB接続
	 * @return int		問題なければ0を、エラーがあれば1を返す
	 */
	public static int connectDb(){
//		@SuppressWarnings("unused")
//		Connection con = null;
		try {
		        // JDBCドライバのロード - JDBC4.0（JDK1.6）以降は不要
		        Class.forName("com.mysql.jdbc.Driver").newInstance();
		        // MySQLに接続
		        con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mysql", "root", "elhand7771");
		        System.out.println("MySQLに接続できました。");
	
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		    System.out.println("JDBCドライバのロードに失敗しました。");
		    return 1;
		} catch (SQLException e) {
		    System.out.println("MySQLに接続できませんでした。");
		    return 1;
		}
		return 0;
	}
	
	
	/**
	 * select文実行
	 * @return Tbl_Account		問題なければ取得したデータを、エラーがあればNULLを返す
	 */
	public static Tbl_Account selectTbl_Account(String sSql)throws Exception{
		// 変数宣言
		Tbl_Account tbl_account = new Tbl_Account();
		try {
			// SQL文実行
			Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sSql);
            
            // 結果を取得
            while(rs.next()){
            	tbl_account.uid = rs.getInt("uid");
            	tbl_account.name = rs.getString("name");
            	tbl_account.address = rs.getString("address");
            	tbl_account.password = rs.getString("password");
            	tbl_account.iGetFlag = 1;
            }
	
		} catch (Exception e){
		    System.out.println("JDBCドライバのロードに失敗しました。");
		    return null;
		}
		return tbl_account;
	}
	
	/**
	 * select文実行
	 * @return Tbl_Account		問題なければ取得したデータを、エラーがあればNULLを返す
	 */
	public static Tbl_Attendance selectTbl_Attendance(String sSql)throws Exception{
		// 変数宣言
		Tbl_Attendance tbl_attendance = new Tbl_Attendance();
		try {
			// SQL文実行
			Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sSql);
            
            // 結果を取得
            while(rs.next()){
            	tbl_attendance.id = rs.getInt("id");
            	tbl_attendance.uid = rs.getInt("uid");
            	tbl_attendance.cometime = rs.getString("cometime");
            	tbl_attendance.returntime = rs.getString("returntime");
            	tbl_attendance.iGetFlag = 1;
            }
	
		} catch (Exception e){
		    System.out.println("JDBCドライバのロードに失敗しました。");

		    return null;
		}
		return tbl_attendance;
	}
	
	/**
	 * select文実行
	 * @return Tbl_Account		問題なければ取得したデータを、エラーがあればNULLを返す
	 */
	public static Tbl_PrintMenu selectTbl_PrintMenu(String sSql)throws Exception{
		// 変数宣言
		Tbl_PrintMenu tbl_printmenu = new Tbl_PrintMenu();
		try {
			// SQL文実行
			Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sSql);
            
            // 結果を取得
            while(rs.next()){
            	tbl_printmenu.name = rs.getString("name");
            	tbl_printmenu.cometime = rs.getString("cometime");
            	tbl_printmenu.returntime = rs.getString("returntime");
            	tbl_printmenu.iGetFlag = 1;
            }
	
		} catch (Exception e){
		    System.out.println("JDBCドライバのロードに失敗しました。");
		    return null;
		}
		return tbl_printmenu;
	}
	
	/**
	 * insert文実行
	 * @return int		問題なければ0を、エラーがあれば1を返す
	 */
	public static int executeSql(String sSql)throws Exception{
		// 変数宣言
		PreparedStatement ps = null;
        try {
            // オートコミットはオフ
            con.setAutoCommit(false);

            //実行するSQL文とパラメータを指定する
            ps = con.prepareStatement(sSql);

            //INSERT文を実行する
            int i = ps.executeUpdate();

            //処理件数を表示する
            System.out.println("結果：" + i);

            //コミット
            con.commit();

        } catch (Exception ex) {
            //例外発生時の処理
            con.rollback();       //ロールバックする
            ex.printStackTrace();  //エラー内容をコンソールに出力する
            return 1;
        } 
		return 0;
	}
	
	
	/**
	 * update文実行
	 * @return int		問題なければ0を、エラーがあれば1を返す
	 */
	
	/**
	 * DB切断
	 * @return int		問題なければ0を、エラーがあれば1を返す
	 */
	public static int closeDb(){
		if(con != null){
			try{
				con.close();
			}catch(SQLException e){
				System.out.println("MySQLのクローズに失敗しました。");
				return 1;
			}
		}
		return 0;
	}
}
