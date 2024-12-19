package com.monstrous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

    public Day1() {
        System.out.println("Day 1");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day1.txt");

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for(String line : input.lines ){
            String[] words  = line.split("\s+");
            int val1 = Integer.parseInt(words[0]);
            int val2 = Integer.parseInt(words[1]);
            list1.add(val1);
            list2.add(val2);
        }

        Collections.sort(list1);
        Collections.sort(list2);

        long sum = 0;
        for(int i = 0; i < list1.size(); i++){
            int diff = Math.abs(list1.get(i) - list2.get(i));
            sum += diff;
        }
        System.out.println("part 1: sum of differences: "+sum);

        long similarity = 0;
        for(int i = 0; i < list1.size(); i++){
            Integer num = list1.get(i);

            int start = list2.indexOf(num);
            if(start < 0)
                continue;
            int index = start+1;
            while(index < list2.size() && list2.get(index).equals(num))
                index++;
            similarity += (long) num * (index - start);
        }
        System.out.println("part 2: similarity score: "+similarity);


        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }
}
