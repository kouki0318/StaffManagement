package frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import common.DBClass;

public class StaffSearch extends JFrame {
    
    private JPanel contentPane;
    private JTextField textName;
    private JTable table;
    
    // 一覧用テーブルモデルメンバ
    private DefaultTableModel model;
    
    // 取得結果格納用メンバ
    private ArrayList<String[]> ary;
    
    // ログインユーザー情報を保持するフィールド
    private String loginUserName = "";
    private String loginUserId = "";
    
    /**
     * アプリケーションを起動する
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    
                    StaffSearch frame = new StaffSearch();
                    frame.setVisible(true);
                    
                } catch (Exception e) {
                    
                    e.printStackTrace();
                    
                }
            }
        });
    }
    
    
    /**
     * ログインユーザー情報をセットするメソッド
     * 
     * @param userData
     *                 ログインユーザーの情報配列 [0]:社員ID、[1]:氏名、[2]:フリガナ、[3]:性別、
     *                 [4]:生年月日、[5]:都道府県、[6]:市町村、[7]:部門ID、[8]:パスワード
     */
    public void setLoginUserInfo(String[] userData) {
        if (userData != null && userData.length > 1) {
            
            this.loginUserId = userData[0]; // 社員ID
            this.loginUserName = userData[1]; // 氏名
            
            // ウィンドウタイトルにユーザー名を表示（オプション）
            this.setTitle("社員検索 - " + this.loginUserName + "様としてログイン中");
            
        }
    }
    
    
    /**
     * フレームを作成する
     */
    public StaffSearch() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JButton btnSearch = new JButton("検索する");
        btnSearch.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                // テキストボックスの値取得
                String name = textName.getText();
                
                if (name.equals("")) {
                    
                    System.out.println("氏名が入力されていないです。");
                    
                    // エラーダイアログの表示
                    JOptionPane.showMessageDialog(null, "氏名を入力してください。", "確認", JOptionPane.ERROR_MESSAGE);
                    
                    return;
                    
                }
                
                // DBクラスのインスタンス生成
                DBClass db = new DBClass();
                
                // DB接続
                db.dbOpen();
                
                // 社員情報取得
                ArrayList<String[]> arr = db.getSyainData(name);
                
                // 取得結果保持用リストにコピー
                ary = arr;
                
                // 行削除
                model.setRowCount(0);
                
                // 取得データ数分繰り返し
                for (int i = 0; i < arr.size(); i++) {
                    
                    // 行データ設定用
                    String[] data = new String[9];
                    
                    data[0] = arr.get(i)[0]; // 社員ID
                    data[1] = arr.get(i)[1]; // 氏名
                    data[2] = arr.get(i)[2]; // フリガナ
                    data[3] = arr.get(i)[3]; // 性別
                    data[4] = arr.get(i)[4]; // 生年月日
                    data[5] = arr.get(i)[5]; // 都道府県
                    data[6] = arr.get(i)[6]; // 市町村
                    data[7] = arr.get(i)[7]; // 部門ID
                    data[8] = arr.get(i)[8]; // パスワード
                    
                    // データモデルに追加
                    model.addRow(data);
                    
                }
                
