package frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import common.DBClass;

public class StaffDetail extends JFrame {

	private JPanel contentPane;

	// 画面項目メンバ
	private JLabel lblEmployeeId; // 社員ID
	private JTextField textName; // 氏名
	private JTextField textKana; // フリガナ
	private JTextField textBirth; // 誕生日
	private JComboBox cmbAddress; // 都道府県
	private JRadioButton radMale; // 性別（男性）
	private JRadioButton radFemale; // 性別（女性）
	private JTextField textCity;
	private JTextField textDepartmentId;
	private JTextField textPassword;

	/**
	 * アプリケーションを起動する
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffDetail frame = new StaffDetail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// クラスフィールドとして追加（インスタンス変数）
	// ログインユーザー情報を保持するフィールド
	private String loginUserName = "";
	private String loginUserId = "";
	private JLabel lblLoginUser; // ログインユーザー表示用ラベル

	/**
	 * ログインユーザー情報をセットするメソッド
	 * 
	 * @param userData ログインユーザーの情報配列 [0]:社員ID, [1]:氏名
	 */
	public void setLoginUserInfo(String[] userData) {
		if (userData != null && userData.length > 1) {
			this.loginUserId = userData[0]; // 社員ID
			this.loginUserName = userData[1]; // 氏名

			// ウィンドウタイトルにユーザー名を表示
			this.setTitle("社員情報修正 - " + this.loginUserName + "様としてログイン中");

			// ログインユーザー表示ラベルが初期化済みならテキスト設定
			if (lblLoginUser != null) {
				lblLoginUser.setText(this.loginUserName + "様としてログイン中");
			}
		}
	}

	/**
	 * フレームを作成する
	 */
	public StaffDetail() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/**
		 * StaffDetail クラスのコンストラクタ内で、他のコンポーネントの追加後に以下を追加 ログインユーザー表示用ラベルを追加
		 * 使用しないのでコメントアウト 将来的に仕様変更があった場合のため残しています
		 */

		// lblLoginUser = new JLabel("");
		// lblLoginUser.setFont(new Font("SansSerif", Font.PLAIN, 14));
		// lblLoginUser.setBounds(28, 345, 400, 20);
		// contentPane.add(lblLoginUser);

		JLabel label_index0 = new JLabel("社員ID");
		label_index0.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index0.setBounds(28, 27, 53, 28);
		contentPane.add(label_index0);

		JLabel label_index1 = new JLabel("氏名");
		label_index1.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index1.setBounds(28, 78, 60, 28);
		contentPane.add(label_index1);

		JLabel label_index2 = new JLabel("フリガナ");
		label_index2.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index2.setBounds(28, 130, 60, 28);
		contentPane.add(label_index2);

		JLabel label_index3 = new JLabel("性別");
		label_index3.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index3.setBounds(28, 240, 60, 28);
		contentPane.add(label_index3);

		JLabel label_index4 = new JLabel("生年月日");
		label_index4.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index4.setBounds(255, 130, 60, 28);
		contentPane.add(label_index4);

		JLabel label_index5 = new JLabel("都道府県");
		label_index5.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index5.setBounds(28, 184, 60, 28);
		contentPane.add(label_index5);

		// 市町村、パスワード、部門IDのラベルを追加
		JLabel label_index6 = new JLabel("市町村");
		label_index6.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index6.setBounds(255, 184, 53, 28);
		contentPane.add(label_index6);

		JLabel label_index7 = new JLabel("部門ID");
		label_index7.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index7.setBounds(255, 27, 53, 28);
		contentPane.add(label_index7);

		JLabel label_index8 = new JLabel("パスワード");
		label_index8.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index8.setBounds(255, 78, 75, 28);
		contentPane.add(label_index8);

		lblEmployeeId = new JLabel("社員ID");
		lblEmployeeId.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblEmployeeId.setBounds(120, 32, 73, 19);
		contentPane.add(lblEmployeeId);

		textName = new JTextField();
		textName.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textName.setBounds(120, 82, 96, 19);
		contentPane.add(textName);
		textName.setColumns(10);

		textKana = new JTextField();
		textKana.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textKana.setColumns(10);
		textKana.setBounds(120, 135, 96, 19);
		contentPane.add(textKana);

