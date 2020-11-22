package com.company;

class Node {

    public int data;        //Хранимые данные
    public Node left;       //Ссылка на левое поддерево
    public Node right;      //Cсылка на правое поддерево

    public Node(int data){
        this.data = data;
        left = null;
        right = null;
    }

    //Вывод ключа
    public void show(){
        System.out.print(data + " ");
    }
}
