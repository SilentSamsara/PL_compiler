package parser.tree;

import parser.models.Grammar;

import java.util.*;

public class Node {
    public static HashMap<String,String> idTable = new HashMap<>();
    public static List<String> num = new ArrayList<String>();
    public static int temp = 0;
    public static int label = 0;
    public static ArrayList<String> error =new ArrayList<String>() ;
    String place = "";
    String begin = "" , bt = "" , bf = "" , next = "";
    public int productionNum;
    public String key;
    public Integer encode;
    public Integer line;
    public ArrayList<Node> children;
    public Node(int x){
        this.productionNum = x;
        children = new ArrayList<Node>();
    }
    String temp(){
        temp++;
        return "t"+(temp-1);
    }
    String label(){
        label++;
        return "L"+(label-1);
    }
    public String getCode(Node now){
        String code = "";
        int produce =now.productionNum;
        if (produce == 1)
            //S->program id;  compoundstmt .
            code = getCode(now.children.get(3));
        else if (produce == 2)
            //compoundstmt->begin stmts end
            code = getCode(now.children.get(1));
        else if (produce == 3)
            //stmts->stmt
            code = getCode(now.children.get(0));
        else if (produce == 4){
            //stmts->stmts; stmt
            code = getCode(now.children.get(0));
            code = code + "\n" + getCode(now.children.get(2));
        }else if (produce == 5){
            //stmt->id := expr
            code = getCode(now.children.get(2)) + now.children.get(0).key + " = " + now.children.get(2).place;
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place+"可能未初始化. line: " + now.children.get(2).line ) ;
            idTable.put(now.children.get(0).key,now.children.get(2).place);
        }else if (produce == 6 || produce == 7 || produce == 8){
            //stmt->compoundstmt
            //stmt->ifstmt
            //stmt->forstmt
            code = getCode(now.children.get(0));
        }else if (produce == 9){
            //stmt->while bool do stmt1
            begin = label();
            code = begin + ":\n" + getCode(now.children.get(1)) + now.children.get(1).bt + ":\n" + getCode(now.children.get(3))
                    + "\ngoto " + begin + "\n" + now.children.get(1).bf + ":";
        }else if (produce == 11){
            //ifstmt->if bool then stmt
            code = getCode(now.children.get(1)) + now.children.get(1).bt + ":\n" + getCode(now.children.get(3)) + "\n"
                    + now.children.get(1).bf + ":\n";
        }else if (produce == 12){
            //ifstmt->if bool then stmt1 else stmt2
            code = getCode(now.children.get(1)) + now.children.get(1).bt + ":\n" + getCode(now.children.get(3))+ "\ngoto ";
            next = label();
            code = code + next + "\n" + now.children.get(1).bf + ":\n"
                    +getCode(now.children.get(5)) + "\n" + next +":\n";
        }else if (produce == 13){
            //forstmt->for id := expr1  to  expr2  do  stmt
            code = getCode(now.children.get(3)) + getCode(now.children.get(5));
            now.begin = label();
            now.bt = label();
            now.bf =label();
            System.out.println(code);
            code = code + now.children.get(1).key + " = " + now.children.get(3).place + "\n"
                    + now.begin + ":\nif " + now.children.get(1).key + " <= " + now.children.get(5).place
                    + " goto " + now.bt + "\ngoto " + now.bf
                    + "\n" + now.bt + ":\n" + getCode(now.children.get(7)) ;
            String t = temp();
            code = code + t + " = "+ now.children.get(1).key +" + 1\n"+ now.children.get(1).key +" = " + t
                    +  "\ngoto " + now.begin + "\n" + now.bf + ":\n";
            if (num.indexOf(now.children.get(3).place) == -1 && idTable.get(now.children.get(3).place) == null)
                error.add(now.children.get(3).place + "可能未初始化. line: " + now.children.get(3).line);
            idTable.put(now.children.get(1).key,now.children.get(3).place);
            idTable.put(t,now.children.get(1).key +" + 1");
        }else if (produce == 14){
            //forstmt->for id := expr1  downto  expr2  do  stmt
            code = getCode(now.children.get(3)) + getCode(now.children.get(5)) ;
            now.begin = label();
            now.bt = label();
            now.bf =label();
            code = code + now.children.get(1).key + " = " + now.children.get(3).place + "\n"
                    + now.begin + ":\nif " + now.children.get(1).key + " >= " + now.children.get(5).place + " goto " + now.bt + "\ngoto " + now.bf
                    + "\n" + now.bt + ":\n" + getCode(now.children.get(7)) ;
            String t = temp();
            code = code + t + " = "+ now.children.get(1).key +" - 1\n"+ now.children.get(1).key +"= " + t
                    +  "\ngoto " + now.begin + "\n" + now.bf + ":\n";
            if (num.indexOf(now.children.get(3).place) == -1 && idTable.get(now.children.get(3).place) == null)
                error.add(now.children.get(3).place + "可能未初始化. line: " + now.children.get(3).line);
            idTable.put(now.children.get(1).key,now.children.get(3).place);
            idTable.put(t,now.children.get(1).key +" + 1");
        }else if (produce == 15){
            //bool->expr1 > expr2
            now.bt = label();
            now.bf = label();
            code = getCode(now.children.get(0)) + getCode(now.children.get(2)) + "if " + now.children.get(0).place + " > "
                    + now.children.get(2).place + " goto " + now.bt + "\ngoto " + now.bf + "\n";
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line);
        }else if (produce == 16){
            //bool->expr1 < expr2
            now.bt = label();
            now.bf = label();
            code = getCode(now.children.get(0)) + getCode(now.children.get(2)) + "if " + now.children.get(0).place + " < "
                    + now.children.get(2).place + " goto " + now.bt + "\ngoto " + now.bf + "\n";
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line);
        }else if (produce == 17){
            //expr->expr1 + expr2
            code = getCode(now.children.get(0)) + getCode(now.children.get(2)) ;
            now.place = temp();
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line);
            code = code + now.place + " = " + now.children.get(0).place + " + " + now.children.get(2).place + "\n";
            idTable.put(now.place,now.children.get(0).place + " + " + now.children.get(2).place);
        }else if (produce == 18){
            //expr->expr1 - expr2
            code = getCode(now.children.get(0)) + getCode(now.children.get(2)) ;
            now.place = temp();
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line);
            code = code + now.place + " = " + now.children.get(0).place + " - " + now.children.get(2).place + "\n";
            idTable.put(now.place,now.children.get(0).place + " + " + now.children.get(2).place);
        }else if (produce == 19){
            //expr->expr1 * expr2
            code = getCode(now.children.get(0)) + getCode(now.children.get(2)) ;
            now.place = temp();
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line);
            code = code + now.place + " = " + now.children.get(0).place + " * " + now.children.get(2).place + "\n";
            idTable.put(now.place,now.children.get(0).place + " + " + now.children.get(2).place);
        }else if (produce == 20){
            //expr->expr1 / expr2
            code = getCode(now.children.get(0)) + getCode(now.children.get(2)) ;
            now.place = temp();
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line);
            code = code + now.place + " = " + now.children.get(0).place + " / " + now.children.get(2).place + "\n";
            idTable.put(now.place,now.children.get(0).place + " + " + now.children.get(2).place);
        }else if (produce == 21){
            //expr->expr1 ^ factor
            code = getCode(now.children.get(0)) ;
            now.place = temp();
            if (num.indexOf(now.children.get(0).place) == -1 && idTable.get(now.children.get(0).place) == null)
                error.add(now.children.get(0).place + "可能未初始化. line: " + now.children.get(0).line);
            code = code + now.place + " = " + now.children.get(0).place + " ^ " + getCode(now.children.get(2)) + "\n";
            if (num.indexOf(now.children.get(2).place) == -1 && idTable.get(now.children.get(2).place) == null)
                error.add(now.children.get(2).place + "可能未初始化. line: " + now.children.get(2).line + " ss");
            idTable.put(now.place,now.children.get(0).place + " + " + now.children.get(2).place);
        }else if (produce == 22){
            //expr->factor
            if (now.children.get(0).productionNum == 25){
                line = now.children.get(0).line;
                code = now.getCode(now.children.get(0));
                now.place = now.children.get(0).place;
            }else {
                line = now.children.get(0).line;
                now.place = now.getCode(now.children.get(0));
            }
        }else if (produce == 23){
            //factor->id
            line = now.children.get(0).line;
            code = now.children.get(0).key;
        }else if (produce == 24){
            //factor->num
            line = now.children.get(0).line;
            code = now.children.get(0).key;
            now.place = now.children.get(0).key;
            num.add(code);
        }else if (produce == 25){
            //factor->( expr )
            code = getCode(now.children.get(1));
            now.place = now.children.get(1).place;
        }
        return code;
    }

    public void display(int x){
        for (int i=0;i<x;i++)
            System.out.print("\t");
        if (productionNum!=-1) {
            System.out.println("L "+ Grammar.productions.get(productionNum).getLeftPart());
        }
        else{
            System.out.println("L "+key);
        }
        for (int i=0;i<children.size();i++){
            int y = x+1;
            children.get(i).display(y);
        }
    }
}
