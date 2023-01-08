package com.company.texteditor.memento;

import java.util.HashMap;
import java.util.Map;

public class Caretaker {
    Map<Integer, Memento> memento2Map = new HashMap<>();

    public void addMemento(int curIndex, Memento m){
        memento2Map.put(curIndex, m);
        System.out.println("saved");
    }

    public Memento getMemento(int index){
        return memento2Map.get(index);
    }

    public int getMementoListSize() {
        return memento2Map.size();
    }
}