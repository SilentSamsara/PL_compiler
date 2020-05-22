package parser.models;

import java.util.*;

import parser.tools.SRException;

public class SLRAnalyserTable {
	public static List<List<Lattice>> ACTION = new ArrayList<List<Lattice>>();
	public static List<List<Lattice>> GOTO = new ArrayList<List<Lattice>>();

	public SLRAnalyserTable() {

	}

	public List<List<Lattice>> getACTION() {
		return ACTION;
	}

	public void setACTION(List<List<Lattice>> aCTION) {
		ACTION = aCTION;
	}

	public List<List<Lattice>> getGOTO() {
		return GOTO;
	}

	public void setGOTO(List<List<Lattice>> gOTO) {
		GOTO = gOTO;
	}

	public SLRAnalyserTable(Grammar G, ItemFamily itemFamily) {
		try {
			createSLRAnalyserTable(G, itemFamily);
		} catch (SRException e) {
			System.out.println(e.error);
			System.exit(0);
		}
	}

	public void createSLRAnalyserTable(Grammar G, ItemFamily itemFamily) throws SRException {
		// �Ȱ��ս���ͷ��ս������һ��
		List<String> non_terminal = G.getNon_terminals();
		List<String> terminal = G.getTerminals();
		List<Follow> follows = G.getFollows();
		terminal.add("$");
		// Ϊaction���goto�����ռ�
		for (int i = 0; i < itemFamily.itemsets.size(); i++) {
			// Ϊaction�����ռ�
			List<Lattice> list1 = new ArrayList<Lattice>();
			for (int j = 0; j < terminal.size(); j++) {
				Lattice lattice = new Lattice();
				list1.add(lattice);
			}
			// Ϊgoto�����ռ�
			List<Lattice> list2 = new ArrayList<Lattice>();
			for (int j = 0; j < non_terminal.size(); j++) {
				Lattice lattice = new Lattice();
				list2.add(lattice);
			}
			ACTION.add(list1);
			GOTO.add(list2);
		}
		// ����SLR������
		for (int i = 0; i < itemFamily.itemsets.size(); i++) {
			for (int j = 0; j < itemFamily.itemsets.get(i).items.size(); j++) {
				Item item = itemFamily.itemsets.get(i).items.get(j);
				if (item.point < item.rightParts.size()) {// �㲻�ڲ���ʽ�Ҳ������һ��λ�ã�����E->E��+T
					if (terminal.contains(item.rightParts.get(item.point))) {// ������������ս������������E->E��+T
						int location = terminal.indexOf(item.rightParts.get(item.point));// ��location��ʾ�������ս�����ս�������е�λ��
						if (ACTION.get(i).get(location).action != 'r' && ACTION.get(i).get(location).action != 's') {
							ACTION.get(i).get(location).action = 's';
						} else if (ACTION.get(i).get(location).action == 'r') {
//							throw new SRException(1);
							//Pascalר�ù��򣬲������޸�
							if ((i == 44 || i == 45)) {
								if((terminal.get(location).equals("*")
									||terminal.get(location).equals("/")
									||terminal.get(location).equals("^"))) {
								ACTION.get(i).get(location).action='s';
								ACTION.get(i).get(location).number=33;
								}else if (terminal.get(location).equals("+")) {
									ACTION.get(i).get(location).action='r';
									ACTION.get(i).get(location).number=18;
								}else if(terminal.get(location).equals("-")) {
									ACTION.get(i).get(location).action='r';
									ACTION.get(i).get(location).number=19;
								}
							}
							if ( i == 46 || i == 47 ) {
								if(terminal.get(location).equals("^")) {
									ACTION.get(i).get(location).action='s';
									ACTION.get(i).get(location).number=33;
									}else if (terminal.get(location).equals("*")) {
										ACTION.get(i).get(location).action='r';
										ACTION.get(i).get(location).number=20;
									}else if(terminal.get(location).equals("/")) {
										ACTION.get(i).get(location).action='r';
										ACTION.get(i).get(location).number=21;
									}
							}
							if(terminal.get(location).equals("^")) {
								ACTION.get(i).get(location).action='s';
								ACTION.get(i).get(location).number=33;
							}else if (terminal.get(location).equals(".")) {
								ACTION.get(i).get(location).action='s';
								ACTION.get(i).get(location).number=2;
							}else if (terminal.get(location).equals("else")) {
								ACTION.get(i).get(location).action='s';
								ACTION.get(i).get(location).number=4;
								ACTION.get(5).get(5).action ='r';
								ACTION.get(5).get(5).number = 13;
								ACTION.get(7).get(24).action = 'r';
								ACTION.get(7).get(24).number = 2;
								ACTION.get(5).get(Grammar.non_terminals.indexOf("compoundstmt")).action = 'r';
								ACTION.get(5).get(Grammar.non_terminals.indexOf("compoundstmt")).number = 7;
								ACTION.get(0).get(2).action = 'r';
								ACTION.get(0).get(2).number = 13;
							}
							continue;
						}
						for (int k = 1; k < itemFamily.itemsets.size(); k++) {
							Item k1item = itemFamily.itemsets.get(k).items.get(0);
							Item ijitem = itemFamily.itemsets.get(i).items.get(j);
							if (k1item.productionEquals(ijitem) && k1item.point == ijitem.point + 1) {
								if(ACTION.get(i).get(location).error!=0) {
									ACTION.get(i).get(location).Action.number = k;
									System.out.println(k);
								}
								else
									ACTION.get(i).get(location).number = k;
								break;
							}
						}
					} else {// ����������ַ��Ƿ��ս��������E->E+��T
						int location = non_terminal.indexOf(item.rightParts.get(item.point));// ��location��ʾ�����ķ��ս���ڷ��ս�������е�λ��
						for (int k = 1; k < itemFamily.itemsets.size(); k++) {
							Item k1item = itemFamily.itemsets.get(k).items.get(0);
							Item ijitem = itemFamily.itemsets.get(i).items.get(j);
							if (k1item.productionEquals(ijitem) && k1item.point == ijitem.point + 1) {
								GOTO.get(i).get(location).number = k;
								break;
							}
						}
					}
				} else if (item.point == item.rightParts.size()) {// ���ڲ���ʽ�Ҳ������һ��λ�ã�����E->E+T��
					if (item.leftPart.equals("START")) { // �������START�ع��ķ�������action���ж�Ӧλ�ü���acc����Ϊʹ��char���ַ����鱣��ģ�������ﱣ��a
						int location = terminal.indexOf("$");
						ACTION.get(i).get(location).action = 'a';
					} else {
						Follow follow = follows.get(non_terminal.indexOf(item.leftPart));// E->T�� ��
						for (int k = 0; k < follow.getfollow().size(); k++) {
							int location = terminal.indexOf(follow.getfollow().get(k));
							if (ACTION.get(i).get(location).action != 'r'
									&& ACTION.get(i).get(location).action != 's') {
								ACTION.get(i).get(location).action = 'r';
								ACTION.get(i).get(location).number = item.number;
							} else if (ACTION.get(i).get(location).action == 's') {
								throw new SRException(1);
							} else {
								throw new SRException(2);
							}
						}
					}
				}
			}
		}
//		GOTO.get(4).get(Grammar.non_terminals.indexOf("compoundstmt")).number = 8;
		// ���SLRԤ�������
		SLRAnalyserTable.display_Table();
	}
	public static void display_Table() {
		System.out.println("\nԤ�������");
		for (int i = 0; i < Grammar.terminals.size(); i++) {
			System.out.print("\t\t" + Grammar.terminals.get(i));
		}
		for (int i = 0; i < Grammar.non_terminals.size(); i++) {
			System.out.print("\t\t" + Grammar.non_terminals.get(i));
		}
		System.out.println();
		for (int i = 0; i < ACTION.size(); i++) {
			System.out.print(i);
			for (int j = 0; j < ACTION.get(i).size(); j++) {
				System.out.print("\t\t" + ACTION.get(i).get(j).action + ACTION.get(i).get(j).number);
				if (ACTION.get(i).get(j).error!=0) {
					System.out.print("/"+ACTION.get(i).get(j).Action.action+ACTION.get(i).get(j).Action.number);
				}
			}
			for (int j = 0; j < GOTO.get(i).size(); j++) {
				System.out.print("\t\t" + GOTO.get(i).get(j).number);
			}
			System.out.println();
		}
	}
	public void print() {

	}
}
