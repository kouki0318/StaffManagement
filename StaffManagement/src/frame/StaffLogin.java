package frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import common.DBClass;

public class StaffLogin extends JFrame {
    
    private JPanel contentPane;
    private JTextField textId;
    private JTextField textPassword;
    
    /**
     * アプリケーションを起動する
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    
                    StaffLogin frame = new StaffLogin();
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
    public StaffLogin() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        idLabel.setBounds(65, 51, 70, 40);
        contentPane.add(idLabel);
        
        JLabel passwordLabel = new JLabel("パスワード");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        passwordLabel.setBounds(65, 101, 70, 40);
        contentPane.add(passwordLabel);
        
        textId = new JTextField();
        textId.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textId.setColumns(10);
        textId.setBounds(147, 62, 96, 19);
        contentPane.add(textId);
        
        textPassword = new JTextField();
        textPassword.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textPassword.setColumns(10);
        textPassword.setBounds(147, 112, 96, 19);
        contentPane.add(textPassword);
        
        JButton btnLogin = new JButton("ログイン");
        btnLogin.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = textId.getText();
                String password = textPassword.getText();
                
                // DBClassをnewして操作開始
                DBClass db = new DBClass();
                List<String[]> ary = new ArrayList<>();
                
                // まずDB接続を開く
                if (db.dbOpen()) {
                    
                    // 接続成功したらログインデータ取得
                    ary = db.getLoginData(id, password);
                    
                    // ログイン判定
                    if (ary.size() > 0) {
                        
                        System.out.println("ログイン成功");
                        // 正常だったら次画面へ
                        StaffSearch search = new StaffSearch();
                        
                        // ログインユーザー情報を設定
                        String[] loginUserData = new String[2];
                        loginUserData[0] = ary.get(0)[0]; // 社員ID
                        loginUserData[1] = ary.get(0)[1]; // 氏名
                        search.setLoginUserInfo(loginUserData);
                        // 正常だったら次画面へ
                        search.setVisible(true);
                        close();
                        
                    } else {
                        
                        System.out.println("ログイン失敗");
                        // 失敗だったら、エラーメッセージ表示
                        JOptionPane.showMessageDialog(null, "IDまたはパスワードが間違っています。", "ログイン失敗", JOptionPane.ERROR_MESSAGE);
                        
                    }
                    
                    // 処理が終わったらDB接続を閉じる
                    db.dbClose();
                    
                } else {
                    
                    // DB接続失敗時の処理
                    System.out.println("データベース接続に失敗しました");
                    
                }
            }
        });
        btnLogin.setBounds(147, 166, 96, 19);
        contentPane.add(btnLogin);
        
        JButton btnRegister = new JButton("新規登録");
        btnRegister.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // 次画面のインスタンス生成
                StaffInsert addition = new StaffInsert();
                
                // 次画面を表示
                addition.setVisible(true);
                
                // 自画面を閉じる
                close();
                
            }
        });
        btnRegister.setBounds(147, 195, 96, 19);
        contentPane.add(btnRegister);
        
        JButton btnDb = new JButton("DB接続");
        btnDb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // DBクラスのインスタンス生成
                DBClass db = new DBClass();
                
                // DB接続
                if (db.dbOpen()) {
                    
                    // 成功ダイアログの表示
                    JOptionPane.showMessageDialog(null, "DB接続に成功しました", "成功", JOptionPane.PLAIN_MESSAGE);
                    
                } else {
                    
                    // エラーダイアログの表示
                    JOptionPane.showMessageDialog(null, "DB接続に失敗しました", "エラー", JOptionPane.ERROR_MESSAGE);
                    
                }
                // DB切断
                db.dbClose();
                
            }
        });
        btnDb.setFont(new Font("SansSerif", Font.PLAIN, 9));
        btnDb.setBounds(302, 235, 70, 16);
        contentPane.add(btnDb);
    }
    
    
    // 自画面終了
    private void close() {
        this.dispose();
    }
}
