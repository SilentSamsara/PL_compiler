package parser.models;

/**
 * 预测分析表中，一个格子的类
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
