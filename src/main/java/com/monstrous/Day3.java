package com.monstrous;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Day3 {

    public Day3() {
        System.out.println("Day 3");
        final long startTime = System.currentTimeMillis();

        String line = "";
        try {
            line = Files.readString(Paths.get("data/day3.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long sum = processLine(line);
        long sum2 = processConditionalLine(line);

        System.out.println("part 1:  "+ sum); //178886550
        System.out.println("part 2:  "+ sum2); // 87163705


        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }


    private long processLine(String line){
        long sum = 0;

        String[] segments = line.split("mul\\(");
        for(int i = 1; i < segments.length; i++ ){
            String segment = segments[i];
            // does it match "NNNN,NNNN)blabla" ?
            if(segment.isEmpty())
                continue;
            int index = 0;
            char k = segment.charAt(index);
            long num1 = 0;
            while(Character.isDigit(k)){
                num1 = 10*num1 + (k-'0');
                index++;
                k = segment.charAt(index);
            }
            if(k != ',')
                continue;
            index++;
            if(index == segment.length())
                continue;
            k = segment.charAt(index);
            long num2 = 0;
            while(Character.isDigit(k)){
                num2 = 10*num2 + (k-'0');
                index++;
                k = segment.charAt(index);
            }
            if(k != ')')
                continue;
            sum += num1 * num2;
        }
        return sum;
    }

    private long processConditionalLine(String line) {
        long sum = 0;

        String[] segments = line.split("don\\'t\\(\\)");

        sum += processLine(segments[0]);
        for (int i = 1; i < segments.length; i++) {
            String segment = segments[i];

            String[] parts = segment.split("do\\(\\)");
            for (int j = 1; j < parts.length; j++) {
                sum += processLine(parts[j]);
            }
        }
        return sum;
    }
}
