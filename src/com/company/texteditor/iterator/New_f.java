package com.company.texteditor.iterator;

import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class New_f implements Observer {
    public static Textarr arr;
    private int index;
    private int[] first;
    private int first_index = 0;

    private boolean bool = true;
    private int sum = 0;
    private int max;

    private Textarr textarr;

    private String text_test = "";

    private LanguageSubject languageSubject;
    private TextStorage textStorage;

    private JFrame jframe;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JLabel label;
    private JTextArea textArea;
    private JTextArea textArea2;

    public void new_ff(JFrame jf,Textarr arr, LanguageSubject languageSubject) {
        languageSubject.registerObserver(this);
        this.languageSubject = languageSubject;
        textStorage = languageSubject.getTextStorage();

        jframe = new JFrame();
        jframe.setTitle(textStorage.getText("Find and Replace"));
        jframe.setSize(500, 300);
        jframe.setResizable(false);

        // set icon
        ImageIcon image = new ImageIcon("llama.png");
        jframe.setIconImage(image.getImage());//top left image of the frame

        JPanel panel = new JPanel();
        textArea = new JTextArea(10, 20);
        textArea2 = new JTextArea(10, 20);
        textArea.setLineWrap(true);

        TitledBorder tb = BorderFactory.createTitledBorder(textStorage.getText("Old"));
        tb.setTitlePosition(TitledBorder.ABOVE_TOP);
        textArea.setBorder(tb);

        TitledBorder tb2 = BorderFactory.createTitledBorder(textStorage.getText("New"));
        tb2.setTitlePosition(TitledBorder.ABOVE_TOP);
        textArea2.setBorder(tb2);

        panel.add(textArea);
        panel.add(textArea2);

        jframe.setContentPane(panel);
        jframe.setVisible(true);

        this.arr = arr;
        first = new int[arr.getLength()];
        Iterator it = arr.iterator();

        bool = true;

        btn1 = new JButton();
        btn1.setText(textStorage.getText("Find"));
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bool){
                    find(it,textArea);
                    bool = false;
                }
                if(first_index <= max){
                    IteratorMain.select(jf,first[first_index],index);
                    first_index++;
                }
                else{
                    first_index = 0;
                    IteratorMain.select(jf,first[first_index],index);
                    first_index++;
                }

            }
        });
        panel.add(btn1);

        btn2 = new JButton();
        btn2.setText(textStorage.getText("Replace"));
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bool){
                    find(it,textArea);
                }
                text_test = textArea2.getText();
                int index1 = index;
                index = text_test.length();
                jframe.dispose();
                if(first_index!=0){
                    IteratorMain.repleace(first[first_index-1],index,index1,text_test);
                }
                else{
                    IteratorMain.repleace(first[first_index],index,index1,text_test);
                }

            }
        });
        panel.add(btn2);
        btn3 = new JButton();
        btn3.setText(textStorage.getText("All Replace"));
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean o = true;
                if(bool){
                    find(it,textArea);
                }
                text_test = textArea2.getText();
                int index1 = index;
                index = text_test.length();
                jframe.dispose();
                for(int i = 0;i<=max;i++){
                    if(!o){
                        IteratorMain.repleace(first[i]+i*(index-index1),index,index1,text_test);
                    }
                    else{
                        IteratorMain.repleace(first[i],index,index1,text_test);
                        o = false;
                    }

                }
            }
        });
        panel.add(btn3);
        label = new JLabel();
        label.setText(textStorage.getText("！If you want to find other words, please restart this window！"));
        panel.add(label);
    }
    public void find(Iterator it, JTextArea textArea){
        int ind = 0;
        it = arr.iterator();
        String test = textArea.getText();
        index = textArea.getText().length();
        textarr = new Textarr(test.length());
        for(int i = 0;i<test.length();i++){
            textarr.appendtext(new Text(test.substring(i,i+1)));
        }
        Iterator tarr = textarr.iterator();
        text_test = "";
        Text t = (Text)tarr.next();
        while (it.hasNext()) {
            Text text = (Text)it.next();
            if(text.gettext().equals(t.gettext()) && tarr.hasNext()){
                text_test = text_test + t.gettext();
                t = (Text) tarr.next();
            }
            else if(text_test.equals(textArea.getText())){
                first[ind] = sum - index;
                text_test = "";
                tarr = textarr.iterator();
                t = (Text) tarr.next();
                ind++;

            }
            else{
                if(text.gettext().equals(t.gettext())){
                    text_test = "";
                    first[ind] = sum - index+1;
                    tarr = textarr.iterator();
                    t = (Text) tarr.next();
                    ind++;


                }
                else{
                    text_test = "";
                    tarr = textarr.iterator();
                    t = (Text) tarr.next();
                    if(text.gettext().equals(t.gettext())){
                        text_test = text_test + t.gettext();
                        t = (Text) tarr.next();
                    }
                }
            }
            sum++;

        }
        max = ind-1;
        /*if(!(text_test.equals(textArea.getText()))){
            text_test = "";
            first = 0;
        }*/
    }

    @Override
    public void update() {
        textStorage = languageSubject.getTextStorage();
        jframe.setTitle(textStorage.getText("Find and Replace"));
        btn1.setText(textStorage.getText("Find"));
        btn2.setText(textStorage.getText("Replace"));
        btn3.setText(textStorage.getText("All Replace"));
        label.setText(textStorage.getText("！If you want to find other words, please restart this window！"));
        TitledBorder tb = BorderFactory.createTitledBorder(textStorage.getText("Old"));
        tb.setTitlePosition(TitledBorder.ABOVE_TOP);
        textArea.setBorder(tb);

        TitledBorder tb2 = BorderFactory.createTitledBorder(textStorage.getText("New"));
        tb2.setTitlePosition(TitledBorder.ABOVE_TOP);
        textArea2.setBorder(tb2);
    }
}


