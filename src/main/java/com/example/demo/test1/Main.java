package com.example.demo.test1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s="";
        while(input.hasNextLine()){
            s=input.nextLine();
            String[] split = s.split(" ");
            int n = split[split.length-1].length();
            System.out.println(n);
        }
    }
}
