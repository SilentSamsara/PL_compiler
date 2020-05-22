package parser.tools;

import lexer.SortCode;
import lexer.SymbolTable;
import parser.models.Grammar;
import parser.models.ItemFamily;
import parser.models.SLRAnalyserTable;

public class SLRAnalyser {
	/**
	 * SLR������
	 * 
	 * ���룺�ķ������λ�ÿ��Դ�����first����Ԥ����������������
	 * 
	 * ���룺�������жϳ���ʷ�����������������������ʱ��ջ�����ݵı仯�ͳ�������
	 * 
	 * ���������ѡ���Ե�������н������First��follow����Ŀ���淶�塢SLRԤ�������ȵȡ�
	 */
	private static Integer search(String key){//�����ؼ�������Ӧ�ı���
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
		G.printAll();// ����ķ���first����follow��
		ItemFamily ItFml = new ItemFamily(G);
		ItFml.print();// �����Ŀ���淶��
		SLRAnalyserTable analyserTable = new SLRAnalyserTable(G, ItFml);
	}
}
