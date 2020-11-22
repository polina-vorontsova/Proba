package com.company;

import java.util.Scanner;

public class Main {

    //Метод для "чтения записи справа налево"
    private static String getReversed(String s){
        String buf = "";
        for (int i = s.length() - 1; i >= 0; i--){
            if (s.charAt(i) == ')') buf += '(';
            else
            if (s.charAt(i) == '(') buf += ')';
            else                buf += s.charAt(i);
        }
        //Возвращаем перевертыш
        return buf;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        RevPolNot toPolish; //Объектная переменная для образования обратной польской записи

        System.out.println("Введите инфиксное выражение: ");

        try {
            String buf = scanner.nextLine(); //Читаем выражение
            toPolish = new RevPolNot(buf); //Делаем обработку
            String RPN = toPolish.getReversedPolishNotation();
            System.out.println("Обратная польская запись: " + RPN);  //Выводим польскую запись
            toPolish = new RevPolNot(getReversed(buf)); //Отправляем на обработку перевернутую исходную строку
            //Переворачиваем справа налево результат и получаем префиксную запись
            System.out.println("Префиксная запись: " + getReversed(toPolish.getReversedPolishNotation()));

        } catch (RuntimeException e){ //Обработка ошибок ввода
            String msg = e.getMessage();
            switch (msg) {
                case "SHORT": System.out.println("Слишком короткое выражение!");
                case "WH_SPACE" : System.out.println("Инфиксное выражение содержит пробелы!!!");
                case "NUM" : System.out.println("Инфиксное выражение содержит числа!");
                case "OPERAND_WR" : System.out.println("В инфиксном выражении некорректно записан операнд!");
                case "OPERATOR_WR" : System.out.println("В инфиксном выражении присутствуют посторонние символы!");
                default : System.out.println("В инфиксном выражении беда со скобками!");
            }
        }
        //Закрываем чтение
        scanner.close();
    }
}