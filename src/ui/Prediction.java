package ui;

import parser.models.Grammar;
import parser.models.SLRAnalyserTable;
import javax.swing.*;

public class Prediction extends JFrame {


    Prediction() {
        setTitle("Ô¤²â·ÖÎö±í");
        setBounds(900, 500, 900, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int length = Grammar.terminals.size() + Grammar.non_terminals.size() + 1;
        String[] names = new String[length];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                names[i] = "";
            } else if (i < Grammar.terminals.size() + 1) {
                names[i] = Grammar.terminals.get(i - 1);
            } else {
                names[i] = Grammar.non_terminals.get(i - Grammar.terminals.size() - 1);
            }
        }

        String[][] content = new String[SLRAnalyserTable.ACTION.size()][length];
        for (int i = 0; i < SLRAnalyserTable.ACTION.size(); i++)
            for (int j = 0; j < length; j++) {
                if (j == 0) content[i][j] = String.valueOf(i);
                else if (j < SLRAnalyserTable.ACTION.get(i).size() + 1) {
                    content[i][j] = String.valueOf(SLRAnalyserTable.ACTION.get(i).get(j - 1).action) + String.valueOf(SLRAnalyserTable.ACTION.get(i).get(j - 1).number);
                } else {
                    content[i][j] = String.valueOf(SLRAnalyserTable.GOTO.get(i).get(j - SLRAnalyserTable.ACTION.get(i).size() - 1).number);
                }
            }


        JTable jTable = new JTable(content, names);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        getContentPane().add(jScrollPane);

        setVisible(true);
    }
}
