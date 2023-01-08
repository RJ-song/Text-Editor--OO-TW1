package com.company.texteditor.chainofresponsibility;

import com.company.texteditor.state.Context;

public class SavedHandler extends Handler{

    public SavedHandler(Handler next){
        super(next);
    }

    @Override
    public void handleExit(String state) {
        if(state == "com.company.texteditor.state.Unmodified"){
            System.exit(0);
        }
        else{
            toNext(state);
        }
    }
}
