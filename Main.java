package com.company;

import java.util.LinkedHashSet;
import java.util.Random;

public class Main {
    public static void main(String[] args){
        Random rand = new Random();//генерация случайных чисел
        //LinkedHashSet для исключения дубликатов ключей.
        // Сохраняет порядок вставки
        LinkedHashSet<Integer> set = new LinkedHashSet<Integer>();

        BinTree tree = new BinTree();//Создаем двоичное дерево


        for (int i = 0; i < 50; i++) //Создаем 100 ключей и кладем их во множество
            set.add(rand.nextInt(60));
        //Проходимся по множеству и каждый элемент вставляем в дерево двоичного поиска
        System.out.println("\nПорядок вставки элементов: ");
        for (Integer integer : set){
            System.out.print(integer  + " ");
            tree.insert(integer );
        }
        System.out.println();

        tree.getInOrder();  //Симметричный обход
        tree.getPreOrder(); //Прямой обход
        tree.getPostOrder(); //Обратный обход

        //Берем половину первых элементов вставленных и удаляем её из дерева
        int k = set.size() / 2;
        for (Integer integer : set){
            tree.delete(integer);
            k--;
            if (k == 0)
                break;
        }

        //Делаем ещё раз симметричный обход
        System.out.println("\n\nПосле удаления половины элементов");
        tree.getInOrder();

        //Демонстрация поиска элементов в дереве
        // (поскольку первая половина удалена, то первая половина попыток поиска безуспешна)
        System.out.println("\nИщем элементы в дереве:");
        for (Integer integer : set){
            if(tree.find(integer) != null){
                System.out.println(integer + " found!");
            }else{
                System.out.println(integer + " not found!");
            }
        }
    }
}
