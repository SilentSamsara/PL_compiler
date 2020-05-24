package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnalyzeProcess extends JFrame {
    AnalyzeProcess(ArrayList<String> stackMessage, ArrayList<String> inputMessage, ArrayList<String> actionMessage){
        //窗口初始化
        setTitle("分析过程");
        setBounds(1000,50,900,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //表格构造
        int height = stackMessage.size();
        String[] name = {"栈区","输入区","动作"};
        String[][] content = new String[height][3];
        for (int i = 0; i < height; i++){
            content[i][0] = stackMessage.get(i).length()<39?stackMessage.get(i):"..." +
                            stackMessage.get(i).substring(stackMessage.get(i).length()-39);
            content[i][1] = inputMessage.get(i);
            content[i][2] = actionMessage.get(i);
        }
        JTable table = new JTable(content,name);
        table.setFont(new Font("default", Font.PLAIN, 14));
        JScrollPane jScrollPane = new JScrollPane(table);
        getContentPane().add(jScrollPane);



        setVisible(true);
    }
}
