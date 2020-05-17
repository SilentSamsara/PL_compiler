package lexer;

import java.util.ArrayList;

public class SymbolTable {
	public static ArrayList<SortCode> sortCodeList = new ArrayList<>();
    public static ArrayList<WordItem> wordItemList = new ArrayList<>();//“—∆•≈‰œÓƒø
	public static void initLoadSortCodeList(){
		sortCodeList.add(new SortCode("READ", 96, "function"));
		sortCodeList.add(new SortCode("READLN", 97, "function"));
		sortCodeList.add(new SortCode("WRITE", 98, "function"));
		sortCodeList.add(new SortCode("WRITELN", 99, "function"));
		sortCodeList.add(new SortCode("STRING", 100, "string"));
		sortCodeList.add(new SortCode("BEGIN", 101, "Start"));
		sortCodeList.add(new SortCode("END", 102, "End"));
		sortCodeList.add(new SortCode("IF", 103, "If"));
		sortCodeList.add(new SortCode("THEN", 104, "Then"));
		sortCodeList.add(new SortCode("ELSE", 105, "Else"));
		sortCodeList.add(new SortCode("WHILE", 106, "While"));
		sortCodeList.add(new SortCode("DO", 107, "Do"));
		sortCodeList.add(new SortCode("PROGRAM", 108, "PROGRAM"));
		sortCodeList.add(new SortCode("INTEGER", 109, "INTEGER"));
		sortCodeList.add(new SortCode("VAR", 110, "Var"));
		sortCodeList.add(new SortCode("<", 111, "Lt"));
		sortCodeList.add(new SortCode(">", 112, "Gt"));
		sortCodeList.add(new SortCode("<=", 113, "LtE"));
		sortCodeList.add(new SortCode(">=", 114, "LtE"));
		sortCodeList.add(new SortCode("<>", 115, "NEt"));
		sortCodeList.add(new SortCode(",", 116, "Dot"));
		sortCodeList.add(new SortCode(";", 117, "Over"));
		sortCodeList.add(new SortCode(":", 118, "Mao"));
		sortCodeList.add(new SortCode("+", 119, "Add"));
		sortCodeList.add(new SortCode("-", 120, "Sub"));
		sortCodeList.add(new SortCode("*", 121, "Mul"));
		sortCodeList.add(new SortCode("/", 122, "Div"));
		sortCodeList.add(new SortCode("=", 123, "Eq"));
		sortCodeList.add(new SortCode("#", 124, "#"));
		sortCodeList.add(new SortCode("/*", 125, "NS"));
		sortCodeList.add(new SortCode("*/", 126, "NE"));
		sortCodeList.add(new SortCode("//", 127, "NL"));
		sortCodeList.add(new SortCode(":=", 128, "VL"));
		sortCodeList.add(new SortCode("(", 129, "Left"));
		sortCodeList.add(new SortCode(")", 130, "Right"));
		sortCodeList.add(new SortCode("ID", 131, "Id"));
		sortCodeList.add(new SortCode("INTEGER_VALUE", 132, "num"));
		sortCodeList.add(new SortCode("STRING_VALUE", 133, "string"));
		sortCodeList.add(new SortCode("FLOAT_VALUE", 134, "num"));
	}
	public static SortCode getSortCodeByEncode(Integer encode) {
		for( int i=0 ; i < sortCodeList.size() ; i++ )
			if(sortCodeList.get(i).encode == encode)
				return sortCodeList.get(i);
		return null;
	}
}
