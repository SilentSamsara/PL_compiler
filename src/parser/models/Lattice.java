package parser.models;

/**
 * Ԥ��������У�һ�����ӵ���
 */
public class Lattice {
	char action;
	int number;
	int error = 0;
	Lattice Action = null;
	public char getAction() {
		return action;
	}

	public void setAction(char action) {
		this.action = action;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Lattice() {

	}
}
