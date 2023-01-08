package com.company.texteditor.text_storage;

import javax.swing.*;
import java.awt.*;

public class EnglishTextStorage extends LanguageTextStorage {

    public EnglishTextStorage() {
        textStorage = new TextStorage();

        textStorage.addText("lan", "en");
        textStorage.addText("Text Editor", "Text Editor");
        textStorage.addText("Arial", "Arial");
        textStorage.addText("Font Size:", "Font Size:");
        textStorage.addText("Text Color", "Text Color");
        textStorage.addText("File", "File");
        textStorage.addText("Edit", "Edit");
        textStorage.addText("Glyph", "Glyph");
        textStorage.addText("Theme", "Theme");
        textStorage.addText("Language", "Language");
        textStorage.addText("Help", "Help");
        textStorage.addText("New", "New");
        textStorage.addText("Open", "Open");
        textStorage.addText("Save To", "Save To");
        textStorage.addText("Cut", "Cut");
        textStorage.addText("Copy", "Copy");
        textStorage.addText("Paste", "Paste");
        textStorage.addText("Undo", "Undo");
        textStorage.addText("Find and Replace", "Find and Replace ");
        textStorage.addText("Select All", "Select All ");
        textStorage.addText("Insert Image", "Insert Image");
        textStorage.addText("Undo", "Undo");
        textStorage.addText("Redo", "Redo");
        textStorage.addText("Bold", "Bold");
        textStorage.addText("Italic", "Italic");
        textStorage.addText("Underline", "Underline ");
        textStorage.addText("White Theme", "White Theme ");
        textStorage.addText("Dark Theme", "Dark Theme ");
        textStorage.addText("About", "About ");
        textStorage.addText("Choose a color", "Choose a color");
        textStorage.addText("OK", "OK");
        textStorage.addText("This is a Text Editor.", "This is a Text Editor.");
        textStorage.addText("Message", "Message");
        textStorage.addText("English", "English ");
        textStorage.addText("Chinese", "Chinese ");
        textStorage.addText("High Contrast White", "High Contrast White ");
        textStorage.addText("High Contrast Dark", "High Contrast Dark ");
        textStorage.addText("Text Edit Area", "Text Edit Area");
        textStorage.addText("Choose a color", "Choose a color");
        textStorage.addText("Unmodified", "Unmodified");
        textStorage.addText("Modified", "Modified");
        textStorage.addText("Save", "Save");
        textStorage.addText("SaveTo", "Save To");
        // find and replace
        textStorage.addText("Find and Replace", "Find and Replace");
        textStorage.addText("Find", "Find");
        textStorage.addText("Replace", "Replace");
        textStorage.addText("All Replace", "All Replace");
        textStorage.addText("！If you want to find other words, please restart this window！", "！If you want to find other words, please restart this window！");
        textStorage.addText("Old", "Old");
        textStorage.addText("New", "New");
        textStorage.addText("Logout", "Logout");
        textStorage.addText("YES", "YES");
        textStorage.addText("NO", "NO");
        textStorage.addText("File has not been saved, sure to leave?", "File has not been saved, sure to leave?");
        textStorage.addText("Exit", "Exit");
    }
}
