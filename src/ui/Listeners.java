package ui;

import lexer.ErrorWriter;
import lexer.SymbolTable;
import parser.models.Grammar;
import parser.models.SLRAnalyserTable;
import parser.tools.SLRAnalyser;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Listeners extends WindowAdapter {
    static Launcher launcher;
    static Lexer lexer;

    public static class Cleaner {
        public static void cleanAll(){
            Grammar.productions.clear();
            Grammar.non_terminals.clear();
            Grammar.firsts.clear();
            Grammar.follows.clear();
            Grammar.terminals.clear();
            SLRAnalyserTable.ACTION.clear();
            SLRAnalyserTable.GOTO.clear();
            SymbolTable.wordItemList.clear();
            SymbolTable.sortCodeList.clear();
            ErrorWriter.errorList.clear();
        }

    }
    @Override
    public void windowOpened(WindowEvent e) {
        if (e.getSource().equals(lexer)){
            lexer.getUnit();
            JOptionPane.showMessageDialog(null,"获取记号流成功！");
            Launcher.frames.add(new SymbolStream(lexer.jta_morpheme));
            lexer.hide();
        }else if (e.getSource().equals(launcher)){  //获得焦点
            launcher.jta_code.requestFocusInWindow();
        }
    }

}
