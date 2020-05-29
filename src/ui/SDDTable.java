package ui;

import javax.swing.*;
import java.awt.*;

public class SDDTable extends JFrame {
    public SDDTable() {
        setTitle("语义规则表");
        setBounds(30, 500, 900, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        String[] names = {"产生式","语义规则"};
        String[][] contents = new String[25][2];
        for (int i=0; i<25; i++){
            contents[i][0] = SDD.productions[i];
            contents[i][1] = SDD.rules[i];
        }

        JTable table = new JTable(contents, names);
        table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
        table.getTableHeader().setFont(new Font("alias", Font.PLAIN, 15));
        JScrollPane jScrollPane = new JScrollPane(table);
        getContentPane().add(jScrollPane);


        setVisible(true);
    }


}
