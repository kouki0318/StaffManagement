package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBClass {

	// DB接続用コネクション
	Connection objCon;

	// 接続文字列
	String connUrl = "";

	public DBClass() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String serverName = "ARLFELIO\\SQLEXPRESS"; // サーバ名
			String dbName = "kensyu"; // データベース名

			String userName = "sa"; // ユーザ名
			String password = "Step2154822"; // パスワード

			connUrl = "jdbc:sqlserver://" + serverName + ";";
			connUrl += "database=" + dbName + ";";
			connUrl += "integratedSecurity=false;encrypt=false;";
			connUrl += "user=" + userName + ";";
			connUrl += "password=" + password + ";";

		} catch (Exception objEx) {
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());
		}
	}

	/**
	 * DB接続処理
	 *
	 * @return 接続結果（true:接続成功、false：接続失敗）
	 */
	public boolean dbOpen() {
		try {

			// 接続開始
			objCon = DriverManager.getConnection(connUrl);

			System.out.println("DB接続成功");

			// 接続成功
			return true;
		} catch (Exception objEx) {
			// エラー表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());

			// 接続失敗
			return false;
		}
	}

	/**
	 * DB切断処理
	 *
	 * @return 切断結果（true:切断成功、false：切断失敗）
	 */
	public boolean dbClose() {
		try {

			// 切断開始
			objCon.close();

			System.out.println("DB切断成功");

			// 切断成功
			return true;
		} catch (Exception objEx) {
			// エラー表示
			System.err.println(objEx.getClass().getName() + ":" + objEx.getMessage());

			// 切断失敗
			return false;
		}
	}

	/**
	 * 社員情報検索処理（あいまい検索）
	 *
	 * @param name 検索キーワード - 社員の氏名またはフリガナの一部（部分一致検索）
	 * @return ArrayList<String[]> - 検索結果の社員情報を含むリスト 各要素の内容:
	 *         [0]:社員ID、[1]:氏名、[2]:フリガナ、[3]:性別、[4]:生年月日、
	 *         [5]:都道府県、[6]:市町村、[7]:部門ID、[8]:パスワード
	 */
	public ArrayList<String[]> getSyainData(String name) {

		ArrayList<String[]> data = new ArrayList<String[]>();

		// Statementを生成
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {

			String sql = "SELECT 社員ID, 氏名, フリガナ, 性別, CONVERT(VARCHAR,生年月日,111) AS 生年月日, 都道府県 , 市町村 , 部門ID , パスワード "
					+ "FROM   社員マスタ " // 末尾にスペース
					+ "WHERE 氏名 LIKE ? OR フリガナ LIKE ?";

			// バックアップ用
//			String sql = "";
//			sql += " SELECT 社員ID, 氏名, フリガナ, 性別, CONVERT(VARCHAR,生年月日,111) AS 生年月日, 都道府県 , 市町村 , 部門ID , パスワード ";
//			sql += " FROM   社員マスタ ";
//			sql += " WHERE  氏名 LIKE '%" + name + "%'";
//			sql += " OR     フリガナ LIKE '%" + name + "%'";

			pstmt = objCon.prepareStatement(sql);

			// LIKE検索のパラメータ設定
			String searchPattern = "%" + name + "%";
			pstmt.setString(1, searchPattern);
			pstmt.setString(2, searchPattern);

			// 実行SQL確認
			System.out.println("実行SQL: " + sql);
			System.out.println("検索パターン: " + searchPattern);

			// 実行（引数なし）
			rset = pstmt.executeQuery();

			while (rset.next()) {

				// 取得するフィールド分の配列生成
				String[] strData = new String[9];

				strData[0] = rset.getString("社員ID");
				strData[1] = rset.getString("氏名");
				strData[2] = rset.getString("フリガナ");
				strData[3] = rset.getString("性別");
				strData[4] = rset.getString("生年月日");
				strData[5] = rset.getString("都道府県");
				strData[6] = rset.getString("市町村");
				strData[7] = rset.getString("部門ID");
				strData[8] = rset.getString("パスワード");

				// リストに追加
				data.add(strData);

			}

			rset.close(); // ResultSetのクローズ
			pstmt.close(); // Statementのクローズ

		} catch (SQLException e) {
			// エラー表示
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}

		return data;
	}

	/**
	 * 社員情報更新処理
	 *
	 * @param id           社員ID（3文字以内）- 更新対象の社員ID
	 * @param name         氏名 - 社員の名前
	 * @param kana         フリガナ - 氏名のフリガナ表記
	 * @param gender       性別 - 'M'=男性、'F'=女性
	 * @param birth        生年月日 - YYYY/MM/DD形式
	 * @param prefecture   都道府県 - 社員の居住都道府県
	 * @param city         市町村 - 社員の居住市町村
	 * @param departmentId 部門ID - 社員の所属部門（4桁）
	 * @param password     パスワード - 社員のログインパスワード
	 * @return 更新件数 - 処理成功:1、失敗:0
	 */
	public int updSyainData(String id, String name, String kana, String gender, String birth, String prefecture,
			String city, String departmentId, String password) {

		// 実行結果影響件数用変数
		int retCount = 0;

		// Statementを生成
		PreparedStatement pstmt = null;

		try {

			String sql = "UPDATE 社員マスタ "
					+ "SET 氏名 = ?, フリガナ = ?, 性別 = ?, 生年月日 = ?, 都道府県 = ?, 市町村 = ?, 部門ID = ?, パスワード = ? "
					+ "WHERE 社員ID = ?";

			// バックアップ用
//			String sql = "";
//			sql += " UPDATE 社員マスタ  ";
//			sql += " SET 氏名 = '" + name + "',";
//			sql += "     フリガナ = '" + kana + "',";
//			sql += "     性別 = '" + gender + "',";
//			sql += "     生年月日 = '" + birth + "',";
//			sql += "     都道府県 = '" + prefecture + "',";
//			sql += "     市町村 = '" + city + "',";
//			sql += "     部門ID = '" + departmentId + "',";
//			sql += "     パスワード = '" + password + "'";
//
//			sql += " WHERE  社員ID = '" + id + "'";

			pstmt = objCon.prepareStatement(sql);

			// パラメータの設定
			pstmt.setString(1, name); // 1番目の?にnameをセット
			pstmt.setString(2, kana); // 2番目の?にkanaをセット
			pstmt.setString(3, gender); // 3番目の?にgenderをセット
			pstmt.setString(4, birth); // 4番目の?にbirthをセット
			pstmt.setString(5, prefecture); // 5番目の?にprefectureをセット
			pstmt.setString(6, city); // 6番目の?にcityをセット
			pstmt.setString(7, departmentId); // 7番目の?にdepartmentIdをセット
			pstmt.setString(8, password); // 8番目の?にpasswordをセット
			pstmt.setString(9, id); // 9番目の?にidをセット (WHEREの条件用)

			// SQLの内容確認（デバッグ用）
			System.out.println("実行SQL: " + sql);

			// 問い合わせの実行
			retCount = pstmt.executeUpdate();

			pstmt.close(); // Statementのクローズ

		} catch (SQLException e) {
			// エラー表示
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}

		return retCount;
	}

	/**
	 * ログイン情報検索処理
	 *
	 * @param id       社員ID（3文字以内）- ログイン時に入力されたID
	 * @param password パスワード - ログイン時に入力されたパスワード
	 * @return ArrayList<String[]> - ログイン成功時に社員情報を含むリスト（失敗時は空のリスト）
	 *         [0]:社員ID、[1]:氏名、[2]:フリガナ、[3]:性別、
	 *         [4]:生年月日、[5]:都道府県、[6]:市町村、[7]:部門ID、[8]:パスワード
	 */
	public ArrayList<String[]> getLoginData(String id, String password) {

		ArrayList<String[]> data = new ArrayList<String[]>();

		// Statementを生成
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {

			// バックアップ用
//			String sql = "SELECT * FROM 社員マスタ WHERE 社員ID = '" + id + "' AND パスワード = '" + password + "'";
//			// Statementを使って実行
//			Statement pstmt = objCon.createStatement();
//			ResultSet rset = pstmt.executeQuery(sql);

			String sql = "SELECT 社員ID, 氏名, フリガナ, 性別, CONVERT(VARCHAR,生年月日,111) AS 生年月日, 都道府県, 市町村, 部門ID, パスワード "
					+ "FROM 社員マスタ " + "WHERE 社員ID = ? AND パスワード = ?";

			// バックアップ用
//			String sql = "";
//			sql += " SELECT 社員ID, 氏名, フリガナ, 性別 , CONVERT(VARCHAR,生年月日,111) AS 生年月日, 都道府県 , 市町村 , 部門ID , パスワード";
//			sql += " FROM   社員マスタ";
//			sql += " WHERE  社員ID = '" + id + "'";
//			sql += " AND    パスワード = '" + password + "'";

			pstmt = objCon.prepareStatement(sql);
//
			// パラメータの設定
			pstmt.setString(1, id); // 1番目の?にidをセット
			pstmt.setString(2, password); // 2番目の?にpasswordをセット
//
			// SQLの内容確認（デバッグ用）
			System.out.println("実行SQL: " + sql);
			System.out.println("パラメータ1: " + id);
			System.out.println("パラメータ2: " + password);
//
			// 実行（引数なし）
			rset = pstmt.executeQuery();

			while (rset.next()) {

				// 取得するフィールド分の配列生成
				String[] strData = new String[9];

				strData[0] = rset.getString("社員ID");
				strData[1] = rset.getString("氏名");
				strData[2] = rset.getString("フリガナ");
				strData[3] = rset.getString("性別");
				strData[4] = rset.getString("生年月日");
				strData[5] = rset.getString("都道府県");
				strData[6] = rset.getString("市町村");
				strData[7] = rset.getString("部門ID");
				strData[8] = rset.getString("パスワード");

				// リストに追加
				data.add(strData);

			}

			rset.close(); // ResultSetのクローズ
			pstmt.close(); // Statementのクローズ

		} catch (SQLException e) {
			// エラー表示
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}

		return data;
	}

	/**
	 * 社員情報削除処理
	 *
	 * @param id 社員ID
	 * @return 削除件数
	 */

	public int deleteSyainData(String id) {

		// 実行結果影響件数用変数
		int retCount = 0;

		// Statementを生成
		PreparedStatement pstmt = null;

		try {

			String sql = "DELETE FROM 社員マスタ WHERE 社員ID = ?";
//			String sql = "";
//			sql += " DELETE FROM 社員マスタ ";
//			sql += " WHERE  社員ID = '" + id + "'";

			pstmt = objCon.prepareStatement(sql);

			pstmt.setString(1, id);

			// SQLの内容確認（デバッグ用）
			System.out.println("実行SQL: " + sql);
			System.out.println("ID: " + id + "は削除されました");

			// 問い合わせの実行
			retCount = pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			// エラー表示
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}
		return retCount;
	}

	/**
	 * 社員情報追加処理
	 *
	 * @param id           社員ID (3文字以内)
	 * @param name         氏名
	 * @param kana         フリガナ
	 * @param gender       性別 ('M'=男性、'F'=女性)
	 * @param birth        生年月日 (YYYY/MM/DDの形式)
	 * @param prefecture   都道府県
	 * @param city         市町村
	 * @param departmentId 部門ID (4桁)
	 * @param password     パスワード
	 * @return 追加件数 (処理成功:1、失敗:0)
	 */
	public int insertSyainData(String id, String name, String kana, String gender, String birth, String prefecture,
			String city, String departmentId, String password) {

		// 入力チェック - すべての項目が入力されているか確認
		if (isEmpty(id)) {
			System.err.println("社員IDが入力されていません");
			return -1;
		}
		if (isEmpty(departmentId)) {
			System.err.println("部門IDが入力されていません");
			return -1;
		}
		if (isEmpty(name)) {
			System.err.println("氏名が入力されていません");
			return -1;
		}
		if (isEmpty(password)) {
			System.err.println("パスワードが入力されていません");
			return -1;
		}
		if (isEmpty(kana)) {
			System.err.println("フリガナが入力されていません");
			return -1;
		}
		if (isEmpty(birth)) {
			System.err.println("生年月日が入力されていません");
			return -1;
		}
		if (isEmpty(prefecture)) {
			System.err.println("都道府県が選択されていません");
			return -1;
		}
		if (isEmpty(city)) {
			System.err.println("市町村が入力されていません");
			return -1;
		}
		if (isEmpty(gender)) {
			System.err.println("性別が選択されていません");
			return -1;
		}

		// 実行結果影響件数用変数
		int retCount = 0;

		// Statementを生成
		PreparedStatement pstmt = null;

		try {

			String sql = "INSERT INTO 社員マスタ (社員ID, 氏名, フリガナ, 性別, 生年月日, 都道府県, 市町村, 部門ID, パスワード, 権限, 削除フラグ) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,0,0)";

			// バックアップ用
//			String sql = "";
//			sql += " INSERT INTO 社員マスタ (社員ID, 氏名, フリガナ, 性別, 生年月日, 都道府県, 市町村, 部門ID, パスワード, 権限, 削除フラグ) ";
//			sql += " VALUES ('" + id + "', ";
//			sql += "'" + name + "',";
//			sql += "'" + kana + "',";
//			sql += "'" + gender + "',";
//			sql += "'" + birth + "',";
//			sql += "'" + prefecture + "',";
//			sql += "'" + city + "',";
//			sql += "'" + departmentId + "',";
//			sql += "'" + password + "',";
//			sql += "'0',"; // 削除フラグを0（未削除）として追加
//			sql += "'0')"; // 削除フラグを0（未削除）として追加

			pstmt = objCon.prepareStatement(sql);

			// パラメータの設定
			pstmt.setString(1, id); // 1番目の?にidをセット
			pstmt.setString(2, name); // 2番目の?にnameをセット
			pstmt.setString(3, kana); // 3番目の?にkanaをセット
			pstmt.setString(4, gender); // 4番目の?にgenderをセット
			pstmt.setString(5, birth); // 5番目の?にbirthをセット
			pstmt.setString(6, prefecture); // 6番目の?にprefectureをセット
			pstmt.setString(7, city); // 7番目の?にcityをセット
			pstmt.setString(8, departmentId); // 8番目の?にdepartmentIdをセット
			pstmt.setString(9, password); // 9番目の?にpasswordをセット

			// SQLの内容確認（デバッグ用）
			System.out.println("実行SQL: " + sql);

			// 問い合わせの実行
			retCount = pstmt.executeUpdate();

			pstmt.close(); // Statementのクローズ

		} catch (SQLException e) {
			// エラー表示
			System.err.println(e.getClass().getName() + ":" + e.getMessage());
		}

		return retCount;
	}

	/**
	 * 文字列が空かどうかチェックするヘルパーメソッド
	 * 
	 * @param str チェックする文字列
	 * @return 空または null の場合 true、それ以外は false
	 */
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
