package com.plugow.shoppingapp;

import org.junit.Test;

import java.util.HashMap;
import java.util.Scanner;

public class MyTests {

    @Test
    public void test(){
        String a = "cos głupiego";
        StringBuilder builder = new StringBuilder();
        builder.append(a);
        System.out.println(builder.reverse());
    }


    @Test
    public void withoutReverse(){
        String a = "cos głupiego";
        char[] ac = a.toCharArray();
        for (int i = ac.length-1; i>=0; i--) {
            System.out.print(ac[i]);
        }
    }


    @Test
    public void withScanner(){
        String str;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your String");
        str = in.nextLine();
        for (int i = str.length()-1; i>=0; i--) {
            System.out.print(str.charAt(i));
        }

    }

    @Test
    public void swapNumbers(){
        int a =1;
        int b =3;
        a = a+b;
        b= a-b;
        a= a-b;
    }

    @Test
    public void countWords(){
        String str = "cos bardzo głupiego i cos bardzo dziwnego";
        String[] words = str.split(" ");
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int index = 1;
        for (String word : words) {
            if (map.containsKey(word)){
                int count= map.get(word);
                map.put(word, count+1);
            } else {
                map.put(word, 1);
            }
        }

        System.out.println(map);
    }

    public boolean isPrime(int a){
        if (a<=1){
            return false;
        } else {
            for (int i = 2; i<Math.sqrt(a); i++){
                if (a%i==0){
                    return false;
                }

            }
            return true;
        }
    }


    @Test
    public void factorTest(){
        System.out.println(factor(5));
        printFibonacci(8);
    }

    public int factor(int a){
        if (a<=1) return 1;
        else return a*factor(a-1);

    }

    public void printFibonacci(int amount){
        int a = 0;
        int b = 1;

        for (int i =2; i<=amount; i++){
            int temp = a+b;
            a=b;
            b=temp;
            System.out.println(b);
        }
    }

    public int fib(int amount){
        if (amount==0 || amount==1) return 1;
        else return fib(amount-1)+fib(amount-2);
    }


    @Test
    public void duplicateCharacters(){
        String a= "ccos ggłupiego";
        char[] chars=a.toCharArray();
        for (int i = 0; i<chars.length; i++){
            for (int j =i+1; j<chars.length; j++){
                if (chars[i]==chars[j]){
                    System.out.println("Duplicate character "+ chars[j]);
                    break;
                }
            }
        }
    }
}
