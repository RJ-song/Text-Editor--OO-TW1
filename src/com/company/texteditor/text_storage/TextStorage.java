package com.company.texteditor.text_storage;

import java.util.HashMap;
import java.util.Map;

public class TextStorage {
    private Map<String, String> textStorage = new HashMap<>();

    public void addText(String key, String value) {
        textStorage.put(key, value);
    }

    public String getText(String key) {
        return textStorage.get(key);
    }
}
