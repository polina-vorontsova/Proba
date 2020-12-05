package com.company;


//Класс-бинарное-дерево
class BinTree {
    private Node root;                              //Корень дерева
    private Node head;                              //Голова дерева
    private final boolean symThreadingDone;         //Истина, когда дерево прошили

    //Конструктор для инициализации
    public BinTree(){
        root = null;
        head = null;
        symThreadingDone = false;
    }

    //Вставка нового ключа в дерево

    public void insert(int data){
        if (symThreadingDone){
            System.out.println("Эта версия вставки более не поддерживается");
        } else {
            Node newNode = new Node(data);//Создаем узел
            if (root == null) { //Корень пустой? Делаем новый узел корневым
                root = newNode;
            } else {
                Node curr = root, prev; //Становимся в начало дерево
                while (true) {
                    //Запоминаем предыдущее положение
                    prev = curr;
                    if (data < curr.data) { //Если то, что мы хотим вставить, меньше того, что находится в текущем узле
                        curr = curr.left; //То идем влево
                        if (curr == null) { //Если нашли место вставки - запоминаем ссылку
                            prev.left = newNode;
                            return;
                        }
                    } else { //Иначе идем вправо
                        curr = curr.right;
                        if (curr == null) { //Нашли место вставки - запоминаем ссылку
                            prev.right = newNode;
                            return;
                        }
                    }
                }
            }
        }
    }

    //Методы для удаления из дерева

    public void deleteFromSymmetricTree(int data){   //Основной метод для удаления. Data - удаляемый ключ
        Node prev = null; //Предыдущий узел
        Node curr  = root;  //Становимся в корень дерева
        boolean found = false; //Пока узел не нашли
        while (curr != null) {  //Покуда не пройдем все дерево
            if (data == curr.data) {  //Если нашли узел
                found = true;
                break;
            }
            prev = curr;     //Запоминаем предыдущего
            if (data < curr.data) { //Идем в левое поддерево
                if (curr.lIsNotThread)
                    curr = curr.left;
                else
                    break;   //Останавливаемся, если ссылка на левое поддерево - НИТЬ
            }
            else {
                if (curr.rIsNotThread)
                    curr = curr.right;
                else
                    break;   //Останавливаемся, если ссылка на правое поддерево - НИТЬ
            }
        }

        if (!found)
            System.out.println("Такого ключа в дереве нет.");   //Такого ключа нет
        else if (curr.lIsNotThread && curr.rIsNotThread)
            deleteNodeWithTwoChildren(prev, curr);//Удаляем узел, у которого два потомка
        else if (curr.lIsNotThread)
            deleteNodeWithOneChild(prev, curr); //Удаляем узел, у которого один потомок (левый)
        else if (curr.rIsNotThread)
            deleteNodeWithOneChild(prev, curr);  //Удаляем узел, у которого один потомок (правый)
        else
            deleteLeaf(prev, curr);  //Удаляем лист
    }

    private void deleteLeaf(Node prev, Node curr){  //Метод для удаления узла-листа
        if (prev == null)                 //Удаляемый узел - корень
            root = null;
        else if (curr == prev.left) {    //Удаляемый узел является левым потомком своего родителя
            prev.lIsNotThread = false;
            prev.left = curr.left;  //Переносим ссылку. Теперь родитель будет ссылаться на узел со следующим меньшим ключом
        } else {
            prev.rIsNotThread = false;
            prev.right = curr.right;    //Переносим ссылку. Теперь родитель будет ссылаться на узел со следующим большим ключом
        }
    }

    private void deleteNodeWithOneChild(Node prev, Node curr){    //Метод для удаления узла с одним потомком
        Node child;
        if (curr.lIsNotThread)
            child = curr.left;   //Потомок у удаляемого узла - левый
        else
            child = curr.right;  //Потомок у удаляемого узла - правый

        if (prev == null)    //Переносим корень дерева на единственного потомка
            root = child;
        else if (curr == prev.left)       //Переносим потомка под ответственность родителю
            prev.left = child;
        else
            prev.right = child;

        Node s = inSuccessor(curr);   //Находим преемника (узел со следующим большим ключом)
        Node p = inPredecessor(curr);   //Находим предшественника (узел со следующим меньшим ключом)

        if (curr.lIsNotThread)     //Меняем ссылки
            p.right = s;
        else {
            if (curr.rIsNotThread)
                s.left = p;
        }
    }

