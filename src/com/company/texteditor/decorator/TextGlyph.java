package com.company.texteditor.decorator;

public class TextGlyph extends Glyph{

    public TextGlyph() {

        description = "TextFont";
    }

    public String getDescription() {
            return this.description;
        }

    @Override
    public void changeStyle() {

    }

}
