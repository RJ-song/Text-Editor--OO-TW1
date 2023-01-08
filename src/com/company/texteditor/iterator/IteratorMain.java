package com.company.texteditor.iterator;

import com.company.texteditor.observer.LanguageSubject;

import javax.swing.*;
public class IteratorMain {
    private static String a;

    private static JTextPane textpane;

    private static Iterator it;
    private static Textarr textarr;
    private static JFrame jf;

    public static void setJP(JFrame jf, JTextPane JP, LanguageSubject languageSubject) {
        jf = jf;
        textpane = JP;
        a = textpane.getText();
        textarr = new Textarr(a.length());
        for(int i = 0;i<a.length();i++) {
            textarr.appendtext(new Text(a.substring(i,i+1)));
        }
        it = textarr.iterator();
        New_f nef = new New_f();
        nef.new_ff(jf,textarr,languageSubject);
    }

    public static void select(JFrame jf,int first,int count) {
        jf.requestFocus();
        textpane.requestFocusInWindow();
        textpane.setCaretPosition(first);
        textpane.moveCaretPosition(first + count);
    }
    public static void repleace(int first,int count,int index,String b) {
        a = textpane.getText();
        textarr = new Textarr(a.length()+count);
        for(int i = 0;i<first;i++){
            try{
                textarr.appendtext(new Text(a.substring(i,i+1)));

            }
            catch (StringIndexOutOfBoundsException e){
                break;
            }
        }
        for(int i = 0;i<count;i++){
            try{
                textarr.appendtext(new Text(b.substring(i,i+1)));

            }
            catch (StringIndexOutOfBoundsException e){
                break;
            }
        }
        for(int i = first + index;i<a.length();i++){
            textarr.appendtext(new Text(a.substring(i,i+1)));
        }
        String text = "";
        it = textarr.iterator();
        while(it.hasNext()){
            Text t = (Text)it.next();
            text = text + t.gettext();
        }
        textpane.setText(text);
    }
}
