package lexer;
import java.io.File;
import java.io.IOException;

public class Test {

	
	public static void main(String[] args) throws IOException {
		// TODO �Զ����ɵķ������
		File file = new File("./PascalCode/test.pas");
		ParseWords p = new ParseWords();
		p.doWork(file);
	
	}

}