		radMale = new JRadioButton("男性");
		radMale.setFont(new Font("SansSerif", Font.PLAIN, 15));
		radMale.setBounds(120, 244, 60, 21);
		contentPane.add(radMale);

		radFemale = new JRadioButton("女性");
		radFemale.setFont(new Font("SansSerif", Font.PLAIN, 15));
		radFemale.setBounds(208, 244, 60, 21);
		contentPane.add(radFemale);

		textBirth = new JTextField();
		textBirth.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textBirth.setColumns(10);
		textBirth.setBounds(342, 134, 96, 19);
		contentPane.add(textBirth);

		textCity = new JTextField();
		textCity.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textCity.setColumns(10);
		textCity.setBounds(342, 189, 96, 19);
		contentPane.add(textCity);

		textDepartmentId = new JTextField();
		textDepartmentId.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textDepartmentId.setColumns(10);
		textDepartmentId.setBounds(342, 32, 96, 19);
		contentPane.add(textDepartmentId);

		textPassword = new JTextField();
		textPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textPassword.setColumns(10);
		textPassword.setBounds(342, 83, 96, 19);
		contentPane.add(textPassword);

		/*******************************************************************/
		// ラジオボタンのグループ化
		ButtonGroup group = new ButtonGroup();
		group.add(radMale);
		group.add(radFemale);
		/*******************************************************************/

