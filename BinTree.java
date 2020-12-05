package com.company;

import java.util.Stack;

//Класс - бинарное дерево
class BinTree {
    private Node root; //Корень дерева

    //Конструктор для инициализации
    public BinTree(){
        root = null;
    }

    //Вставка нового ключа в дерево
    public void insert(int data){
        Node newNode = new Node(data);  //Создаем узел
        
        if (root == null) { //Корень пустой? Делаем новый узел корнем
            root = newNode;
        } else {
            Node curr = root, prev; //Иначе становимся в начало дерево
            while (true) {
                prev = curr; //Запоминаем предыдущее положение
                if (data < curr.data) {  //Если то, что мы хотим вставить, меньше того, что находится в текущем узле
                    curr = curr.left;  //То идем влево
                    if (curr == null) { 
                        prev.left = newNode; //Нашли место вставки - запоминаем ссылку
                        return;
                    }
                } else {
                    curr = curr.right;  //Иначе идем вправо
                    if (curr == null) {  //Нашли место вставки - запоминаем ссылку
                        prev.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    public void insertToThreadedBinaryTree(int data){
        getThreadingUpdated(root);
        insert(data);
        doThreading();
    }

    public void doTraversing() {
        Node curr = root;
        while (curr != null){
            System.out.print(curr.data + "-->");
            if (curr.left != null && !curr.lIsThread) 
                curr = curr.left;
            else if (curr.left == null)
                curr = curr.right;
            else if (curr.lIsThread)
                curr = curr.left;
        }
        System.out.println("ВСЁ ПРОЙДЕНО");
    }

    public void doThreading(){
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            Node extraction = stack.pop();
            if (extraction.right != null) stack.push(extraction.right);
            if (extraction.left  != null && !extraction.lIsThread) stack.push(extraction.left);
            if (stack.empty()) return;
            if (extraction.right == null && (extraction.lIsThread || extraction.left == null)){
                if (stack.peek() == root){
                    extraction.left = null;
                    extraction.lIsThread = false;
                } else {
                    extraction.left = stack.peek();
                    extraction.lIsThread = true;
                }
            }
        }
    }

    private void getThreadingUpdated(Node n){
        if (n != null){
            if (n.lIsThread){
                n.lIsThread = false;
                n.left = null;
            }
            getThreadingUpdated(n.left);
            getThreadingUpdated(n.right);
        }
    }

    //Вспомогательный метод для поиска преемника узлу delNode.
    //Преемник - либо (1) правый потомок delNode
    // либо (2) "самый левый" потомок правого потомка delNode
    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;  //Становимся в правое поддерево удаляемого узла
        Node successor = delNode;
        Node current = delNode.right;
        while(current != null) { //Находим "самого левого"
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        //Если "самый левый" не является (1), а является (2), то
        //правый потомком преемника должно стать правое поддерево delNode
        if(successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }
        return successor; //Возвращаем преемника
    }


    //Метод для удаления некоторого ключа из дерева.
    public boolean delete(int key) {
        Node current = root; //Становимся в начало дерева
        Node parent = root;
        boolean isLeftChild = true; //Пока предполагаем, что удаляемый узел будет являться левым потомком узла - родителя
        //По пути запоминаем родителя
        //и обновляем, является ли найденный удаляемый узел левым потомком либо нет
        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            //То, что просили удалить в дереве, не обнаружено,
            if (current == null)
                return false;
        }
        
        //Удаляемый узел все-таки найден.
        
        //Случай, когда удаляемый узел - лист
        if (current.left == null &&
                current.right == null) {
            if (current == root)
                root = null;     //Удаляемый узел - корень (обнулив его)
            else if (isLeftChild)
                parent.left = null;//Удаляемый узел - левый потомок своего родителя. Обнуляем левую ссылку родителя
            else
                parent.right = null; //Удаляемый узел - правый потомок своего родителя. Обнуляем правую ссылку родителя
        }
        else
            //Случай, когда у удаляемого узла есть только левый потомок
            if (current.right == null) {
                if (current == root)
                    root = current.left;   //Удаляемый узел - корень. Правого поддерева нет. Просто перезапоминаем корень.
                else
                if (isLeftChild)    //Удаляемый узел - не корень. При этом удаляемый узел - левый потомок. Правого поддерева нет.
                    parent.left = current.left;   //Исключаем удаляемый узел из цепочки. Теперь родитель будет ссылаться на нового левого потомка.
                else
                    parent.right = current.left; //Исключаем удаляемый узел из цепочки. Теперь родитель будет ссылаться на нового правого потомка.
            }
            else
                //Случай, когда у удаляемого узла есть только правый потомок
                if (current.left == null) {
                    if (current == root)
                        root = current.right;
                    else
                    if (isLeftChild)
                        parent.left = current.right;
                    else
                        parent.right = current.right;
                }
                else
                //Случай, когда у удаляемого узла есть и левый, и правфй потомок
                {
                    Node successor = getSuccessor(current); //Находим преемника ( узел со следующим по величине ключом ) 
                    if (current == root)
                        root = successor;  //Если удаляемый узел - корень, то перезапоминаем его
                    else if (isLeftChild)
                        parent.left = successor; //Удаляемый узел - левый потомок. Заменяем его преемником.
                    else
                        parent.right = successor; //Аналогично в случае правого потомка.

                    successor.left = current.left;  //Переносим левое поддерево удаляемого узла.
                }
        return true; //Сообщаем об удачном удалении
    }
    
    public boolean delFromThredBTree(int key){
        getThreadingUpdated(root);
        boolean res = delete(key);
        doThreading();
        return res;
    }

}
