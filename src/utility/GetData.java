package utility;

import java.sql.ResultSet;
import java.util.ArrayList;

import tbl.Tbl_PrintTable;

public class GetData {
	
	public static ArrayList<Tbl_PrintTable> getTbl_PrintTable(int iUid){
		// 変数宣言
		String sSql;
		ResultSet rs;
		ArrayList<Tbl_PrintTable> array_printtable = new ArrayList<Tbl_PrintTable>();
		
		try{
			// menu画面に表示する名前、出退勤時間を取得
			sSql = "";
			sSql = sSql.concat("select id, "                                                                       );
			sSql = sSql.concat("    cast(month as signed) as month, "                                              );
			sSql = sSql.concat("    cast(day as signed) as day, "                                                  );
			sSql = sSql.concat("	case weekday(concat(year, '/', month, '/', day)) "                             );                                                                          
			sSql = sSql.concat("	when 0 then '月' "                                                             );
			sSql = sSql.concat("	when 1 then '火' "                                                             );
			sSql = sSql.concat("	when 2 then '水' "                                                             );
			sSql = sSql.concat("	when 3 then '木' "                                                             );
			sSql = sSql.concat("	when 4 then '金' "                                                             );
			sSql = sSql.concat("	when 5 then '土' "                                                             );
			sSql = sSql.concat("	when 6 then '日' "                                                             );
			sSql = sSql.concat("	end as weekdays, "                                                             );
			sSql = sSql.concat("	ifnull(date_format(cometime, '%H:%i:%s'),'') as cometimes, "                   );        
			sSql = sSql.concat("	ifnull(date_format(returntime, '%H:%i:%s'),'') as returntimes, "               );   
			sSql = sSql.concat("	ifnull(timediff(returntime,cometime) - interval 1 hour,'') as difftime "       ); 
			sSql = sSql.concat("from tbl_attendance "                                                              );
			sSql = sSql.concat("where uid = " + iUid + " "                                                         );
			sSql = sSql.concat("and year = date_format(current_timestamp(), '%Y') "                                );
			sSql = sSql.concat("and month = date_format(current_timestamp(), '%m') "                               );
			sSql = sSql.concat("and delflag != 1 "                                                                 );
			sSql = sSql.concat("order by concat(year, '/', month, '/', day) "                                      );
			sSql = sSql.concat("; "                                                                                );
			System.out.println(sSql);
			// SQL実行
			rs = MyQuery.selectSql(sSql);
			if(rs.next() != false){
				// 
				rs.beforeFirst();
	            while(rs.next()){
	            	Tbl_PrintTable  tbl_printtable = new Tbl_PrintTable();
	            	tbl_printtable.id = rs.getInt("id");
	            	tbl_printtable.months = rs.getInt("month");
	            	tbl_printtable.days = rs.getInt("day");
	            	tbl_printtable.weekdays = rs.getString("weekdays");
	            	tbl_printtable.cometimes = rs.getString("cometimes");
	            	tbl_printtable.returntimes = rs.getString("returntimes");
	            	tbl_printtable.difftime = rs.getString("difftime");
	            	array_printtable.add(tbl_printtable);
	            }  
			}else{
				// Selectに失敗したときの処理
				throw new Exception();
			}
		}catch(Exception e){
			return null;
		}
		return array_printtable;
	}
}