		JButton btnDetailToSearch = new JButton("戻る");
		btnDetailToSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 次画面のインスタンス生成
				StaffSearch search = new StaffSearch();
				// ログインユーザー情報を引き継ぐ
				if (!loginUserName.isEmpty()) {
					String[] loginUserData = new String[2];
					loginUserData[0] = loginUserId;
					loginUserData[1] = loginUserName;
					search.setLoginUserInfo(loginUserData);
				}
				// 次画面を表示
				search.setVisible(true);
				// 自画面終了
				close();
			}
		});
		btnDetailToSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnDetailToSearch.setBounds(160, 300, 96, 34);
		contentPane.add(btnDetailToSearch);

		JButton btnDelete = new JButton("削除");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 確認ダイアログの表示（はい：0、いいえ：1）
				int ans = JOptionPane.showConfirmDialog(null, "この情報を削除しますか？", "削除", JOptionPane.YES_NO_OPTION);

				// 「はい：0」以外場合、処理を中断する
				if (ans != 0) {
					System.out.println("いいえ");
					return;
				}

				// 削除処理
				if (delete()) {
					// 成功ダイアログの表示
					JOptionPane.showMessageDialog(null, "削除に成功しました", "成功", JOptionPane.PLAIN_MESSAGE);

					// 次画面のインスタンス生成
					StaffSearch search = new StaffSearch();

					// ログインユーザー情報を引き継ぐ
					if (!loginUserName.isEmpty()) {
						String[] loginUserData = new String[2];
						loginUserData[0] = loginUserId;
						loginUserData[1] = loginUserName;
						search.setLoginUserInfo(loginUserData);
					}

					// ログイン画面のインスタンス生成
					StaffLogin login = new StaffLogin();

					// ログイン画面を表示
					login.setVisible(true);

					// 自画面を閉じる
					close();
				} else {
					// エラーダイアログの表示
					JOptionPane.showMessageDialog(null, "削除に失敗しました", "エラー", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnDelete.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnDelete.setBounds(270, 300, 96, 34);
		contentPane.add(btnDelete);

		JButton btnUpdate = new JButton("更新");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 確認ダイアログの表示（はい：0、いいえ：1）
				int ans = JOptionPane.showConfirmDialog(null, "この情報を更新しますか？", "更新", JOptionPane.YES_NO_OPTION);

				// 「はい：0」以外場合、処理を中断する
				if (ans != 0) {
					System.out.println("いいえ");
					return;
				}

				// 更新処理
				if (update()) {
					// 成功ダイアログの表示
					JOptionPane.showMessageDialog(null, "更新に成功しました", "成功", JOptionPane.PLAIN_MESSAGE);

					// 次画面のインスタンス生成
					StaffSearch search = new StaffSearch();

					// ログインユーザー情報を引き継ぐ
					if (!loginUserName.isEmpty()) {
						String[] loginUserData = new String[2];
						loginUserData[0] = loginUserId;
						loginUserData[1] = loginUserName;
						search.setLoginUserInfo(loginUserData);
					}

					// 次画面を表示
					search.setVisible(true);

					// 自画面を閉じる
					close();
				} else {
					// エラーダイアログの表示
					JOptionPane.showMessageDialog(null, "更新に失敗しました", "エラー", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnUpdate.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnUpdate.setBounds(380, 300, 96, 34);
		contentPane.add(btnUpdate);

		/*******************************************************************/
		// 都道府県の処理
		String[] kendata = { "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都",
				"神奈川県", "新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", "三重県", "滋賀県", ",京都府", "大阪府",
				"兵庫県", "奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県",
				"長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県 " };

		cmbAddress = new JComboBox(kendata);
		cmbAddress.setFont(new Font("SansSerif", Font.PLAIN, 12));
		/*******************************************************************/

		cmbAddress.setBounds(120, 189, 91, 19);
		contentPane.add(cmbAddress);
	}

	/**
	 * 表示用データ設定処理
	 *
	 * @param frm 表示用データ
	 *
	 */
	public void setStrData(String[] data) {

		// 社員IDの設定
		lblEmployeeId.setText(data[0]);

		// 氏名の設定
		textName.setText(data[1]);

		// フリガナの設定
		textKana.setText(data[2]);

		// 性別の設定（M：男性、F：女性）
		if ("M".equals(data[3])) {
			radMale.setSelected(true);
		} else {
			radFemale.setSelected(true);
		}

		// 生年月日の設定
		textBirth.setText(data[4]);

		// 住所の設定
		cmbAddress.setSelectedItem(data[5]);

		// 市町村の設定
		textCity.setText(data[6]);

		// 部門IDの設定
		textDepartmentId.setText(data[7]);

		// パスワードの設定
		textPassword.setText(data[8]);

	}

	/**
	 * 表示用データ設定処理
	 *
	 * @param frm 表示用データ
	 *
	 * @return 更新結果（true：更新成功、false：更新失敗）
	 */
	public boolean update() {

		boolean retUpd = false;

		// 社員IDの取得
		String id = lblEmployeeId.getText();

		// 氏名の取得
		String name = textName.getText();

		// フリガナの取得
		String kana = textKana.getText();

		// 性別の設定（M：男性、F：女性）
		String gender = "";

		// 男性がチェックされている場合
		if (radMale.isSelected()) {
			gender = "M";
		} else {
			gender = "F";
		}

		// 生年月日の取得
		String birth = textBirth.getText();

		// 都道府県の設定
		String prefecture = (String) cmbAddress.getSelectedItem();

		String city = textCity.getText();
		String departmentId = textDepartmentId.getText();
		String password = textPassword.getText();

		// DBクラスのインスタンス生成
		DBClass db = new DBClass();

		// DB接続
		db.dbOpen();

		// 社員情報更新
		int intRet = db.updSyainData(id, name, kana, gender, birth, prefecture, city, departmentId, password);

		if (intRet > 0) {
			// 更新件数が０件より大きければ更新成功
			retUpd = true;

			// 更新対象が現在ログインしているユーザーの場合、ログイン情報も更新
			if (id.equals(loginUserId)) {
				loginUserName = name; // 名前を更新

				// ウィンドウタイトルを更新
				this.setTitle("社員情報修正 - " + loginUserName + "様としてログイン中");
			}
		} else {
			// 更新件数が０件以下ならば更新失敗
			retUpd = false;
		}
		// DB切断を追加
		db.dbClose();
		return retUpd;

	}

	public boolean delete() {

		boolean retDel = false;

		String id = lblEmployeeId.getText();

		DBClass db = new DBClass();
		db.dbOpen();

		int intRet = db.deleteSyainData(id);

		if (intRet > 0) {
			// 削除件数が０件より大きければ更新成功
			retDel = true;
		} else {
			// 削除件数が０件以下ならば更新失敗
			retDel = false;
		}
		// DB切断を追加
		db.dbClose();
		return retDel;
	}

	// 自画面終了
	private void close() {
		this.dispose();
	}

}
