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

        //������tip
        JLabel jta_tip = new JLabel("�� �� �� Enter your pascal code");
        jta_tip.setFont(new Font("default",Font.BOLD,15));
        jta_tip.setBounds(50, 20, 300, 20);
        jp.add(jta_tip);
        //���ô�����
        jta_code = new JTextArea(12, 27);
        jta_code.setLineWrap(true);    //�����ı����е��ı�Ϊ�Զ�����
        jta_code.setForeground(Color.BLACK);    //��������ı���ɫ
        jta_code.setFont(new Font("alias", Font.PLAIN, 16));    //�޸�������ʽ
        JScrollPane jsp_code = new JScrollPane(jta_code);    //���ı�������������
        Dimension size = jta_code.getPreferredSize();    //����ı������ѡ��С
        jsp_code.setBounds(15, 50, size.width, size.height);
        jsp_code.setRowHeaderView(new LineNumberHeaderView());
        jp.add(jsp_code);
        //�Ǻ�����ť
        btn_lexer = new JButton("��ȡ�Ǻ���");
        btn_lexer.setBounds(95, 350, 100, 30);
        btn_lexer.addActionListener(btnListeners);
        jp.add(btn_lexer);
        //������ť
        btn_analyze = new JButton("��������");
        btn_analyze.setBounds(210,350,100,30);
        btn_analyze.addActionListener(btnListeners);
        btn_analyze.setEnabled(false);
        jp.add(btn_analyze);
        //�ķ��ļ���ť
        btn_grammar = new JButton("�༭�ķ��ļ�");
        btn_grammar.setBounds(380,80,100,30);
        btn_grammar.addActionListener(btnListeners);
        jp.add(btn_grammar);
        //���ɹ淶��
        btn_items = new JButton("���ɹ淶��");
        btn_items.setBounds(380,120,100,30);
        btn_items.addActionListener(btnListeners);
        jp.add(btn_items);
        //����Ԥ�������8
        btn_prediction = new JButton("���ɷ�����");
        btn_prediction.setBounds(380,160,100,30);
        btn_prediction.addActionListener(btnListeners);
        jp.add(btn_prediction);

        setVisible(true);
    }




    void initView(){
        //���ڳ�ʼ��
        setTitle("�﷨������");
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
                    JOptionPane.showMessageDialog(null,"������ɣ�û���﷨����");
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


