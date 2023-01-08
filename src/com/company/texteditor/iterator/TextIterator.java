package com.company.texteditor.iterator;

public class TextIterator implements Iterator {
    private Textarr textarr;
    private int index;
    public TextIterator(Textarr textarr) {
        this.textarr = textarr;
        this.index = 0;
    }
    public boolean hasNext() {
        if (index < textarr.getLength()) {
            return true;
        } else {
            return false;
        }
    }
    public Object next() {
        Text text = textarr.gettextAt(index);
        index++;
        return text;
    }
}
