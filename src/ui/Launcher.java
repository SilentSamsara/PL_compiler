package ui;

import main.SLRParser;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import parser.models.ItemSet;
import ui.JTableHeaderGroup.TableDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends JFrame {
    JTextArea jta_code;
    JPanel jp;
    JButton btn_lexer, btn_analyze, btn_grammar, btn_items, btn_prediction;
    BtnListeners btnListeners;
    public static ArrayList<String> stackMessage, inputMessage, actionMessage;
    public static List<ItemSet> itemSets = new ArrayList<>();

    public Launcher() {
        initView();

        //代码域tip
        JLabel jta_tip = new JLabel("↓ ↓ ↓ Enter your pascal code");
        jta_tip.setFont(new Font("default",Font.BOLD,15));
        jta_tip.setBounds(50, 20, 300, 20);
        jp.add(jta_tip);
        //设置代码域
        jta_code = new JTextArea(12, 27);
        jta_code.setLineWrap(true);    //设置文本域中的文本为自动换行
        jta_code.setForeground(Color.BLACK);    //设置组件的背景色
        jta_code.setFont(new Font("alias", Font.PLAIN, 16));    //修改字体样式
        JScrollPane jsp_code = new JScrollPane(jta_code);    //将文本域放入滚动窗口
        Dimension size = jta_code.getPreferredSize();    //获得文本域的首选大小
        jsp_code.setBounds(15, 50, size.width, size.height);
        jsp_code.setRowHeaderView(new LineNumberHeaderView());
        jp.add(jsp_code);
        //记号流按钮
        btn_lexer = new JButton("获取记号流");
        btn_lexer.setBounds(95, 350, 100, 30);
        btn_lexer.addActionListener(btnListeners);
        jp.add(btn_lexer);
        //分析按钮
        btn_analyze = new JButton("分析句子");
        btn_analyze.setBounds(210,350,100,30);
        btn_analyze.addActionListener(btnListeners);
        btn_analyze.setEnabled(false);
        jp.add(btn_analyze);
        //文法文件按钮
        btn_grammar = new JButton("编辑文法文件");
        btn_grammar.setBounds(380,80,100,30);
        btn_grammar.addActionListener(btnListeners);
        jp.add(btn_grammar);
        //生成规范族
        btn_items = new JButton("生成规范族");
        btn_items.setBounds(380,120,100,30);
        btn_items.addActionListener(btnListeners);
        jp.add(btn_items);
        //生成预测分析表8
        btn_prediction = new JButton("生成分析表");
        btn_prediction.setBounds(380,160,100,30);
        btn_prediction.addActionListener(btnListeners);
        jp.add(btn_prediction);

        setVisible(true);
    }




    void initView(){
        //窗口初始化
        setTitle("语法分析器");
        setBounds(700, 300, 500, 500);
        jp = new JPanel();
        jp.setLayout(null);
        setContentPane(jp);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        btnListeners = new BtnListeners();
        Listeners.launcher = this;
        this.addWindowListener(new Listeners());
    }

    class BtnListeners implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Listeners.Cleaner.cleanAll();

            if (e.getSource().equals(btn_lexer)){
                new Lexer(jta_code);

                FileWriter writer;
                try {
                    writer = new FileWriter("./PascalCode/test.pas");
                    writer.write(jta_code.getText());
                    writer.flush();
                    writer.close();
                } catch (IOException a) {
                    a.printStackTrace();
                }
                btn_analyze.setEnabled(true);
            }else if(e.getSource().equals(btn_analyze)){
                new SLRParser();
                new AnalyzeProcess(stackMessage,inputMessage,actionMessage);
                if (SLRParser.error.equals("")){
                    JOptionPane.showMessageDialog(null,"分析完成，没有语法错误");
                }else {
                    JOptionPane.showMessageDialog(null, SLRParser.error);
                }
                btn_analyze.setEnabled(false);
            }else if (e.getSource().equals(btn_grammar)){
                File file = new File("./PascalCode/Grammar.txt");
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if (e.getSource().equals(btn_items)){
                new SLRParser();
                new ItemFamilies(itemSets);
            }else if (e.getSource().equals(btn_prediction)){
                new SLRParser();
                new TableDemo();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            BeautyEyeLNFHelper.frameBorderStyle= BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
            UIManager.put("RootPane.setupButtonVisible", false);
        }catch (Exception e) {
        }
        new Launcher();

    }
}


