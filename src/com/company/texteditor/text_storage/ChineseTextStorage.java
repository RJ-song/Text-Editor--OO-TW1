package com.company.texteditor.text_storage;

public class ChineseTextStorage extends LanguageTextStorage {

    public ChineseTextStorage() {
        textStorage = new TextStorage();

        textStorage.addText("lan", "ch");
        textStorage.addText("Text Editor", "文字編輯器");
        textStorage.addText("Arial", "Arial");
        textStorage.addText("Font Size:", "文字大小:");
        textStorage.addText("Text Color", "字體顏色");
        textStorage.addText("File", "檔案");
        textStorage.addText("Edit", "編輯");
        textStorage.addText("Glyph", "字型");
        textStorage.addText("Theme", "佈景主題");
        textStorage.addText("Language", "語言");
        textStorage.addText("Help", "幫助");
        textStorage.addText("New", "新增檔案");
        textStorage.addText("Open", "開啟檔案");
        textStorage.addText("Cut", "剪下");
        textStorage.addText("Copy", "複製");
        textStorage.addText("Paste", "貼上");
        textStorage.addText("Undo", "復原");
        textStorage.addText("Redo", "重做");
        textStorage.addText("Find and Replace", "尋找與取代");
        textStorage.addText("Insert Image", "插入圖片");
        textStorage.addText("Select All", "全選");
        textStorage.addText("Bold", "粗體 ");
        textStorage.addText("Italic", "斜體 ");
        textStorage.addText("Underline", "底線 ");
        textStorage.addText("White Theme", "白色主題 ");
        textStorage.addText("Dark Theme", "黑色主題 ");
        textStorage.addText("About", "關於 ");
        textStorage.addText("Choose a color", "選擇顏色");
        textStorage.addText("OK", "確認");
        textStorage.addText("This is a Text Editor.", "這是一個文字編輯器");
        textStorage.addText("Message", "訊息");
        textStorage.addText("English", "英文 ");
        textStorage.addText("Chinese", "中文 ");
        textStorage.addText("High Contrast White", "高對比白色 ");
        textStorage.addText("High Contrast Dark", "高對比黑色 ");
        textStorage.addText("Text Edit Area", "文字編輯區域");
        textStorage.addText("Choose a color", "請選擇一個顏色");
        textStorage.addText("Unmodified", "未修改");
        textStorage.addText("Modified", "已修改");
        textStorage.addText("Save", "儲存舊檔");
        textStorage.addText("SaveTo", "另存新檔");

        // find and replace
        textStorage.addText("Find and Replace", "尋找與取代");
        textStorage.addText("Find", "尋找");
        textStorage.addText("Replace", "取代");
        textStorage.addText("All Replace", "全部取代");
        textStorage.addText("！If you want to find other words, please restart this window！", "！若要搜尋其他文字，請先關閉此視窗再重新開啟一次！");
        textStorage.addText("Old", "被取代文字");
        textStorage.addText("New", "新增檔案");
        textStorage.addText("Logout", "登出");
        textStorage.addText("YES", "是");
        textStorage.addText("NO", "否");
        textStorage.addText("File has not been saved, sure to leave?", "檔案還未儲存，是否要離開?");
        textStorage.addText("Exit", "結束");
    }
}
