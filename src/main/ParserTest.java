package main;


import parser.models.Grammar;
import parser.tree.Node;

public class ParserTest {

	public static void main(String[] args) {
		new SLRParser();
		for (int i = 0 ; i < Node.error.size() ; i++ )
			System.out.println(Node.error.get(i));
	}
}