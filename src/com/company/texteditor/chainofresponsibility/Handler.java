package com.company.texteditor.chainofresponsibility;
import com.company.texteditor.state.Context;
public abstract class Handler {
    protected Handler next;

    public abstract void handleExit(String state);

    public Handler(Handler next){
        this.next=next;
    }
    public void toNext(String state){
        if(next!=null){
            next.handleExit(state);
        }
    }
}


