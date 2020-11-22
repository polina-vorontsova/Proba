package com.company;

//Класс- бинарное дерево
class BinTree {
    Node root;  //Корень дерева

    public BinTree(){
        root = null;
    }

    //Вставка нового ключа в дерево
    public void insert(int data){
        //Создаем узел
        Node newNode = new Node(data);

        //Корень пустой? Значит делаем новый узел корневым
        if (root == null){
            root = newNode;
        } else {
            Node curr = root, prev; //Становимся в начало дерево
            while (true){
                prev = curr; //Запоминаем предыдущее положение
                //Если то, что хотим вставить, меньше того, что находится в текущем узле
                if (data < curr.data){ //То идем влево
                    curr = curr.left;
                    if (curr == null){ //Нашли место вставки - запоминаем ссылку
                        prev.left = newNode;
                        return;
                    }
                } else { //Иначе идем вправо
                    curr = curr.right;
                    if (curr == null){//Нашли место вставки - запоминаем ссылку
                        prev.right = newNode;
                        return;
                    }
                }
            }
        }
    }

    //Метод для поиска ключа в дереве
    public Node find(int x){
        Node curr = root; //Становимся в корень дерева
        while (curr.data != x){  //Пока не найдем узел с нужным нам ключом
            if (x < curr.data) //То, что мы ищем, меньше того, что находится в текущем узле?
                curr = curr.left; //Идем влево
            else
                curr = curr.right;  //Иначе - вправо
            if (curr == null) //Если дошли до "дна" нашего дерева.
                return null; // Возвращаем null, так как нет узла с таким ключом
        }
        //Вышли из цикла. -> Значит, нужный узел найден.
        return curr; // Возвращаем его
    }

    //Вспомогательный метод для вызова симмметричного обхода дерева.
    public void getInOrder(){
        if (root == null) {
            System.out.println("Дерево пустое."); return;
        }
        System.out.println("Симметричный обход дерева:");
        inOrder(root);
        System.out.println();
    }

    //Вспомогательный метод для вызова прямого обхода дерева.
    public void getPreOrder(){
        if (root == null) {
            System.out.println("Дерево пустое."); return;
        }
        System.out.println("Прямой обход дерева:");
        preOrder(root);
        System.out.println();
    }

    //Вспомогательный метод для вызова обратного обхода дерева.
    public void getPostOrder(){
        if (root == null) {
            System.out.println("Дерево пустое."); return;
        }
        System.out.println("Обратный обход дерева:");
        postOrder(root);
        System.out.println();
    }

    //Рекурсивный симметричный обход дерева
    private void inOrder(Node root){
        if (root != null){
            inOrder(root.left);
            root.show();
            inOrder(root.right);
        }
    }

    //Рекурсивный прямой обход дерева
    private void preOrder(Node root){
        if (root != null){
            root.show();
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    //Рекурсивный обратный обход дерева
    private void postOrder(Node root){
        if (root != null){
            postOrder(root.left);
            postOrder(root.right);
            root.show();
        }
    }

    //Вспомогательный метод для поиска преемника удалеяемому узлу .
    //Преемник - либо (1) правый потомок delNode
    //           либо (2) "самый левый" потомок правого потомка delNode

    private Node getSuccessor(Node delNode) {
        //Становимся в правое поддерево удаляемого узла
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.right;
        //Находим "самого левого"
        while(current != null) {
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
        //Возвращаем преемника
        return successor;
    }

    //Метод для удаления некоторого ключа из дерева.
    public boolean delete(int key) {
        //Становимся в начало дерева
        Node current = root;
        Node parent = root;
        //Пока предполагаем, что удаляемый узел будет являться
        //левым потомком узла-родителя
        boolean isLeftChild = true;
        //По пути запоминаем родителя
        //и обновляем, является ли найденный удаляемый узел левым
        // потомком либо нет
        while (current.data != key) {
            parent = current;
            if (key < current.data) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            //То, что просили удалить в дереве, не обнаружено, уведомляем
            //о неуспешном удалении
            if (current == null)
                return false;
        } //Удаляемый узел все-таки найден.

        //Рассматриваем случай, когда удаляемый узел - лист
        if (current.left == null &&
                current.right == null) {
            if (current == root)
                root = null;                //Удаляемый узел - корень. Просто обнуляем его
            else if (isLeftChild)
                parent.left = null;         //Удаляемый узел - левый потомок своего родителя. Обнуляем левую ссылку родителя
            else
                parent.right = null;        //Удаляемый узел - правый потомок своего родителя. Обнуляем правую ссылку родителя
        }
        else
            //Рассматриваем случай, когда у удаляемого узла есть только левый потомок
            if (current.right == null) {
                if (current == root)
                    root = current.left;//Удаляемый узел - корень. Правого поддерева нет. Просто перезапоминаем корень.
                else
                if (isLeftChild)   //Удаляемый узел - не корень. При этом удаляемый узел - левый потомок. Правого поддерева нет.
                    parent.left = current.left;   //Исключаем удаляемый узел из цепочки. Теперь родитель будет ссылаться на нового левого потомка.
                else
                    parent.right = current.left; //Исключаем удаляемый узел из цепочки. Теперь родитель будет ссылаться на нового правого потомка.
            }
            else
                //Рассматриваем случай, когда у удаляемого узла есть только правый потомок
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
                //Рассматриваем случай, когда у удаляемого узла есть и левый, и правый потомок
                {
                    //Находим преемника. Преемник - это узел со следующим по величине ключом.
                    Node successor = getSuccessor(current);
                    if (current == root)
                        root = successor;               //Если удаляемый узел - корень, то перезапоминаем его
                    else if (isLeftChild)
                        parent.left = successor;        //Удаляемый узел - левый потомок. Заменяем его преемником.
                    else
                        parent.right = successor;       //Аналогично в случае правого потомка.

                    successor.left = current.left;      //Переносим левое поддерево удаляемого узла.
                }
        //Сообщаем об удачном удалении
        return true;
    }
}
