package ui;

import parser.models.Item;
import parser.models.ItemSet;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemFamilies extends JFrame {
    public List<ItemSet> itemSets = new ArrayList<>();

    ItemFamilies(List<ItemSet> list) {
        this.itemSets = list;
        setTitle("项目集规范族");
        String[] names = {"", "", "", "", "", "", "", ""};
        setBounds(30, 500, 900, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int rows = (int) Math.ceil(itemSets.size() * 1.0 / 8);
        String[][] content = new String[rows][8];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 8; j++) {
                int index = i * 8 + j;
                if (index >= itemSets.size())   break;
                StringBuilder s = new StringBuilder();
                s.append("I").append(index).append(":").append("\n");
                for (Item item : itemSets.get(index).items) {
                    s.append(item.toString()).append("\n");
                }
                content[i][j] = s.toString();
            }

        }


        JTable table = new JTable(content, names);
        table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());
        table.getTableHeader().setFont(new Font("alias", Font.PLAIN, 25));
        JScrollPane jScrollPane = new JScrollPane(table);
        getContentPane().add(jScrollPane);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setPreferredSize(new Dimension(0, 0));
        table.getTableHeader().setDefaultRenderer(renderer);
        setVisible(true);
    }
}

class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
    public TableCellTextAreaRenderer() {
        this.setFont(new Font("alias", Font.PLAIN, 13));
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        // 计算当下行的最佳高度
        int maxPreferredHeight = 0;
        for (int i = 0; i < table.getColumnCount(); i++) {
            setText("" + table.getValueAt(row, i));
            setSize(table.getColumnModel().getColumn(column).getWidth(), 0);
            maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height);
        }

        if (table.getRowHeight(row) != maxPreferredHeight)  // 少了这行则处理器瞎忙
            table.setRowHeight(row, maxPreferredHeight);

        setText(value == null ? "" : value.toString());
        return this;
    }
}