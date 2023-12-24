package company.org;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFrame extends JFrame{
    private JPanel MainPanel;
    private JButton abcButton;
    private JButton defButton;
    private JButton ghiButton;
    private JButton PlusButton;
    private JButton jklButton;
    private JButton mnoButton;
    private JButton pqrButton;
    private JButton MinusButton;
    private JButton stuvButton;
    private JButton wxyzButton;
    private JButton DivideButton;
    private JButton LettersizeButton;
    private JButton CEButton;
    private JButton cButton;
    private JButton EqualsButton;
    private JTextField textField;
    public List<JButton> Listofbuttons = new ArrayList<>();
    public List<JButton> Listofsigns = new ArrayList<>();
    public List<Character> listaZnakow = new ArrayList<>();
    private long ostatnieKlikniecie = 0;

    public MainFrame(){
        setContentPane(MainPanel);
        setTitle("App");
        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Listofbuttons.add(abcButton);
        Listofbuttons.add(defButton);
        Listofbuttons.add(ghiButton);
        Listofbuttons.add(jklButton);
        Listofbuttons.add(mnoButton);
        Listofbuttons.add(pqrButton);
        Listofbuttons.add(stuvButton);
        Listofbuttons.add(wxyzButton);
        Listofsigns.add(PlusButton);
        Listofsigns.add(MinusButton);
        Listofsigns.add(DivideButton);
        listaZnakow.add('+');
        listaZnakow.add('-');
        listaZnakow.add('/');

        LettersizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton btn : Listofbuttons){
                    if (btn.getText().equals(btn.getText().toUpperCase())){
                        btn.setText(btn.getText().toLowerCase());
                    }else {
                        btn.setText(btn.getText().toUpperCase());
                    }
                }
            }
        });

        CEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Objects.equals(textField.getText(), "")) {
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                }
            }
        });

        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
            }
        });

        EqualsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int wyst = 0;
                for (Character znak : listaZnakow){
                    for (int i = 0; i<textField.getText().length();i++){
                        if (textField.getText().charAt(i) == znak){
                            wyst+=1;
                        }
                    }
                }
                if (wyst != 1){
                    JOptionPane.showMessageDialog(null,"Podaj tylko jeden operator");
                }else {
                    if (textField.getText().contains("+")){
                        String[] text = textField.getText().split("\\+");
                        textField.setText(textField.getText()+"="+text[0]+text[1]);
                    }
                    else if (textField.getText().contains("-")){
                        String[] text = textField.getText().split("-");
                        textField.setText(textField.getText()+"="+textField.getText().replace(text[1],"").replace("-",""));
                    }
                    else {
                        String[] text = textField.getText().split("/");
                        textField.setText(textField.getText()+"="+findLongestCommonSubstring(text[0],text[1]));
                    }
                }
            }
        });

        for (JButton btn : Listofbuttons){
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    obsluzKlikniecie(btn);
                }
            });
        }

        for (JButton sign : Listofsigns){
            sign.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textField.setText(textField.getText()+sign.getText());
                }
            });
        }
    }
    int licznik = 0;
    private void obsluzKlikniecie(JButton btn) {
        long teraz = System.currentTimeMillis();
        char aktualnaLitera;
        if (teraz - ostatnieKlikniecie > 1000) {
            licznik = 0;
            aktualnaLitera = btn.getText().charAt(0);
            textField.setText(textField.getText() + aktualnaLitera);
        } else {
            aktualnaLitera = btn.getText().charAt(licznik);
            int dl = textField.getText().length();
            textField.setText(textField.getText().substring(0,dl-1));
            textField.setText(textField.getText() + aktualnaLitera);
        }
        licznik += 1;
        if (licznik == btn.getText().length()){
            licznik = 0;
        }
        ostatnieKlikniecie = teraz;
    }

    public static String findLongestCommonSubstring(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];
        int maxLength = 0;
        int endIndex = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i - 1;
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        if (maxLength == 0) {
            return "";
        }
        return str1.substring(endIndex - maxLength + 1, endIndex + 1);
    }

    public static void main(String[] args){
        MainFrame myFrame = new MainFrame();
    }
}
