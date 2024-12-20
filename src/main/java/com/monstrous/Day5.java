package com.monstrous;

import java.util.ArrayList;
import java.util.List;

public class Day5 {

    static class Pair {
        int before;
        int after;

        public Pair(int before, int after) {
            this.before = before;
            this.after = after;
        }
    }


    public Day5() {
        System.out.println("Day 5");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day5.txt");

        List<Pair> pairs = new ArrayList<>();
        List<Integer> pages = new ArrayList<>();

        long sum = 0;
        long sum2 = 0;
        boolean rulesMode = true;
        for(String line : input.lines ){
            if(line.isBlank()){
                rulesMode = false;
                continue;
            }

            if(rulesMode){
                String[] words = line.split("\\|");
                int before = Integer.parseInt(words[0]);
                int after = Integer.parseInt(words[1]);
                Pair pair = new Pair(before, after);
                pairs.add(pair);
            } else {
                pages.clear();
                String[] words = line.split(",");
                for(int i = 0; i < words.length; i++)
                    pages.add( Integer.parseInt(words[i]) );
                int middlePage = check(pages, pairs);
                sum += middlePage;
                if(middlePage == 0)
                    sum2 += fix(pages, pairs);
            }
        }

        System.out.println("part 1:  "+ sum); //6267
        System.out.println("part 2:  "+ sum2); // 5184

        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }

    private int check(List<Integer> pages, List<Pair> rules){
        for(Pair rule : rules){
            if(!followsRule(pages, rule))
                return 0;
        }
        return pages.get(pages.size()/2);
    }

    private boolean followsRule(List<Integer> pages, Pair rule){
        int index1 = pages.indexOf(rule.before);
        int index2 = pages.indexOf(rule.after);
        if(index1 < 0 || index2 < 0)
            return true;
        return index1 < index2;
    }

    private int fix(List<Integer> pages, List<Pair> rules){

        boolean swapped;
        do {
            swapped = false;
            for (Pair rule : rules) {
                swapped |= applyRule(pages, rule);
            }
        } while(swapped);
        return pages.get(pages.size()/2);
    }

    private boolean applyRule(List<Integer> pages, Pair rule){
        int index1 = pages.indexOf(rule.before);
        int index2 = pages.indexOf(rule.after);
        if(index1 < 0 || index2 < 0)
            return false;
        if(index2 < index1){
            int tmp = pages.get(index1);
            pages.set(index1, pages.get(index2));
            pages.set(index2, tmp);
            return true;
        }
        return false;
    }

}
