package com.company.texteditor.strategy_language;

import com.company.texteditor.text_storage.EnglishTextStorage;
import com.company.texteditor.text_storage.TextStorage;

public class English implements LanguageStrategy {

    TextStorage textStorage = new TextStorage();

    @Override
    public void setLanguageText() {
        EnglishTextStorage englishTextStorage = new EnglishTextStorage();
        textStorage = englishTextStorage.getTextStorage();
    }

    @Override
    public TextStorage getLanguageText() {
        return textStorage;
    }
}
