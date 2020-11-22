package com.company;

class Stack {

    private Node top; //Ссылка на вершину стека

    public Stack(){
        top = null;
    }
    //Проверка на пустоту
    public boolean isEmpty() {
        return top == null;
    }
    //Вставляем элемент в стек

    public void push(Node el){
        el.next = top;
        top = el;
    }
    //Очищаем вершину стека
    public Node pop(){
        Node toReturn = top;
        top = top.next;
        return toReturn;
    }

    //Смотрим вершину стека, но не удаляем её
    public char peek(){
        if (top == null) return '~';
        return top.getOperation();
    }
}

