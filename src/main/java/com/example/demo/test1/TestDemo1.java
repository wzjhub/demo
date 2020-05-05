package com.example.demo.test1;

import java.util.ArrayList;
import java.util.List;

public class TestDemo1 {
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        List<ArrayList<Integer>> arrays = getSubArrays(nums);
        for(int i=0;i<arrays.size();i++){
            ArrayList<Integer> sub = arrays.get(i);
            for(int j=0;j<sub.size();j++){
                System.out.print(sub.get(j)+"  ");
            }
            System.out.println();
        }

    }

    public static List getSubArrays(int[] nums){
        int count = (int)Math.pow(2,nums.length);
        List<ArrayList<Integer>> arrays = new ArrayList<ArrayList<Integer>>();
        for(int i=1;i<count;i++){
            List<Integer> subarray = new ArrayList<Integer>();
            int temp = i;
            int index = 0;
            while (temp!=0){
                if((temp&1)==1){
                    subarray.add(nums[index]);
                }
                index++;
                temp = temp >>1;
            }
            arrays.add((ArrayList<Integer>) subarray);
        }
        return arrays;
    }
}
