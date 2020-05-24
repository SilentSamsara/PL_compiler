package ui.JTableHeaderGroup;

import parser.models.Grammar;
import parser.models.SLRAnalyserTable;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class TableDemo extends JFrame {
    String[] header;
    String[][] content;


    public TableDemo() {
        super("Ô¤²â·ÖÎö±í");
        setBounds(900, 500, 900, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int length = Grammar.terminals.size() + Grammar.non_terminals.size() + 1;
        header = new String[length];
        content = new String[SLRAnalyserTable.ACTION.size()][length];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                header[i] = "";
            } else if (i < Grammar.terminals.size() + 1) {
                header[i] = Grammar.terminals.get(i - 1);
            } else {
                header[i] = Grammar.non_terminals.get(i - Grammar.terminals.size() - 1);
            }
        }
        for (int i = 0; i < SLRAnalyserTable.ACTION.size(); i++)
            for (int j = 0; j < length; j++) {
                if (j == 0) content[i][j] = String.valueOf(i);
                else if (j < SLRAnalyserTable.ACTION.get(i).size() + 1) {
                    content[i][j] = String.valueOf(SLRAnalyserTable.ACTION.get(i).get(j - 1).action) + String.valueOf(SLRAnalyserTable.ACTION.get(i).get(j - 1).number);
                } else {
                    content[i][j] = String.valueOf(SLRAnalyserTable.GOTO.get(i).get(j - SLRAnalyserTable.ACTION.get(i).size() - 1).number);
                }
            }

        DefaultTableModel dm = new DefaultTableModel(content, header);

        JTable table = new JTable(dm) {
            @Override
            protected JTableHeader createDefaultTableHeader() {

                return new GroupableTableHeader(columnModel);
            }
        };

        TableColumnModel cm = table.getColumnModel();
        ColumnGroup g_name = new ColumnGroup("ACTION");
        for (int i = 0; i < Grammar.terminals.size() + 1; i++) {
                g_name.add(cm.getColumn(i));
        }

        ColumnGroup g_name2 = new ColumnGroup("GOTO");
        for (int i = Grammar.terminals.size() + 1; i < length; i++) {
            g_name2.add(cm.getColumn(i));
        }

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        header.addColumnGroup(g_name);
        header.addColumnGroup(g_name2);
        table.getTableHeader().setUI(new GroupableTableHeaderUI());

        JScrollPane panel = new JScrollPane(table);
        getContentPane().add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        TableDemo demo = new TableDemo();
        demo.setVisible(true);
    }
}