    private void deleteNodeWithTwoChildren(Node prev, Node curr){      //Метод для удаления узла с двумя потомками
        Node prevSucc = curr;         //Запоминаем предыдущего
        Node succ = curr.right;         //Наш преемник

        while (succ.lIsNotThread) {          //Покуда у преемника актуальная ссылка на
            prevSucc = succ;         //левое поддерево, идём влево
            succ = succ.left;
        }
        curr.data = succ.data;                   //Заменяем ключ
        if (!succ.lIsNotThread && !succ.rIsNotThread)         //Преемник - лист. Удаляем его как лист
            deleteLeaf(prevSucc, succ);
        else
            deleteNodeWithOneChild(prevSucc, succ);     //У преемника есть хоть один потомок
    }

    private Node inSuccessor(Node ptr) {    //Метод для поиска преемника
        if (!ptr.rIsNotThread)       //Правая ссылка - это нить. Возвращаем её сразу
            return ptr.right;

        ptr = ptr.right;   //Ищем подходящую ссылку
        while (ptr.lIsNotThread)
            ptr = ptr.left;
        return ptr;
    }

    private Node inPredecessor(Node ptr) {   //Метод для поиска предшественника
        if (!ptr.lIsNotThread)      //Левая ссылка - это нить. Возвращаем её сразу
            return ptr.left;

        ptr = ptr.left;     //Ищем подходящую ссылку
        while (ptr.rIsNotThread)
            ptr = ptr.right;
        return ptr;
    }


    //Метод для вставки ключа в уже симметрично проитое дерево

    public void insertToSymmetricTree(int data) {
        Node curr = root;
        Node prev = null;
        while (curr != null) {
            prev = curr;
            if (data < curr.data) {
                if (curr.lIsNotThread)
                    curr = curr.left;
                else
                    break;
            } else {
                if (curr.rIsNotThread)
                    curr = curr.right;
                else
                    break;
            }
        }

        Node newNode = new Node(data);
        newNode.lIsNotThread = false;
        newNode.rIsNotThread = false;

        if (prev == null) {
            root = newNode;
            newNode.left = null;
            newNode.right = null;
        } else if (data < (prev.data)) {
            newNode.left = prev.left;
            newNode.right = prev;
            prev.lIsNotThread = true;
            prev.left = newNode;
        } else {
            newNode.left = prev;
            newNode.right = prev.right;
            prev.rIsNotThread = true;
            prev.right = newNode;
        }
    }

//Метод для начала прошивания обычного бинарного дерева поиска

    Node y;   //Глобальный указатель
    public void makeSymmetricallyThreaded(){
        if (symThreadingDone){
            System.out.println("Невозможно создать дерево с симметричным прошиванием! Оно уже имеет симметричное прошивание");
        } else {
            head = new Node(-1);
            head.left  = root;
            head.right = head;

            y = head;

            recursiveSymmetricalThreadingR(root);           //Вызываем рекурсивный метод для ПРАВОСТОРОННЕЙ ПРОШИВКИ
            y.right = head;
            y.rIsNotThread = false;                         //Самый крайний правый лист должен ссылаться на голову

            y = head;

            recursiveSymmetricalThreadingL(root);           //Вызываем рекурсивный метод для ЛЕВОСТОРОННЕЙ ПРОШИВКИ
            y.left = head;
            y.lIsNotThread = false;                         //Самый крайний левый лист должен ссылаться на голову
        }
    }

//Методы для ПРАВОСТОРОННЕЙ ПРОШИВКИ
    private void recursiveSymmetricalThreadingR(Node x){
        if (x != null){
            recursiveSymmetricalThreadingR(x.left);
            doSymmetricalThreadingR(x);
            recursiveSymmetricalThreadingR(x.right);
        }
    }

