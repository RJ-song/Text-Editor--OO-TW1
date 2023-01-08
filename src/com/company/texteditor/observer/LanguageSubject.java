package com.company.texteditor.observer;

import com.company.texteditor.strategy_language.English;
import com.company.texteditor.strategy_language.Language;
import com.company.texteditor.text_storage.TextStorage;

import java.util.ArrayList;
import java.util.List;

public class LanguageSubject implements Subject {
    private List<Observer> observers;

    private Language language;
    private TextStorage textStorage;

    public LanguageSubject() {
        observers = new ArrayList<Observer>();

        language = new Language();
        language.setLanguageStrategy(new English());
        textStorage = language.getLanguageText();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    public void changeLanguage(Language language) {
        this.language = language;
        this.textStorage = language.getLanguageText();
    }

    public void setTextStorage(TextStorage textStorage) {
        this.textStorage = textStorage;
    }

    public TextStorage getTextStorage() {
        return textStorage;
    }
}
