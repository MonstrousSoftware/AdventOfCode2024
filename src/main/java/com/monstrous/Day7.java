package com.monstrous;

public class Day7 {

    public Day7() {
        System.out.println("Day 7");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day7.txt");
        int[] terms;
        int numTerms;
        long sum = 0;
        long sum2 = 0;
        for(String line : input.lines ){
            String[] words = line.split(":");
            long total = Long.parseLong(words[0]);
            String[] values = words[1].trim().split(" ");
            numTerms = values.length;
            terms = new int[numTerms];
            for(int i = 0; i < values.length; i++)
                terms[i] = Integer.parseInt(values[i]);
            if(test(2, total, terms, numTerms))
                sum += total;

            if(test(3, total, terms, numTerms))
                sum2 += total;
         }

        System.out.println("part 1:  "+ sum); // 1038838357795
        System.out.println("part 2:  "+ sum2); // 254136560217241

        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }

    private boolean test(int base, long expected, int terms[], int numTerms){

        int counter = 0;
        int[] ops = new int[numTerms-1];
        while(counter < (int) Math.pow(base, numTerms-1)) {
            int shift = counter;
            for (int i = 0; i < numTerms - 1; i++) {
                ops[i] = shift % base;
                shift /= base;
            }
            counter++;


            long total = terms[0];
            for (int i = 1; i < numTerms; i++) {
                switch (ops[i - 1]) {
                    case 0:
                        total *= terms[i];
                        break;
                    case 1:
                        total += terms[i];
                        break;
                    case 2:
                        total = concat(total, terms[i]);
                        break;
                }
            }
            if (total == expected)
                return true;
        }
        return false;
    }

    private long concat(long a, long b){
        int digits = 1 + (int) Math.log10(b);
        while(digits-- > 0)
            a *= 10;
        long cat = a+b;
        return cat;
    }

}