    private void doSymmetricalThreadingR(Node p){
        if (y != null){
            if (y.right == null){
                y.rIsNotThread = false;
                y.right = p;
            } else {
                y.rIsNotThread = true;
            }
        }
        y = p;
    }


  //МЕТОДЫ ДЛЯ ЛЕВОСТОРОННЕЙ ПРОШИВКИ
    private void recursiveSymmetricalThreadingL(Node x){
        if (x != null){
            if (x.rIsNotThread) recursiveSymmetricalThreadingL(x.right);
            doSymmetricalThreadingL(x);
            recursiveSymmetricalThreadingL(x.left);
        }
    }

    private void doSymmetricalThreadingL(Node p){
        if (y != null){
            if (y.left == null){
                y.lIsNotThread = false;
                y.left = p;
            } else {
                y.lIsNotThread = true;
            }
        }
        y = p;
    }


//МЕТОДЫ ДЛЯ ОБХОДА ПРОШИТОГО ДЕРЕВА В ПОРЯДКЕ ВОЗРАСТАНИЯ И УБЫВАНИЯ КЛЮЧЕЙ
    public void threadedInOrderTraverse(){
        Node curr = root;
        if (root == null) {
            System.out.println("Дерево пустое");
            return;
        }
        System.out.println("Симметричный обход дерева при помощи метода прошивки:");
        while (curr != head){
            while (curr.left != null && curr.lIsNotThread) curr = curr.left;
            curr.show();
            while (!curr.rIsNotThread && curr.right != null) {
                curr = curr.right;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.right;
        }
        System.out.println();
    }

    public void threadedInOrderTraverseReverse(){
        Node curr = root;
        if (root == null) {
            System.out.println("Дерево пустое");
            return;
        }
        System.out.println("Симметричный обход дерева при помощи метода прошивки по убыванию ключа:");
        while (curr != head){
            while (curr.right != null && curr.rIsNotThread) curr = curr.right;
            curr.show();
            while (!curr.lIsNotThread) {
                curr = curr.left;
                if (curr == head) {
                    System.out.println();
                    return;
                }
                curr.show();
            }
            curr = curr.left;
        }
        System.out.println();
    }



    //Метод для поиска ключа в дереве
    public Node find(int x){
        //Становимся в корень дерева
        Node curr = root;
        //Покуда не найдем узел с нужным нам ключом
        while (curr.data != x){
            //То, что мы ищем, меньше того, что находится в текущем узле?
            if (x < curr.data)
                //Идем влево
                curr = curr.left;
            else
                //Иначе идем вправо
                curr = curr.right;
            //Дошли до "дна" дерева. Возвращаем null, так как нет узла с таким ключом
            if (curr == null) return null;
        }
        //Мы вышли из цикла. Значит, нужный узел найден. Возвращаем его
        return curr;
    }

    //Вспомогательный метод для вызова симмметричного обхода дерева.
    public void getInOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Симметричный обход дерева:");
        inOrder(root);
        System.out.println();
    }
    //Рекурсивный симметричный обход дерева.
    private void inOrder(Node root){
        if (root != null){
            inOrder(root.left);
            root.show();
            inOrder(root.right);
        }
    }



    //Вспомогательный метод для вызова прямого обхода дерева.
    public void getPreOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Прямой обход дерева:");
        preOrder(root);
        System.out.println();
    }
    //Рекурсивный прямой обход дерева.
    private void preOrder(Node root){
        if (root != null){
            root.show();
            preOrder(root.left);
            preOrder(root.right);
        }
    }
    //Вспомогательный метод для вызова обратного обхода дерева.
    public void getPostOrder(){
        if (root == null) { System.out.println("Дерево пусто!"); return; }
        System.out.println("Обратный обход дерева:");
        postOrder(root);
        System.out.println();
    }


    //Рекурсивный обратный обход дерева.
    private void postOrder(Node root){
        if (root != null){
            postOrder(root.left);
            postOrder(root.right);
            root.show();
        }
    }

}