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
	List<First> firsts;//first集
	List<Follow> follows;//follow集
	List<String> terminals;//终结符
	List<String> non_terminals;//非终结符
	List<Production> productions;//产生式
	List<String> stack_Message;//栈信息
	List<String> input_Message;//将输入的词素
	List<String> action_Message;//动作信息
	public static String three_address;
	public static String error = "";
	/*输出参考display()方法,可考虑输出语法分析表参考SLRAnalyerTable.display_Table()方法*/
	ArrayList<WordItem> wordItemList;
	ArrayList<Integer> integers = new ArrayList<>();
	public SLRParser() {
		three_address = "";
		error = "";
		Launcher.inputMessage = (ArrayList<String>) this.input_Message;
		//载入文法
		new SLRAnalyser();
		//词法分析
		File file = new File("./PascalCode/test.pas");
		ParseWords p = new ParseWords();
		try {
			p.doWork(file);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			System.out.println("词法分析出错");
			error =  "词法分析出错";
		}
		System.out.println();
		//准备工作
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
		//添加引用
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
			error = "词法分析出错:\n";
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
	
	private static String search(int encode){//搜索关键字所对应的编码
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
		Stack<Integer> cow =new Stack<Integer>();//项目集栈
		Stack<String> word = new Stack<String>();//词素栈
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
					string =string + "移进";
				}else if (ACTION.get(s).get(terminals.indexOf(get)).getAction() == 'r') {
					int y = ACTION.get(s).get(terminals.indexOf(get)).getNumber() - 1;
					Node out = new Node(y);
					for(int i = productions.get(y).getRightParts().size() ; i>0 ; i--) {//产生式右部出栈
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
					string = string + "按" +productions.get(y).getLeftPart()+"->"
							+productions.get(y).getRightParts()+"归约";
				}
				else if(ACTION.get(s).get(terminals.indexOf(get)).getAction()=='a') {
					string = string + "接受";
//					System.out.println(string);
					action_Message.add(string+"程序正确");
					action_Message.add("程序正确");
//					System.out.println("程序正确");
					root = Nodes.pop();
					return true;
				}else {
					action_Message.add("出错了，输入的程序有误，请检查程序");
//					System.out.println("\t\t出错了，输入的程序有误，请检查程序");
					error = "语法错误：line: "+a.line+" ";
					error = error + getExpected(s, terminals.indexOf(get));
					break;
				}
				action_Message.add(string);
//				System.out.println(string);
			} catch (Exception e) {
				action_Message.add("分析出现错误：输入的程序中出现了文法不可识别的字符，其虽然可以通过词法分析，但文法中不存在对应字符，故出现语法错误。");
				error = error + "分析出现错误：输入的程序中出现了文法不可识别的字符，其虽然可以通过词法分析，但文法中不存在对应字符，故出现语法错误。";
//				System.out.println("\n分析出现错误：输入的程序中出现了文法不可识别的字符，其虽然可以通过词法分析，但文法中不存在对应字符，故出现语法错误。");
//				e.printStackTrace();
				break;
				
			}
		}
		return false;
	}
	
}