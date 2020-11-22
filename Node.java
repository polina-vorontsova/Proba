package com.company;

//Класс-узел для хранения пары: операция-приоритет

class Node {

    private final char operation;//Операция
    private final int priority;//Приоритет
    public Node next;//Ссылка на следующий элемент стека

    public Node(char operation, int priority){
        this.operation = operation;
        this.priority = priority;
        next = null;
    }

    public char getOperation(){
        return operation;
    }
}