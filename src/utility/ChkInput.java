package utility;

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
}

