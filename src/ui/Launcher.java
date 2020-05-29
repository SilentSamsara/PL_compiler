package ui;

import main.SLRParser;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import parser.models.ItemSet;
import parser.tree.Node;
import ui.JTableHeaderGroup.TableDemo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends JFrame {
    static List<JFrame> frames = new ArrayList<>();
    JTextArea jta_code;
    JPanel jp;
    JButton btn_lexer, btn_analyze, btn_grammar, btn_items, btn_prediction, btn_error, btn_SDD, btn_threeAdd, btn_close;
    BtnListeners btnListeners;
    public static ArrayList<String> stackMessage, inputMessage, actionMessage;
    public static List<ItemSet> itemSets = new ArrayList<>();
    public static String threeAddress;

    public Launcher(){
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
        jta_code.getDocument().addDocumentListener(new textListener());
        JScrollPane jsp_code = new JScrollPane(jta_code);    //���ı�������������
        Dimension size = jta_code.getPreferredSize();    //����ı������ѡ��С
        jsp_code.setBounds(15, 50, size.width, size.height);
        jsp_code.setRowHeaderView(new LineNumberHeaderView());
        jp.add(jsp_code);
        //�Ǻ�����ť
        btn_lexer = new JButton("��ȡ�Ǻ���");
        btn_lexer.setBounds(35, 350, 100, 30);
        btn_lexer.addActionListener(btnListeners);
        jp.add(btn_lexer);
        //������ť
        btn_analyze = new JButton("��������");
        btn_analyze.setBounds(150,350,100,30);
        btn_analyze.addActionListener(btnListeners);
        btn_analyze.setEnabled(false);
        jp.add(btn_analyze);
        //��������ַ��
        btn_threeAdd = new JButton("��������ַ��");
        btn_threeAdd.setBounds(265,350,100,30);
        btn_threeAdd.addActionListener(btnListeners);
        btn_threeAdd.setEnabled(false);
        jp.add(btn_threeAdd);
        //�ر����д���
        btn_close = new JButton("��");
        btn_close.setBounds(413,30,28,28);
        btn_close.setFont(new Font("alias",Font.PLAIN,14));
        btn_close.addActionListener(btnListeners);
        jp.add(btn_close);
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
        //�鿴SDD
        btn_SDD = new JButton("�鿴SDD");
        btn_SDD.setBounds(380, 240,100, 30);
        btn_SDD.addActionListener(btnListeners);
        jp.add(btn_SDD);

        //�򿪴�����־
        btn_error = new JButton("������־");
        btn_error.setBounds(380,280,100,30);
        btn_error.addActionListener(btnListeners);
        jp.add(btn_error);
        setVisible(true);
    }




    void initView(){
        //���ڳ�ʼ��
        setTitle("�﷨������");
        setBounds(700, 300, 500, 460);
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
                if (jta_code.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "���������");
                }else {
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
                }

            }else if(e.getSource().equals(btn_analyze)){
                new SLRParser();
                frames.add(new AnalyzeProcess(stackMessage,inputMessage,actionMessage));
                btn_threeAdd.setEnabled(true);
                if (SLRParser.error.equals("")){
                    JOptionPane.showMessageDialog(null,"������ɣ�û���﷨����");
                }else {
                    JOptionPane.showMessageDialog(null, SLRParser.error);
                    FileWriter writer;
                    try {
                        writer = new FileWriter("./PascalCode/error.txt");
                        writer.write(SLRParser.error);
                        writer.flush();
                        writer.close();
                    } catch (IOException a) {
                        a.printStackTrace();
                    }
                }
            }else if (e.getSource().equals(btn_grammar)){
                File file = new File("./PascalCode/Grammar.txt");
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else if (e.getSource().equals(btn_items)){
                new SLRParser();
                frames.add(new ItemFamilies(itemSets));
            }else if (e.getSource().equals(btn_prediction)){
                new SLRParser();
                frames.add(new TableDemo());
            }else if (e.getSource().equals(btn_error)){
                try {
                    File file = new File("./PascalCode/error.txt");
                    Desktop.getDesktop().open(file);
                } catch (Exception ioException) {
                    JOptionPane.showMessageDialog(null, "δ���ִ����ļ�");
                }
            }else if (e.getSource().equals(btn_SDD)){
                frames.add(new SDDTable());
            }else if (e.getSource().equals(btn_threeAdd)){
                File file = new File("./PascalCode/error.txt");
                try{
                    file.delete();
                }catch (Exception ex){
                    //TODO
                }
                new SLRParser();
                frames.add(new ThreeAddress(threeAddress));
                if (!Node.error.isEmpty()){
                    JOptionPane.showMessageDialog(null,"��⵽���������򿪴����ļ��鿴!");
                }else {
                    JOptionPane.showMessageDialog(null,"������ɣ�û���������");
                }
            }else if(e.getSource().equals(btn_close)){
                for (JFrame jFrame:frames){
                    jFrame.dispose();
                }
                frames.clear();
            }
        }
    }

    class textListener implements DocumentListener{

        @Override
        public void insertUpdate(DocumentEvent e) {
            btn_analyze.setEnabled(false);
            btn_threeAdd.setEnabled(false);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            btn_analyze.setEnabled(false);
            btn_threeAdd.setEnabled(false);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            btn_analyze.setEnabled(false);
            btn_SDD.setEnabled(false);
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


