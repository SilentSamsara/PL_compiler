package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.sun.applet2.AppletParameters;
import lexer.*;
import parser.models.First;
import parser.models.Follow;
import parser.models.Grammar;
import parser.models.Lattice;
import parser.models.Production;
import parser.models.SLRAnalyserTable;
import parser.tools.SLRAnalyser;
import parser.tree.Node;
import ui.Launcher;

public class SLRParser {
	List<List<Lattice>> ACTION;
	List<List<Lattice>> GOTO;
	List<First> firsts;//first��
	List<Follow> follows;//follow��
	List<String> terminals;//�ս��
	List<String> non_terminals;//���ս��
	List<Production> productions;//����ʽ
	List<String> stack_Message;//ջ��Ϣ
	List<String> input_Message;//������Ĵ���
	List<String> action_Message;//������Ϣ
	public static String three_address;
	public static String error = "";
	/*����ο�display()����,�ɿ�������﷨������ο�SLRAnalyerTable.display_Table()����*/
	ArrayList<WordItem> wordItemList;
	ArrayList<Integer> integers = new ArrayList<>();
	public SLRParser() {
		three_address = "";
		error = "";
		Launcher.inputMessage = (ArrayList<String>) this.input_Message;
		//�����ķ�
		new SLRAnalyser();
		//�ʷ�����
		File file = new File("./PascalCode/test.pas");
		ParseWords p = new ParseWords();
		try {
			p.doWork(file);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			System.out.println("�ʷ���������");
			error =  "�ʷ���������";
		}
		System.out.println();
		//׼������
		this.ACTION = SLRAnalyserTable.ACTION;
		this.GOTO = SLRAnalyserTable.GOTO;
		this.firsts = Grammar.firsts;
		this.follows = Grammar.follows;
		this.terminals = Grammar.terminals;
		this.non_terminals = Grammar.non_terminals;
		this.productions = Grammar.productions;
		this.wordItemList = SymbolTable.wordItemList;
		stack_Message = new ArrayList<String>();
		input_Message = new ArrayList<String>();
		action_Message = new ArrayList<String>();
		three_address = "";
		//�������
		Launcher.stackMessage = (ArrayList<String>) stack_Message;
		Launcher.actionMessage = (ArrayList<String>) action_Message;
		Launcher.inputMessage = (ArrayList<String>) input_Message;
		matching();
		display();
		Node.error = new ArrayList<>();
		Node.idTable = new HashMap<>();
		Node.num = new ArrayList<>();
		Node.label = 0;
		Node.temp = 0;
		try {
			three_address = root.getCode(root);
			root.display(0);
		}catch (Exception e){
		}
		Launcher.threeAddress = three_address;
		FileOutputStream fileOutputStream=null;
		try {
			fileOutputStream = new FileOutputStream("./PascalCode/error.txt");
			for (int i = 0 ; i <Node.error.size() ; i++)
				fileOutputStream.write((Node.error.get(i)+"\n").getBytes());
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(three_address);
		if ( ErrorWriter.errorList.size() != 0 ){
			error = "�ʷ���������:\n";
			for (int i = 0 ; i < ErrorWriter.errorList.size() ; i++){
				error = error + " " +ErrorWriter.getErrorString(i);
			}
		}
		if (!error.equals("")) {
			System.out.println(error);
		}
	}
	void display() {
		int i=0;
		for(i=0;i<stack_Message.size();i++) {
			System.out.println(stack_Message.get(i)+"\t\t"+input_Message.get(i)+"\t\t"+action_Message.get(i));
		}
		for(;i<action_Message.size();i++) {
			System.out.println(action_Message.get(i));
		}
		for (i = 0 ; i<integers.size() ;i++)
			System.out.println(productions.get(integers.get(i)).getLeftPart()+"->"+productions.get(integers.get(i)).getRightParts());
	}
	
	private static String search(int encode){//�����ؼ�������Ӧ�ı���
		for (SortCode sortCode : SymbolTable.sortCodeList){
			if (sortCode.encode == encode){
				if (sortCode.word.equals("INTEGER_VALUE")||sortCode.word.equals("FLOAT_VALUE")) {
					return "num";
				}else if (sortCode.word.equals("STRING_VALUE")) {
					return "string";
				}else
					return sortCode.word;
			}
		}
		return "Error";
	}
	
	private String getExpected(int x,int y) {
		String error = "Unexpected: "+terminals.get(y)+", Expected: ";
		for(int i = 0;i < terminals.size() ; i++){
			char a = ACTION.get(x).get(i).getAction();
			if (a == 'r' || a == 'a' || a == 's') {
				error = error + terminals.get(i)+" ";
			}
		}
		return error;
	}
	Node root;
	Stack<WordItem> stack = new Stack<WordItem>();
	Stack<Node> Nodes = new Stack<Node>();
	boolean matching() {
		for(int i = wordItemList.size()-1 ; i >= 0 ; i--) {
			stack.add(wordItemList.get(i));
		}
		stack.add(0,new WordItem("$", 131, wordItemList.get(wordItemList.size()-1).line));
		int s,t;
		Stack<Integer> cow =new Stack<Integer>();//��Ŀ��ջ
		Stack<String> word = new Stack<String>();//����ջ
		cow.add(0);
		WordItem a = wordItemList.get(0);
		for(;;) {
			String string = "";
			for(int i=0; i< cow.size() ;i++)
			{
				if(i!=0) {
					string = string + " " + word.get(i-1);
				}
				string = string + cow.get(i);
			}
			stack_Message.add(string);
//			System.out.print(string+"\t\t");
			string = "";
			for(int i = stack.size() - 1 ; i >= 0;i--)
				string = string + " " + stack.get(i).key;
			input_Message.add(string);
//			System.out.print(string);
			string = "";
			s = cow.peek();
			try {
				String get = search(a.encode).toLowerCase();
				if (search(a.encode).equals("$")) {
					t = ACTION.get(s).get(terminals.indexOf("$")).getNumber();
				}else
					t = ACTION.get(s).get(terminals.indexOf(get)).getNumber();
				
				
				if (ACTION.get(s).get(terminals.indexOf(get)).getAction() == 's') {
					cow.push(t);
					word.push(terminals.get(terminals.indexOf(get)));
					Node x = new Node(-1);
					x.line = a.line;
					x.encode = a.encode;
					x.key = a.key;
					stack.pop();
					Nodes.push(x);
					a = stack.peek();
					string =string + "�ƽ�";
				}else if (ACTION.get(s).get(terminals.indexOf(get)).getAction() == 'r') {
					int y = ACTION.get(s).get(terminals.indexOf(get)).getNumber() - 1;
					Node out = new Node(y);
					for(int i = productions.get(y).getRightParts().size() ; i>0 ; i--) {//����ʽ�Ҳ���ջ
						cow.pop();
						word.pop();
						out.children.add(Nodes.pop());
					}
					Collections.reverse(out.children);
					integers.add(new Integer(y));

					t = cow.peek();
					String aString = productions.get(y).getLeftPart();
					cow.push(GOTO.get(t).get(non_terminals.indexOf(aString)).getNumber());
					word.push(aString);
					Nodes.push(out);
					string = string + "��" +productions.get(y).getLeftPart()+"->"
							+productions.get(y).getRightParts()+"��Լ";
				}
				else if(ACTION.get(s).get(terminals.indexOf(get)).getAction()=='a') {
					string = string + "����";
//					System.out.println(string);
					action_Message.add(string+"������ȷ");
					action_Message.add("������ȷ");
//					System.out.println("������ȷ");
					root = Nodes.pop();
					return true;
				}else {
					action_Message.add("�����ˣ�����ĳ��������������");
//					System.out.println("\t\t�����ˣ�����ĳ��������������");
					error = "�﷨����line: "+a.line+" ";
					error = error + getExpected(s, terminals.indexOf(get));
					break;
				}
				action_Message.add(string);
//				System.out.println(string);
			} catch (Exception e) {
				action_Message.add("�������ִ�������ĳ����г������ķ�����ʶ����ַ�������Ȼ����ͨ���ʷ����������ķ��в����ڶ�Ӧ�ַ����ʳ����﷨����");
				error = error + "�������ִ�������ĳ����г������ķ�����ʶ����ַ�������Ȼ����ͨ���ʷ����������ķ��в����ڶ�Ӧ�ַ����ʳ����﷨����";
//				System.out.println("\n�������ִ�������ĳ����г������ķ�����ʶ����ַ�������Ȼ����ͨ���ʷ����������ķ��в����ڶ�Ӧ�ַ����ʳ����﷨����");
//				e.printStackTrace();
				break;
				
			}
		}
		return false;
	}
	
}