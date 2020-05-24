package ui;

import lexer.ErrorWriter;
import lexer.SymbolTable;
import parser.models.Grammar;
import parser.models.SLRAnalyserTable;
import parser.tools.SLRAnalyser;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Listeners implements WindowListener {
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
            new SymbolStream(lexer.jta_morpheme);
            lexer.hide();
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
