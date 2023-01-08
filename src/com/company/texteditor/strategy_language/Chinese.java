package com.company.texteditor.strategy_language;

import com.company.texteditor.text_storage.ChineseTextStorage;
import com.company.texteditor.text_storage.TextStorage;

public class Chinese implements LanguageStrategy {
    TextStorage textStorage = new TextStorage();

    public void setLanguageText() {
        ChineseTextStorage chineseTextStorage = new ChineseTextStorage();
        textStorage = chineseTextStorage.getTextStorage();
    }

    @Override
    public TextStorage getLanguageText() {
        return textStorage;
    }
}
