package com.company;

import java.util.LinkedHashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random random = new Random();
        //LinkedHashSet для исключения дубликатов ключей (сохраняет порядок вставки)
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        BinTree tree = new BinTree(); //Создаем обычное двоичное дерево поиска
        
        //Создаем 20 ключей и кладем их во множество
        for (int i = 0; i < 20; i++)
            set.add(random.nextInt(10));
        //Проходимся по множеству и каждый элемент вставляем в дерево двоичного поиска
        System.out.println("Порядок вставки элементов в двоичное дерево : ");
        for (Integer integer : set){
            System.out.print(integer + " ");
            tree.insert(integer);
        }
        System.out.println();
        
        tree.doThreading(); //Делаем прошивку

        System.out.println("Проход по прошитому дереву в прямом порядке:");
        tree.doTraversing();

        //Делаем вставку элементов в прошитое дерево
        tree.insertToThreadedBinaryTree(67);
        tree.insertToThreadedBinaryTree(87);
        tree.insertToThreadedBinaryTree(-3);
        tree.insertToThreadedBinaryTree(97);
        tree.insertToThreadedBinaryTree(-15);

        System.out.println("Проход по прошитому дереву в прямом порядке после вставки:");
        tree.doTraversing();

        //Удаляем примерно первую половину вставленных элементов
        int K = set.size() / 2;
        for (Integer integer : set){
            tree.delFromThredBTree(integer);
            K--;
            if (K == 0)
                break;
        }

        System.out.println("Проход по прошитому дереву в прямом порядке (после удаления):");
        tree.doTraversing();
    }
}
