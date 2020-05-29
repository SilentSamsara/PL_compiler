package ui;

import parser.tree.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ThreeAddress extends JFrame {
    public ThreeAddress(String data) {
        setTitle("ÈýµØÖ·Âë");
        setBounds(1250, 450, 500, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JTextArea textArea = new JTextArea(12, 22);
        textArea.setFont((new Font("alias", Font.PLAIN, 14)));
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);

        textArea.setText(data);


        setVisible(true);
    }
}