                // DB切断
                db.dbClose();
                
            }
        });
        btnSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnSearch.setBounds(712, 28, 140, 30);
        contentPane.add(btnSearch);
        
        textName = new JTextField();
        textName.setFont(new Font("SansSerif", Font.PLAIN, 15));
        textName.setBounds(104, 28, 500, 30);
        contentPane.add(textName);
        textName.setColumns(10);
        
        JLabel label_index1 = new JLabel("氏名");
        label_index1.setFont(new Font("SansSerif", Font.PLAIN, 15));
        label_index1.setBounds(48, 32, 44, 21);
        contentPane.add(label_index1);
        
        JButton btnSearchToDetail = new JButton("修正");
        btnSearchToDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // 選択行の取得
                int rowNum = table.getSelectedRow();
                
                // 行未選択の場合は処理しない
                if (rowNum < 0) {
                    
                    System.out.println("行が選択されていないです。");
                    
                    // エラーダイアログの表示
                    JOptionPane.showMessageDialog(null, "行を選択してください", "確認", JOptionPane.ERROR_MESSAGE);
                    
                    return;
                    
                }
                
                // 次画面のインスタンス生成
                StaffDetail dtl = new StaffDetail();
                
                // 表示用データのセット
                dtl.setStrData(ary.get(rowNum));
                
                // ログインユーザー情報の引き継ぎ
                String[] loginUserData = new String[2];
                loginUserData[0] = loginUserId;
                loginUserData[1] = loginUserName;
                dtl.setLoginUserInfo(loginUserData);
                
                // 次画面を表示
                dtl.setVisible(true);
                
                // 自画面を閉じる
                close();
                
            }
        });
        btnSearchToDetail.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnSearchToDetail.setBounds(732, 388, 120, 30);
        contentPane.add(btnSearchToDetail);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(32, 106, 820, 254);
        contentPane.add(scrollPane);
        
        /********************************************************************************/
        // モデルを生成
        model = new DefaultTableModel();
        
        // カラム名設定
        model.setColumnIdentifiers(new String[] {
                "社員ID", "社員名", "フリガナ", "性別", "生年月日", "都道府県", "市町村", "部門ID", "パスワード"
        });
        
        // モデルをテーブルに適用
        table = new JTable(model);
        /********************************************************************************/
        
        scrollPane.setViewportView(table);
        
        JButton btnLogout = new JButton("ログアウト");
        btnLogout.setFont(new Font("SansSerif", Font.PLAIN, 15));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // ユーザー名を含めた確認メッセージ
                String confirmMessage = "ログアウトしますか？";
                
                // ログインユーザー名が設定されている場合は、それをメッセージに含める
                if (!loginUserName.isEmpty()) {
                    
                    confirmMessage = loginUserName + "様、ログアウトしますか？";
                    
                }
                
                // 確認ダイアログの表示
                int ans = JOptionPane.showConfirmDialog(null, confirmMessage, "確認", JOptionPane.YES_NO_OPTION);
                
                // 確認ダイアログの表示
                // int ans = JOptionPane.showConfirmDialog(null, "ログアウトしますか？", "確認",
                // JOptionPane.YES_NO_OPTION);
                
                // 「はい：0」以外の場合、処理を中断する
                if (ans != 0) {
                    
                    return;
                    
                }
                
                // ログイン画面のインスタンス生成
                StaffLogin login = new StaffLogin();
                
                // ログイン画面を表示
                login.setVisible(true);
                
                // 現在の画面（検索画面）を閉じる
                close();
            }
        });
        btnLogout.setBounds(32, 388, 120, 30);
        contentPane.add(btnLogout);
        
        /**
         * ログインユーザー表示用ラベルを追加 使用しないのでコメントアウト UIのシンプル化のため、余分な要素を減らした
         * 将来的に仕様変更があった場合のため残しています
         */
        
//		JLabel lblLoginUser = new JLabel("");
//		lblLoginUser.setFont(new Font("SansSerif", Font.PLAIN, 14));
//		lblLoginUser.setBounds(650, 430, 220, 20);
//		contentPane.add(lblLoginUser);
        
        /**
         * ログインユーザー情報が設定されたときにラベルに表示するメソッド
         */
        
//		setLoginUserInfoLabel(lblLoginUser);
    }
    
    
    /**
     * ログインユーザー情報をラベルに表示するメソッド 使用しないのでコメントアウト 将来的に仕様変更があった場合のため残しています
     */
    
//	private void setLoginUserInfoLabel(JLabel label) {
//		// ログインユーザー情報が登録されているか確認するためのタイマー処理
//		javax.swing.Timer timer = new javax.swing.Timer(100, new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (!loginUserName.isEmpty()) {
//					label.setText(loginUserName + "様としてログイン中");
//					((javax.swing.Timer)e.getSource()).stop(); // タイマー停止
//				}
//			}
//		});
//		timer.start();
//	}
    
    // 自画面終了
    private void close() {
        this.dispose();
    }
}
