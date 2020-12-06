package com.company;

public class Main {

    public static void main(String[] args) {
        Graph theGraph = new Graph();
        theGraph.addVertex('A'); // 0
        theGraph.addVertex('B'); // 1
        theGraph.addVertex('C'); // 2
        theGraph.addVertex('D'); // 3
        theGraph.addVertex('E'); // 4
        //добавление ребер
        theGraph.addEdge(0, 1, 1); // AB
        theGraph.addEdge(1, 2, 2); // BC
        theGraph.addEdge(2, 4, 4); // CE
        theGraph.addEdge(2, 3, 2); // CD
        theGraph.addEdge(4, 3, 5); // ED
        theGraph.addEdge(3, 1, 1); // DB
        theGraph.addEdge(3, 2, 3); // DC
        theGraph.findMinWays();
        System.out.print("Минимальный путь от А до D: ");
        System.out.println(theGraph.getMinDestination('A', 'D'));
        System.out.print("Центр нашего графа: ");
        System.out.println(theGraph.getCenter());
        System.out.println("Все пути от C до D");
        theGraph.showAllWays('C', 'D');
        System.out.print("Самый длинный путь от C до D: ");
        theGraph.showLongestWay('C', 'D');
        System.out.println("Матрица минимальных путей:");
        theGraph.showMinWaysMatrix();
    }
}
