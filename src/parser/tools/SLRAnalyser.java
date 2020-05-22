package parser.tools;

import lexer.SortCode;
import lexer.SymbolTable;
import parser.models.Grammar;
import parser.models.ItemFamily;
import parser.models.SLRAnalyserTable;

public class SLRAnalyser {
	/**
	 * SLR分析器
	 * 
	 * 输入：文法保存的位置可以创建从first集到预测分析表的所有资料
	 * 
	 * 输入：给出待判断程序词法分析结果可以输出程序运行时的栈内内容的变化和程序正误。
	 * 
	 * 输出：可以选择性的输出所有结果，如First、follow、项目集规范族、SLR预测分析表等等。
	 */
	private static Integer search(String key){//搜索关键字所对应的编码
		String bigWord = key.toUpperCase();
		for (SortCode sortCode : SymbolTable.sortCodeList){
			if (sortCode.word.equals(bigWord)){
				return sortCode.encode;
			}
		}
		return -1;
	}
	public SLRAnalyser() {
		Grammar G = new Grammar("./PascalCode/Grammar.txt");
		G.printAll();// 输出文法，first集，follow集
		ItemFamily ItFml = new ItemFamily(G);
		ItFml.print();// 输出项目集规范族
		SLRAnalyserTable analyserTable = new SLRAnalyserTable(G, ItFml);
	}
}
