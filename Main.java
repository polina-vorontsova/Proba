package com.company;

import java.util.LinkedHashSet;
import java.util.Random;


public class Main {
    public static void main(String[] args){
        Random random = new Random();
        LinkedHashSet<Integer> set = new LinkedHashSet<>();  //LinkedHashSet для исключения дубликатов ключей. Также сохраняет порядок вставки
        BinTree tree = new BinTree(); //Создаем  обычное двоичное дерево поиска
        for (int i = 0; i < 60; i++)   //Создаем 60 ключей и кладем их во множество
            set.add(random.nextInt(100));
        //Проходимся по множеству и каждый его элемент вставляем в дерево двоичного поиска
        System.out.println("Порядок вставки элементов в двоичное дерево поиска: ");
        for (Integer integer : set){
            System.out.print(integer + " ");
            tree.insert(integer);
        }
        System.out.println();

        tree.getInOrder(); //Симметричный обход

        tree.makeSymmetricallyThreaded();//Делаем прошивку

        tree.threadedInOrderTraverse(); //Выводим после прошивки
        tree.threadedInOrderTraverseReverse();

        //Вставка ключей в прошитое
        tree.insertToSymmetricTree(-208);
        tree.insertToSymmetricTree(-923);
        tree.insertToSymmetricTree(-780);
        tree.insertToSymmetricTree(570);
        tree.insertToSymmetricTree(-50);
        tree.insertToSymmetricTree(-36);
        tree.insertToSymmetricTree(614);
        tree.insertToSymmetricTree(-97);


        System.out.println("После вставки уже новых ключей:");
        tree.threadedInOrderTraverse();

        tree.deleteFromSymmetricTree(1458); //Попытки удалить несуществующие ключи
        tree.deleteFromSymmetricTree(-8699);
        tree.deleteFromSymmetricTree(-2470);

        //Удаляем первую половину того, что вставили
        int N = set.size() / 2;
        for (Integer integer: set){
            tree.deleteFromSymmetricTree(integer);
            N--;
            if (N == 0) break;
        }

        //После удаления
        System.out.println("Наше дерево после удаления элементов");
        tree.threadedInOrderTraverse();
    }
}
