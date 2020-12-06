package com.company;

class Vertex{
    private final char label;
    private boolean wasVisited; //посещение

    public Vertex(char label){
        this.label = label;
        this.wasVisited = false;
    }

    public char getLabel(){
        return label;
    }

    public boolean isWasVisited(){
        return wasVisited;
    }

    public void setWasVisited(boolean wasVisited){
        this.wasVisited = wasVisited;
    }
}
