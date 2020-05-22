package lexer;

import java.util.ArrayList;

public class SymbolTable {
	public static ArrayList<SortCode> sortCodeList = new ArrayList<>();
    public static ArrayList<WordItem> wordItemList = new ArrayList<>();//“—∆•≈‰œÓƒø
	public static void initLoadSortCodeList(){
		sortCodeList.add(new SortCode("FOR", 91, "function"));
		sortCodeList.add(new SortCode("TO", 92, "function"));
		sortCodeList.add(new SortCode("DOWNTO", 93, "function"));
		sortCodeList.add(new SortCode("READ", 94, "function"));
		sortCodeList.add(new SortCode("READLN", 95, "function"));
		sortCodeList.add(new SortCode("WRITE", 96, "function"));
		sortCodeList.add(new SortCode("WRITELN", 97, "function"));
		sortCodeList.add(new SortCode("STRING", 98, "string"));
		sortCodeList.add(new SortCode("BEGIN", 99, "Start"));
		sortCodeList.add(new SortCode("END", 100, "End"));
		sortCodeList.add(new SortCode("IF", 101, "If"));
		sortCodeList.add(new SortCode("THEN", 102, "Then"));
		sortCodeList.add(new SortCode("ELSE", 103, "Else"));
		sortCodeList.add(new SortCode("WHILE", 104, "While"));
		sortCodeList.add(new SortCode("DO", 105, "Do"));
		sortCodeList.add(new SortCode("PROGRAM", 106, "PROGRAM"));
		sortCodeList.add(new SortCode("INTEGER", 107, "INTEGER"));
		sortCodeList.add(new SortCode("VAR", 108, "Var"));
		sortCodeList.add(new SortCode("<", 109, "Lt"));
		sortCodeList.add(new SortCode(">", 110, "Gt"));
		sortCodeList.add(new SortCode("<=", 111, "LtE"));
		sortCodeList.add(new SortCode(">=", 112, "LtE"));
		sortCodeList.add(new SortCode("<>", 113, "NEt"));
		sortCodeList.add(new SortCode(",", 114, "Dot"));
		sortCodeList.add(new SortCode(";", 115, "Over"));
		sortCodeList.add(new SortCode(".", 116, "point"));
		sortCodeList.add(new SortCode(":", 117, "Mao"));
		sortCodeList.add(new SortCode("^", 118, "index"));
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
		sortCodeList.add(new SortCode("$", 131, "P_end"));
		sortCodeList.add(new SortCode("ID", 132, "Id"));
		sortCodeList.add(new SortCode("INTEGER_VALUE", 133, "num"));
		sortCodeList.add(new SortCode("STRING_VALUE", 134, "string"));
		sortCodeList.add(new SortCode("FLOAT_VALUE", 135, "num"));
	}
	public static SortCode getSortCodeByEncode(Integer encode) {
		for( int i=0 ; i < sortCodeList.size() ; i++ )
			if(sortCodeList.get(i).encode == encode)
				return sortCodeList.get(i);
		return null;
	}
}
