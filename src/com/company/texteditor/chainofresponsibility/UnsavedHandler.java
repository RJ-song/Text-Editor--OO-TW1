package com.company.texteditor.chainofresponsibility;
import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.state.Context;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;
import java.awt.*;

public class UnsavedHandler extends Handler implements Observer {
    private LanguageSubject languageSubject;
    private TextStorage textStorage;

    public UnsavedHandler(Handler next, LanguageSubject languageSubject){
        super(next);
        this.languageSubject = languageSubject;
        textStorage = languageSubject.getTextStorage();
    }

    @Override
    public void handleExit(String state) {

        Object[] checkButton = {textStorage.getText("YES"),textStorage.getText("NO")};
        Object defaultCheckButton = checkButton[0];
        ImageIcon icon = new ImageIcon("src/llama.png");
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);// transform it back

        if(state == "com.company.texteditor.state.Modified"){


            int result=JOptionPane.showOptionDialog(null,
                    textStorage.getText("File has not been saved, sure to leave?"),textStorage.getText("Exit"),
                    JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,icon,checkButton,defaultCheckButton);

            if( result == JOptionPane.YES_OPTION){
                System.exit(0);
            }
            else if( result == JOptionPane.NO_OPTION){

            }

        }
        else{
            toNext(state);
        }
    }

    @Override
    public void update() {
        textStorage = languageSubject.getTextStorage();
    }
}
