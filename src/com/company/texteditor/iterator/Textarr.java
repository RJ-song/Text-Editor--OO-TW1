package com.company.texteditor.iterator;

public class Textarr implements Aggregate{
    public Text[] text;
    private int last = 0;
    public Textarr(int maxsize) {
        this.text = new Text[maxsize];
    }
    public Text gettextAt(int index) {
        return text[index];
    }
    public void appendtext(Text text) {
        this.text[last] = text;
        last++;
    }
    public int getLength() {
        return last;
    }
    public Iterator iterator() {
        return new TextIterator(this);
    }
}
