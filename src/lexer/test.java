package lexer;
import java.io.File;
import java.io.IOException;

public class test {

	
	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
		File file = new File("./PascalCode/test.pas");
		ParseWords p = new ParseWords();
		p.doWork(file);
		
//		int x = 133;
//		Integer y = x;
//		System.out.println(x == y);
		
		
//		String xString = "0016";
//		System.out.println(xString.substring(2));
		
	}

}
