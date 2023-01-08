package com.company.texteditor.strategy_language;

import com.company.texteditor.text_storage.TextStorage;

public class Language {
    protected  LanguageStrategy languageStrategy;

    public void setLanguageStrategy(LanguageStrategy ls) {
        this.languageStrategy = ls;
        this.languageStrategy.setLanguageText();
    }

    public TextStorage getLanguageText() {
        return languageStrategy.getLanguageText();
    }
}
