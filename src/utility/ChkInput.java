package utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChkInput {
	// アドレスチェック
//	private String mailFormat = 
//			("^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+" + 
//			"\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+" + 
//			"(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$");

	String mailFormat = "^[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+(\\.[a-zA-Z0-9!#$%&'_`/=~\\*\\+\\-\\?\\^\\{\\|\\}]+)*+(.*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*(\\.[a-zA-Z0-9\\-]+)+$";
	
	// 半角英数字
	private String alphanumericFormat =
			("^[a-zA-Z0-9]+$");
	
	/**
	 * 文字列がフォーマット通りになっているかチェックする
	 * @param sStr			チェックする文字列
	 * @param chkFormat		フォーマット文字列
	 * @return boolean		問題なければtrueを返す
	 */
	public int chkFormat(String sStr, int iFormat, StringBuilder sErrMsg){
		// チェックするフォーマット
		String sFormat = new String();
		if(iFormat == 0){
			sFormat = this.mailFormat;
		}else if(iFormat == 1){
			sFormat = this.alphanumericFormat;
		}else{
			;
		}
		
		// エラーがあれば１を返す
		if( sStr.matches(sFormat) ) {
            return 0;
        }
        else {
        	sErrMsg.append("入力フォーマットエラー        ");
            return 1;
        }
	}
	
	/**
	 * 文字列の長さをチェック
	 * @param sStr  		チェックする文字列
	 * @param iMinLength 	文字列の最小長さ
	 * @param iMaxLength 	文字列の最大長さ
	 * @return boolean		問題なければtrueを返す
	 */
	public int chkLength(String sStr, int iMinLength, int iMaxLength, StringBuilder sErrMsg){
		// エラーがあれば１を返す
		if(sStr.length() <= iMaxLength && sStr.length() >= iMinLength) {
            return 0;
        }
        else {
        	sErrMsg.append("文字数制限エラー        ");
            return 1;
        }
	}
	/**
	 * 指定された文字列の最後から指定された文字列長の文字列を返します。
	 *
	 * @param value 基となる文字列
	 * @param length 返す文字列の文字列長
	 * @return 基となる文字列の最後から指定された文字列長の文字列
	 */
	public static String rightstring(String value, int length) {
	  try {
	    if ( value.length() >= length )
	      return value.substring(value.length() - length);
	    else
	      return value.substring(1);
	  } catch ( Exception e ) {
	    return value;
	  }
	}

	/**
	 * 指定された文字列が時刻型かどうかを返します。
	 *
	 * @param value 文字列
	 * @return true:正常, false:異常
	 */
	public static boolean isTimeType(String value) {
	  if ( value == null || "".equals(value) )
	    return false;
	  Pattern p = Pattern.compile("^([0-1][0-9]|[2][0-3]):[0-5][0-9]$");
	  Matcher m = p.matcher(rightstring("0"+value, 5));
	  if ( !m.find() )
	    return false;
	  return true;
	}
}

