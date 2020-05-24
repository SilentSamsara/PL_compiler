package ui;

import javax.swing.*;
import java.awt.*;

public class SymbolStream extends JFrame {
    JTextArea jta_morpheme;
    SymbolStream(JTextArea jTextArea){
        setTitle("¼ÇºÅÁ÷");
        setBounds(50,50,596,292);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jta_morpheme = new JTextArea(12,22);
        jta_morpheme.setFont(new Font("alias", Font.PLAIN, 14));
        JScrollPane jsp_morpheme = new JScrollPane(jta_morpheme);
        getContentPane().add(jsp_morpheme);
        jta_morpheme.setText(jTextArea.getText());

        setVisible(true);
    }

}
