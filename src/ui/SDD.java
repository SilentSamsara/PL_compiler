package ui;

public class SDD {
    static String[] productions = {"S->program id;\ncompoundstmt .", "compoundstmt->begin stmts end", "stmts->stmt",
            "stmts->stmts;\nstmt", "stmt->id := expr", "stmt->compoundstmt", "stmt->ifstmt", "stmt->forstmt",
            "stmt->while bool do stmt1", "stmt->~", "ifstmt->if bool then stmt", "ifstmt->if bool then stmt1 else stmt2",
            "forstmt->for id := expr1  to  expr2  do  stmt", "forstmt->for id := expr1  downto  expr2  do  stmt",
            "bool->expr1 > expr2", "bool->expr1 < expr2", "expr->expr1 + expr2", "expr->expr1 - expr2",
            "expr->expr1 * expr2", "expr->expr1 / expr2", "expr->expr1 ^ factor", "expr->factor", "factor->id",
            "factor->num", "factor->( expr )"};


    static String[] rules = { "S.code = compoundstmt,code", "compoundstmt.code = stmts.code", "stmts.code = stmt.code",
        "stmts.code = stmts.code || stmt.code", "stmt.code = expr.code || gen(id.place':='expr.place)", "stmt.code = compoundstmt.code",
            "stmt.code = ifstmt.code", "stmt.code = forstmt.code",
            "stmt.begin = new Label();\nbool.true = new Lable();\nbool.false = stmt.next;\nstmt1.next = stmt.begin;\nstmt.code = gen(stmt.begin':') || bool.code || gen(bool.true':') || stmt1.code || gen('goto'stmt.begin);\n",
            "stmt.code = gen(' ')",  "bool.true = new label();\nbool.false = ifstmt.next;\nstmt.next = ifstmt.next;\nifstmt.code = bool.code || gen(bool.true) || stmt.code",
            "bool.true = new label();\nbool.false = new label();\nstmt1.next = stmt2.next = ifstmt.next;\nifstmt = bool.code || gen(bool.true':') || stmt1.code || gen('goto'ifstmt.next) || stmt2.code",
            "forstmts.begin = new label() ;\nforstmt.true = new label() ;\nforstmt.false = forstmt.next ;\nforstmt.tem =new temp() ;\nforstmt.tem = i + 1;\nforstmt.code = expr1.code || expr2.code || forstmt.begin || gen('if'id.place'<'expr2.place'goto'forstmt.true) || gen('goto'forstmt.false) || gen(forstmt.true':') || stmt.code || gen(id.place':='forstmt.tem) || gen(id.place'') ||  gen('goto'forstmt.begin)",
            "forstmts.begin = new label() ;\nforstmt.true = new label() ;\nforstmt.false = forstmt.next ;\nforstmt.tem =new temp() ;\nforstmt.tem = i + 1;\nforstmt.code = expr1.code || expr2.code || forstmt.begin || gen('if'id.place'<'expr2.place'goto'forstmt.true) || gen('goto'forstmt.false) || gen(forstmt.true':') || stmt.code || gen(id.place':='forstmt.tem) || gen(id.place'') ||  gen('goto'forstmt.begin)",
            "bool.code = expr1.code || expr2.code || gen('if' expr1.place'>'expr2.place'goto'bool.true) || gen('goto'bool.false)",
            "bool.code = expr1.code || expr2.code || gen('if' expr1.place'<'expr2.place'goto'bool.true) || gen('goto'bool.false)",
            "expr.place = new temp();\nexpr.code = expr1.code  || expr2.code || gen(expr.place':='expr1.place'+'expr2.place)",
            "expr.place = new temp();\nexpr.code = expr1.code  || expr2.code || gen(expr.place':='expr1.place'-'expr2.place)",
            "expr.place = new temp();\nexpr.code = expr1.code  || expr2.code || gen(expr.place':='expr1.place'*'expr2.place)",
            "expr.place = new temp();\nexpr.code = expr1.code  || expr2.code || gen(expr.place':='expr1.place'/'expr2.place)",
            "expr.place = new temp();\nexpr.code = expr1.code  || expr2.code || gen(expr.place':='expr1.place'^'expr2.place)",
            "expr.code = factor.code",
            "factor = id.name",
            "factor = num.value",
            "factor .place = expr.place;\nfactor.code = expr.code"
};

}
