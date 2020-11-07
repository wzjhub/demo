package com.huawei.it.execise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MinGongBeiShu {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(" ");
            int n1 = Integer.valueOf(arr[0]);
            int n2 = Integer.valueOf(arr[1]);
            for (int i = n2; i <= n1 * n2 ; i = i + n2) {
                if (i % n1 == 0) {
                    System.out.println(i);
                    break;
                }
            }
        }
    }
}
