package com.monstrous;

import java.util.ArrayList;
import java.util.List;

public class Day8 {

    int width;
    int height;
    List<Integer> locations;

    public static class Node{
        char frequency;
        int x, y;

        public Node(char frequency, int x, int y) {
            this.frequency = frequency;
            this.x = x;
            this.y = y;
        }
    }

    public Day8() {
        System.out.println("Day 8");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day8.txt");
        List<Node> nodes[] = new List[128];
        for(int i = 0 ; i < 128 ; i++)
            nodes[i] = new ArrayList<>();

        int ty = 0;
        width = input.lines.get(0).length();
        height = input.lines.size();

        for(String line : input.lines ){
            for(int x = 0; x < line.length(); x++){
                char k = line.charAt(x);
                if(k != '.'){
                    Node node = new Node(k, x, ty);
                    nodes[k].add(node);
                }
            }
            ty++;
        }

        locations = new ArrayList<>();
        for(int i = 0; i < 128; i++)
            makeAntinodes(1, nodes[i]);

        long sum1 = locations.size();

        locations.clear();
        for(int i = 0; i < 128; i++)
            makeAntinodes(0, nodes[i]);
        long sum2 = locations.size();

        System.out.println("part 1:  "+ sum1);
        System.out.println("part 2:  "+ sum2);

        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }

    private void makeAntinodes( int steps, List<Node> nodes ){
        for(int i = 0 ; i < nodes.size()-1; i++){
            for(int j = i+1; j < nodes.size(); j++){
                doPair(steps, nodes.get(i), nodes.get(j));
            }
        }
    }

    private void doPair(int steps, Node a, Node b){
        testPair(steps, a, b);
        testPair(steps, b, a);
    }

    // steps = 1 for phase 1, 0 for phase 2
    private void testPair(int steps, Node a, Node b){

        int dx = (b.x - a.x);
        int dy = (b.y - a.y);
        int iter = steps;
        while(true) {
            int x = b.x + iter * dx;
            int y = b.y + iter * dy;
            if (x >= 0 && y >= 0 && x < width && y < height) {
                int key = (x << 16) | y;
                if (!locations.contains(key))
                    locations.add(key);
                iter++;
                if(steps == 1)
                    return;
            } else
                return;
        }
    }



}
