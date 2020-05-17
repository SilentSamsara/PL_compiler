package lexer;

public class WordItem{
	public String key;//�ؼ���
	public Integer encode;//����
	public Integer line;//�к�
	public WordItem(String key, Integer encode, Integer line){
		this.key = key;
		this.encode = encode;
		this.line = line;
	}
    @Override
	public String toString() {
    	if(encode<131)
    		return "<"+key+">";
    	else
    		return "<"+SymbolTable.getSortCodeByEncode(encode).symbol+","+key+">";
	}
}