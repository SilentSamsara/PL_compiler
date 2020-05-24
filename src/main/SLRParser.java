package main;

import java.io.File;
import java.io.IOException;
import java.util.*;

import lexer.*;
import parser.models.First;
import parser.models.Follow;
import parser.models.Grammar;
import parser.models.Lattice;
import parser.models.Production;
import parser.models.SLRAnalyserTable;
import parser.tools.SLRAnalyser;
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
	public static String error = "";
	/*输出参考display()方法,可考虑输出语法分析表参考SLRAnalyerTable.display_Table()方法*/
	ArrayList<WordItem> wordItemList;
	public SLRParser() {
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
		//添加引用
		Launcher.stackMessage = (ArrayList<String>) stack_Message;
		Launcher.actionMessage = (ArrayList<String>) action_Message;
		Launcher.inputMessage = (ArrayList<String>) input_Message;
		matching();
		display();
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
	
	Stack<WordItem> stack = new Stack<WordItem>();
	boolean matching() {
		for(int i = wordItemList.size()-1 ; i >= 0 ; i--) {
			stack.add(wordItemList.get(i));
		}
		stack.add(0,new WordItem("$", 131, wordItemList.get(wordItemList.size()-1).line));
		int s,t;
		Stack<Integer> cow =new Stack<Integer>();
		Stack<String> p = new Stack<String>();
		cow.add(0);
		WordItem a = wordItemList.get(0);
		for(;;) {
			String string = "";
			for(int i=0; i< cow.size() ;i++)
			{
				if(i!=0) {
					string = string + " " + p.get(i-1);
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
					p.push(terminals.get(terminals.indexOf(get)));
					stack.pop();
					a = stack.peek();
					string =string + "移进";
				}else if (ACTION.get(s).get(terminals.indexOf(get)).getAction() == 'r') {
					int y = ACTION.get(s).get(terminals.indexOf(get)).getNumber() - 1;
					for(int i = productions.get(y).getRightParts().size() ; i>0 ; i--) {
						cow.pop();
						p.pop();
					}
					t = cow.peek();
					String aString = productions.get(y).getLeftPart();
					cow.push(GOTO.get(t).get(non_terminals.indexOf(aString)).getNumber());
					p.push(aString);
					string = string + "按" +productions.get(y).getLeftPart()+"->"
							+productions.get(y).getRightParts()+"归约";
				}
				else if(ACTION.get(s).get(terminals.indexOf(get)).getAction()=='a') {
					string = string + "接受";
//					System.out.println(string);
					action_Message.add(string+"程序正确");
					action_Message.add("程序正确");
//					System.out.println("程序正确");
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