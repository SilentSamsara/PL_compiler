package ui;

import lexer.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Lexer extends JFrame {
    Object[] name = {"名字", "属性", "值"};
    Object[][] a;
    JTable table;
    JScrollPane jsp_table;
    JTextArea jta, jta_morpheme;
    JPanel jp;
    JTextArea console;
    public Lexer(JTextArea jTextArea) {
        Font tip_font = new Font("default",Font.BOLD,15);
        //初始化窗口
        Listeners.lexer = this;
        this.addWindowListener(new Listeners());
        setTitle("词法分析器");
        setBounds(450, 200, 950, 730);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        jp = new JPanel();
        jp.setLayout(null);
        setContentPane(jp);
        //文本域提示标签
        JLabel jta_tip = new JLabel("↓ ↓ ↓ Enter your pascal code");
        jta_tip.setFont(tip_font);
        jta_tip.setBounds(80, 20, 300, 20);
        jp.add(jta_tip);
        //代码文本域
        jta = new JTextArea(12, 23);
        jta.setText(jTextArea.getText());
        jta.setEnabled(false);
        jta.setLineWrap(true);    //设置文本域中的文本为自动换行
        jta.setForeground(Color.BLACK);    //设置组件的背景色
        jta.setFont(new Font("alias", Font.PLAIN, 16));    //修改字体样式
        JScrollPane jsp_code = new JScrollPane(jta);    //将文本域放入滚动窗口
        Dimension size = jta.getPreferredSize();    //获得文本域的首选大小
        jsp_code.setBounds(50, 50, size.width, size.height);
        jp.add(jsp_code);
        //分析button
//        JButton analyze = new JButton("分析");
//        analyze.setBounds(175, 300, 60, 30);
//        analyze.addActionListener(new MyListener());
//        jp.add(analyze);
        //词素标签
        JLabel morpheme_tip = new JLabel("词素");
        morpheme_tip.setFont(tip_font);
        morpheme_tip.setBounds(57,347,60,30);
        jp.add(morpheme_tip);
        JScrollPane jsp_morpheme = new JScrollPane();
        jp.add(jsp_morpheme);
        //符号表提示标签
        JLabel table_tip = new JLabel("符号表");
        table_tip.setBounds(593,284,100,30);
        table_tip.setFont(tip_font);
        jp.add(table_tip);
        //符号表
        a = new Object[0][3];
        table = new JTable(a, name);
        table.setFont(new Font("default", Font.PLAIN, 14));
        jsp_table = new JScrollPane(table);
        jsp_table.setViewportView(table);
        jsp_table.setBounds(675, 282, 240, 400);
        jp.add(jsp_table);
        validate();
        //控制台标签
        JLabel console_tip = new JLabel("控制台");
        console_tip.setBounds(456,15,100,30);
        console_tip.setFont(tip_font);
        jp.add(console_tip);
        JScrollPane  jsp_console = new JScrollPane();
        jp.add(jsp_console);
        jsp_morpheme.setBounds(50,390,596,292);
        //词素内容
        jta_morpheme = new JTextArea(12,22);
        jsp_morpheme.setViewportView(jta_morpheme);
        jta_morpheme.setBorder(BorderFactory.createLineBorder(Color.black));
        jta_morpheme.setFont(new Font("alias", Font.PLAIN, 14));
        jsp_console.setBounds(367,53,518,216);
        //控制台内容
        console = new JTextArea(12,80);
        jsp_console.setViewportView(console);
        setVisible(true);

    }


    public void getUnit() {
        String code = jta.getText();
        ParseWords parseWords = new ParseWords();
        try {
            parseWords.doWorkByStr(code);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        a = new Object[SymbolTable.wordItemList.size()][3];
        for (int i = 0; i < SymbolTable.wordItemList.size(); i++) {
            WordItem wordItem = SymbolTable.wordItemList.get(i);
            SortCode sortCode = SymbolTable.getSortCodeByEncode(wordItem.encode);
            if (wordItem.encode <= 131) {
                a[i][0] = wordItem.key;
                a[i][1] = sortCode.symbol;
            } else {
                a[i][0] = sortCode.word;
                a[i][1] = sortCode.symbol;
                a[i][2] = wordItem.key;
            }
        }
        table = new JTable(a, name);
        table.setFont(new Font("default", Font.PLAIN, 14));
        jsp_table.setViewportView(table);

        //词素输出
        jta_morpheme.setText("");
        int line = 1;
        String lineOut = "";
        for (WordItem wordItem : SymbolTable.wordItemList) {
            if (line != wordItem.line) {
                lineOut = "";
                jta_morpheme.append("\n");
                line = wordItem.line;
            }
            lineOut = lineOut + wordItem;
            jta_morpheme.append(wordItem.toString());
        }
        //打印控制台
        console.setText("");
        console.setText(String.valueOf(ErrorWriter.errorList.size()) + " error(s)" + "\n");
        if (!ErrorWriter.errorList.isEmpty()) {
            for (int i = 0; i < ErrorWriter.errorList.size(); i++) {
                console.append(ErrorWriter.getErrorString(i) + "\n");
            }
        }
    }


//    public static void main(String[] args) throws Exception {
//    	try {
//			BeautyEyeLNFHelper.frameBorderStyle=FrameBorderStyle.generalNoTranslucencyShadow;
//			BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
//			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//			UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
//			UIManager.put("RootPane.setupButtonVisible", false);
//		}catch (Exception e) {
//		}
//        new MainFrame();
//
//    }
}

