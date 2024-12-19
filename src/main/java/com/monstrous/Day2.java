package com.monstrous;

import java.util.ArrayList;
import java.util.List;

public class Day2 {

    public Day2() {
        System.out.println("Day 2");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day2.txt");

        List<Integer> levels = new ArrayList<>();
        int safeCount1 = 0;
        int safeCount2 = 0;
        for(String line : input.lines ){
            levels.clear();
            String[] words  = line.split("\s+");
            for(String word : words) {
                int value = Integer.parseInt(word);
                levels.add(value);
            }
            if(isSafe(levels))
                safeCount1++;
            if(isSafeWithMargin(levels))
                safeCount2++;
        }

        System.out.println("part 1: safe records: "+ safeCount1);   //686
        System.out.println("part 2: safe records: "+ safeCount2);   //717


        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }



    private boolean isSafe(List<Integer> levels){

        boolean ascending = true;
        int prev = -1;
        for(int index = 0; index < levels.size(); index++){
            int value = levels.get(index);
            if(index == 1)
                ascending = (value > prev);
            if(index > 0){
                int diff = Math.abs(value - prev);
                if(diff < 1 || diff > 3)
                    return false;
                else if(ascending != (value > prev))
                    return false;
            }
            prev = value;
        }
        return true;
    }

    private boolean isSafeWithMargin(List<Integer> levels){
        if(isSafe(levels))
            return true;
        int size = levels.size();
        for(int index = 0; index < size; index++){
            int value = levels.get(index);
            levels.remove(index);
            if(isSafe(levels))
                return true;
            levels.add(index, value);
        }
        return false;
    }

}
