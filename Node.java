package com.company;

//Класс-узел
class Node {
    public int data;                    //Хранимые данные (ключ)
    public boolean lIsThread;        //Истина, если ссылка на левое поддерево ЯВЛЯЕТСЯ нитью
    public Node left;                   //Ссылка на левое поддерево
    public Node right;                  //Ссылка на правое поддерево

    public Node(int data){              //Конструтор для инициализации
        this.data = data;
        lIsThread = false;
        left  = null;
        right = null;
    }

    //Метод для вывода ключа
    public void show() { 
        System.out.print(data + " "); 
    }
}
