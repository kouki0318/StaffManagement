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

public class StaffInsert extends JFrame {

	private JPanel contentPane;

	private JTextField textId; // ID
	private JTextField textName; // 氏名
	private JTextField textKana; // フリガナ
	private JTextField textBirth; // 誕生日
	private JComboBox cmbAddress; // 都道府県
	private JRadioButton radMale; // 性別（男性）
	private JRadioButton radFemale; // 性別（女性）
	private JButton btnInsert; // 追加ボタン
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
					StaffInsert frame = new StaffInsert();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * フレームを作成する
	 */
	public StaffInsert() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		textId = new JTextField();
		textId.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textId.setBounds(120, 32, 96, 19);
		contentPane.add(textId);
		textId.setColumns(10);

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

		JLabel label_index6 = new JLabel("市町村");
		label_index6.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index6.setBounds(255, 184, 53, 28);
		contentPane.add(label_index6);

		textCity = new JTextField();
		textCity.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textCity.setColumns(10);
		textCity.setBounds(342, 189, 96, 19);
		contentPane.add(textCity);

		JLabel label_index7 = new JLabel("部門ID");
		label_index7.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index7.setBounds(255, 27, 53, 28);
		contentPane.add(label_index7);

		textDepartmentId = new JTextField();
		textDepartmentId.setFont(new Font("SansSerif", Font.PLAIN, 12));
		textDepartmentId.setColumns(10);
		textDepartmentId.setBounds(342, 32, 96, 19);
		contentPane.add(textDepartmentId);

		JLabel label_index8 = new JLabel("パスワード");
		label_index8.setFont(new Font("SansSerif", Font.PLAIN, 15));
		label_index8.setBounds(255, 78, 75, 28);
		contentPane.add(label_index8);

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
				StaffLogin top = new StaffLogin();

				// 次画面を表示
				top.setVisible(true);

				// 自画面を閉じる
				close();

			}
		});
		btnDetailToSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnDetailToSearch.setBounds(270, 300, 96, 34);
		contentPane.add(btnDetailToSearch);

		btnInsert = new JButton("追加する");
		btnInsert.setFont(new Font("SansSerif", Font.PLAIN, 15));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 確認ダイアログの表示（はい：0、いいえ：1）
				int ans = JOptionPane.showConfirmDialog(null, "この情報を追加しますか？", "追加", JOptionPane.YES_NO_OPTION);

				// 「はい：0」以外場合、処理を中断する
				if (ans != 0) {
					System.out.println("いいえ");
					return;
				}

				// 削除処理
				if (insert()) {
					// 成功ダイアログの表示
					JOptionPane.showMessageDialog(null, "追加に成功しました", "成功", JOptionPane.PLAIN_MESSAGE);

					// ここにログイン画面に戻る処理を追加
					StaffLogin login = new StaffLogin();
					login.setVisible(true);
					close(); // 現在の画面を閉じる
				} else {
					// エラーダイアログの表示
					JOptionPane.showMessageDialog(null, "追加に失敗しました", "エラー", JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnInsert.setBounds(380, 300, 96, 34);
		contentPane.add(btnInsert);

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

	private boolean insert() {

		boolean retIns = false;

		// 社員IDの取得
		String id = textId.getText();

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

		DBClass db = new DBClass();
		db.dbOpen();

		int intRet = db.insertSyainData(id, name, kana, gender, birth, prefecture, city, departmentId, password);

		if (intRet > 0) {
			// 追加件数が0件より大きければ追加成功
			retIns = true;
		} else if (intRet == -1) {
			// 入力エラーの場合
			JOptionPane.showMessageDialog(null, "すべての項目を入力してください", "入力エラー", JOptionPane.ERROR_MESSAGE);
			retIns = false;
		} else {
			// その他のエラー
			retIns = false;
		}

		// バックアップ用
//		if (intRet > 0) {
//			// 削除件数が０件より大きければ更新成功
//			retIns = true;
//		} else {
//			// 削除件数が０件以下ならば更新失敗
//			retIns = false;
//		}

		return retIns;
	}

	// 自画面終了
	private void close() {
		this.dispose();
	}
}
