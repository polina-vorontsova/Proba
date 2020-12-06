package com.company;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class Graph{
    private static final int MAXVERTEXES = 20;
    private final Vertex[] vertexList; //узлы графа
    private final int[][] adjMatrix; // матрица переходов (длины путей)
    private final int[][] minWayMatrix;
    private int nVerts; //кол-во вершин
    private Stack<Integer> stack; //

    public Graph(){
        vertexList = new Vertex[MAXVERTEXES];
        adjMatrix = new int[MAXVERTEXES][MAXVERTEXES];
        minWayMatrix = new int[MAXVERTEXES][MAXVERTEXES];
        nVerts = 0;
        for (int j = 0; j < MAXVERTEXES; j++){
            for (int k = 0; k < MAXVERTEXES; k++){
                adjMatrix[j][k] = 0;
                minWayMatrix[j][k] = 0;
            }
        }
        stack = new Stack<>();
    }

    private int getVertexNumber(char label){ // если есть вершина, то вернуть позицию (номер)
        for (int i = 0; i < nVerts; i++){
            if (label == vertexList[i].getLabel()){
                return i;
            }
        }
        return -1;
    }

    private int findMinDestination(char start, char end){ //возвращает минимальное расстояние между 2-мя вершинами
        int first = getVertexNumber(start);
        int second = getVertexNumber(end);
        if (first == -1 || second == -1){
            return -1;//если не находит хотя бы 1 из вершин
        } else {
            return minWayMatrix[first][second];
        }
    }

    public String getMinDestination(char start, char end){
        int answer = findMinDestination(start, end);
        if (answer == -1){
            return "Error";
        } else if (answer == Integer.MAX_VALUE){
            return "Inf";
        } else {
            return Integer.toString(answer);
        }
    }

    public void addVertex(char lab){ //добавление вершины в граф
        vertexList[nVerts++] = new Vertex(lab);
    }

    public void addEdge(int start, int end, int destination){  //добавление ребра графа
        adjMatrix[start][end] = destination;
    }

    public int getnVerts(){ //кол-во вершин
        return nVerts;
    }

    private HashSet<String> inDeep(int first, int second) { //метод обхода в глубину для нахождения мин. путей
        HashSet<String> list = new HashSet<>();
        String destination = Character.toString(vertexList[first].getLabel());
        vertexList[first].setWasVisited(true);
        stack.push(first);
        while(!stack.isEmpty()){
            int v = getAdjUnvisitedVertex(stack.peek());
            if (v == -1){
                stack.pop();
                destination = destination.substring(0, destination.length()-1);
            } else if (v == second){
                destination += vertexList[v].getLabel();
                list.add(destination);
                vertexList[v].setWasVisited(true);
                destination = destination.substring(0, destination.length()-1);
                int v2 = getAdjUnvisitedVertex(stack.peek());
                if (v2 == -1){
                    stack.pop();
                    destination = destination.substring(0, destination.length()-1);
                    vertexList[v].setWasVisited(false);
                } else {
                    vertexList[v].setWasVisited(false);
                    vertexList[v2].setWasVisited(true);
                    stack.push(v2);
                    destination += vertexList[v2].getLabel();
                }
            }else{
                vertexList[v].setWasVisited(true);
                stack.push(v); // Занесение в стек
                destination += vertexList[v].getLabel();
            }
        }
        for(int j=0; j<nVerts; j++)
            vertexList[j].setWasVisited(false);
        return list; //множество String(-ов)
    }

    public int getAdjUnvisitedVertex(int v){ //возвращает смежную вершину с текущей, в которой еще не были
        for (int j = 0; j < nVerts; j++)
            if (adjMatrix[v][j] != 0 && !vertexList[j].isWasVisited())
                return j;
        return -1;
    }

    private int getLengthOfWay(String str){ //возвращает длину пути
        int destination = 0;
        for (int i = 1; i < str.length(); i++){
            destination += adjMatrix[getVertexNumber(str.charAt(i-1))][getVertexNumber(str.charAt(i))];
        }
        return destination;
    }

    private HashMap<String, Integer> getAllWays(char start, char end){
        HashSet<String> set = inDeep(getVertexNumber(start), getVertexNumber(end));
        HashMap<String, Integer> map = new HashMap<>(); // (путь и длина)
        for (String str : set){
            map.put(str, getLengthOfWay(str));
        }
        return map;
    }

    public void showAllWays(char start, char end){ //Выводим все пути
        HashMap<String,Integer> map = getAllWays(start, end);
        for (Map.Entry<String, Integer> x: map.entrySet()){
            System.out.printf("%s %d\n", x.getKey(), x.getValue());
        }
    }

    public void showLongestWay(char start, char end){ //Выводим самый длинный путь
        HashMap<String,Integer> map = getAllWays(start, end);
        int max = -1;
        String way = "";
        for (Map.Entry<String, Integer> x: map.entrySet()){
            if (x.getValue() > max){
                max = x.getValue();
                way = x.getKey();
            }
        }
        System.out.printf("%s %d\n", way, max);
    }

    public int[] dijkstraAlgorithm(int cur) { //Алгоритм Дейкстры для поиска мин. пути между 2 вершинами
        int[] distance = new int[nVerts];
        for(int i = 0; i < nVerts; i++){
            distance[i] = Integer.MAX_VALUE;
        }
        distance[cur] = 0;
        while(cur != -1){
            for (int i = 0; i < nVerts; i++)
                if ((adjMatrix[cur][i] + distance[cur] < distance[i]) && adjMatrix[cur][i] != 0)
                    distance[i] = adjMatrix[cur][i] + distance[cur];
            vertexList[cur].setWasVisited(true);
            cur = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < nVerts; i++){
                if (!vertexList[i].isWasVisited() && (distance[i] < minDistance)){
                    cur = i;
                    minDistance = distance[i];
                }
            }
        }
        for (int i = 0; i < nVerts; i++){
            vertexList[i].setWasVisited(false);
        }
        return distance;
    }

    public void findMinWays(){ //поиск мин. путей
        int[] array;
        for (int i = 0; i < nVerts; i++){
            array = dijkstraAlgorithm(i);
            System.arraycopy(array, 0, minWayMatrix[i], 0, nVerts);
        }
    }

    private int findMaxInColon(int j){ //поиска уентра графа
        int max = minWayMatrix[0][j];
        for (int i = 1; i < nVerts; i++){
            if (minWayMatrix[i][j] > max){
                max = minWayMatrix[i][j];
            }
        }
        return max;
    }

    private int calculateCenter(){  // Вычисление центра
        int min = findMaxInColon(0);
        int numbOfVertex = 0;
        for (int i = 1; i < nVerts; i++){
            int current = findMaxInColon(i);
            if (min > current){
                min = current;
                numbOfVertex = i;
            }
        }
        return numbOfVertex;
    }

    public char getCenter(){ //возвращаем центр
        return vertexList[calculateCenter()].getLabel();
    }

    public void showMinWaysMatrix(){ //Выводим матрицу мин. переходов
        for (int i = 0; i < this.getnVerts(); i++){
            for (int j = 0; j < this.getnVerts(); j++){
                if (minWayMatrix[i][j] == Integer.MAX_VALUE){
                    System.out.print("Inf");
                } else {
                    System.out.printf("%3d", minWayMatrix[i][j]);
                }
            }
            System.out.println();
        }
    }
}
