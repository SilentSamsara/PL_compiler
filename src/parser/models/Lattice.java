package parser.models;

/**
 * Ԥ��������У�һ�����ӵ���
 */
public class Lattice {
	public char action;
	public int number;
	public int error = 0;
	public Lattice Action = null;
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
