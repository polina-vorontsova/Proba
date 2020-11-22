package com.company;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Класс INfix to Reversed-Polish-Notation

class RevPolNot {

    //Строка для хранения изначального выражения в ИНФИКСНОЙ форме
    private String infix;
    //Строка для хранения результирующей ПОСТФИКСНОЙ форме
    private String rPostfix;
    private Stack stack; //Стек для выполнения преобразований

    public RevPolNot(String s) throws  RuntimeException{ //Конструктор класса
        checkCorrectness(s); //Проверяем корректность введеного инфиксного выражения
        infix = s;         //Запоминаем инфиксное выражение
        rPostfix = "";     //Пока результирующую строку делаем пустой
        makeRPN(); //Выполняем преобразования
    }


    private void makeRPN(){ //Метод для преобразования в ПОСТФИКСНУЮ форму

        stack = new Stack(); //Создаем пустой стек
        for(int i = 0; i < infix.length(); i++) { //Посимвольно проходимся по строке (инфиксное выражение)
            char ch = infix.charAt(i);

            switch(ch)
            {
                //Плюс или минус? Обрабатываем с приоритетом 2
                case '+':
                case '-':
                    processOperation(ch, 2);
                    break;
                //Умножение или деление? Обрабатываем с приоритетом 3
                case '*':
                case '/':
                    processOperation(ch, 3);
                    break;
                //Возведение в степень? Обрабатываем с приоритетом 4
                case '^':
                    processOperation(ch, 4);
                    break;
                //Открывающая скобка? Просто кладём её в стек с приоритетом 0
                case '(':
                    stack.push(new Node('(', 0));
                    break;
                //Закрывающая скобка? Обрабатываем с приоритетом 1
                case ')':
                    processBracket(ch, 1);
                    break;
                //Операнд? Сразу присоединяем к ответу
                default:
                    rPostfix = rPostfix + ch;
                    break;
            }
        }
        //Дописываем то, что осталось в стеке операций
        while(!stack.isEmpty() )
            rPostfix = rPostfix + stack.pop().getOperation();

    }

    //Метод для обработки бинарного оператора
    private void processOperation(char op, int prior){
        //Покуда стек не пуст
        while(!stack.isEmpty() ) {
            //Очищаем вершину стека. Смотрим, какая операция там была
            char opTop = stack.pop().getOperation();
            if(opTop == '(') {
                //Была открывающаяся скобка? Кладём её обратно
                stack.push(new Node(opTop, 0));
                break;
            }
            else {
                //Переменая для приоритета операции, находившейся в верхушке стека
                int prior2;
                //Плюс или минус? Приоритет 2.
                if (opTop == '+' || opTop == '-')
                    prior2 = 2;
                    //Умножить или делить? Приоритет 3
                else if (opTop == '*' || opTop == '/')
                    prior2 = 3;
                    //Возведение в степень? Приоритет 4
                else
                    prior2 = 4;
                //Приоритет нового оператора меньше старого?
                if(prior2 < prior) {
                    //Кладем в стек новый оператор
                    stack.push(new Node(opTop, prior2));
                    break;
                }
                else
                    //Сразу присоединяем оператор к ответу
                    rPostfix = rPostfix + opTop;
            }
        }
        //Кладем оператор в стек операторов
        stack.push(new Node(op, prior));
    }

    //Метод для обработки закрывающейся скобки
    private void processBracket(char op, int prior){

        while( !stack.isEmpty() ) { //Покуда стек не пуст

            char chx = stack.pop().getOperation(); //Извлекаем текующий вершинку стека (символ)
            if(chx == '(') //Прекращаем выполнение
                break;
            else
                rPostfix = rPostfix + chx; //Приписываем к ответу
        }
    }

    //Возвращаем полученную обратную польскую нотацию
    public String getReversedPolishNotation(){
        return rPostfix;
    }

    //Метод для проверки инфиксного выражения
    private void checkCorrectness(String s){
        if (s.length() < 3) throw new RuntimeException("SHORT");        //Короткая строка? Ошибка
        if (s.contains(" ")) throw new RuntimeException("WH_SPACE");    //Есть пробелы? Ошибка

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        if (m.find()) throw new RuntimeException("NUM");                //Есть цифры? Ошибка

        char[] arr = s.toCharArray();                                   //Переводим в массив char[]
        final String operands = "+-*/^";                                //Строка с разрешимыми операторами
        Stack brStack = new Stack();                                    //Создаем пустой стек для проверки скобочек

        for (int i = 0; i < arr.length - 1; i++) {
            if (Character.isLetter(arr[i]) &&                           //Подряд стоят два операнда
                    Character.isLetter(arr[i + 1]))                     //без знака?
                throw new RuntimeException("OPERAND_WR");               //Ошибка

            if (!Character.isLetter(arr[i]) &&                          //Посторонний символ?
                    !Character.isDigit(arr[i]) &&
                    arr[i] != '(' &&
                    arr[i] != ')' &&
                    arr[i] != '+' &&
                    arr[i] != '-' &&
                    arr[i] != '*' &&
                    arr[i] != '/' &&
                    arr[i] != '^') throw new RuntimeException("OPERATOR_WR");   //Ошибка

            if (operands.contains(Character.toString(arr[i])) &&         //Несколько подряд идущих операторов? Ошибка
                    operands.contains(Character.toString(arr[i + 1])))    throw new RuntimeException("OPERATOR_WR");
        }

        //Проверяем выражение на скобочки
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == '(') brStack.push(new Node(arr[i], 0));
            else
            if (arr[i] == ')' && brStack.peek() == '(') brStack.pop();
        }
        //Нарушен баланс скобок? Ошибка
        if (!brStack.isEmpty()) throw new RuntimeException("BRACKET_WR");
    }
}