package com.monstrous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {


    public Day6() {
        System.out.println("Day 6");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day6a.txt");

        int height = input.lines.size();
        int width = input.lines.get(0).length();
        char[][] grid = new char[height][width];

        int y = 0;
        for(String line : input.lines ){
            for(int x = 0; x < width; x++)
                grid[y][x] = line.charAt(x);
            y++;
         }

        int len = findPathLength(grid, width, height);
        int num = findObstacleLocations(grid, width, height);
        System.out.println("part 1:  "+ len); //4752
        System.out.println("part 2:  "+ num); // too high 15992

        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }

    // NESW
    int[] dx = { 0, 1, 0, -1 };
    int[] dy = { -1, 0, 1, 0 };

    private int findPathLength(char[][] grid, int width, int height){
        int startx = 0, starty = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(grid[y][x] == '^'){
                    startx = x;
                    starty = y;
                }
            }
        }
        int dir = 0;
        int sx = startx;
        int sy = starty;

        while(true) {
            int tx = sx + dx[dir];
            int ty = sy + dy[dir];
            if (tx < 0 || ty < 0 || tx >= width || ty >= height)
                break;
            if (grid[ty][tx] != '#') {
                grid[ty][tx] = 'X';
                sx = tx;
                sy = ty;
            } else {
                dir = (dir + 1) % 4;
            }
        }
        grid[starty][startx] = '^';
        return countX(grid, width, height);
    }

    private int countX(char[][]grid, int width, int height){
        int steps = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(grid[y][x] == 'X' || grid[y][x] == '^'){
                    steps++;
                    grid[y][x] = '.';
                }
            }
        }
        return steps;
    }

    private int findObstacleLocations(char[][] grid, int width, int height){

        int startx = 0, starty = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(grid[y][x] == '^'){
                    startx = x;
                    starty = y;
                }
            }
        }

        int numLocations = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(grid[y][x] == '.'){
                    grid[y][x] = 'O';
                    if(causesLoop(grid, width, height, startx, starty))
                        numLocations++;
                    countX(grid, width, height);
                    grid[y][x] = '.';
                    grid[starty][startx] = '^';
                }
            }
        }
        return numLocations;
    }

    private boolean causesLoop(char[][] grid, int width, int height, int sx, int sy){
        int dir = 0;
        Map<Integer, Integer> visited = new HashMap<>();

        while(true) {
            int tx = sx + dx[dir];
            int ty = sy + dy[dir];
            if (tx < 0 || ty < 0 || tx >= width || ty >= height)
                return false;
            if (grid[ty][tx] != '#' && grid[ty][tx] != 'O') {
                int key = tx | (ty << 16) | (dir<<20);
                if(grid[ty][tx] == 'X'){
                    if(visited.get(key) != null)    // already been here? with same direction?
                        return true;
                }
                visited.put(key,key);
                grid[ty][tx] = 'X';
                sx = tx;
                sy = ty;
            } else {
                dir = (dir + 1) % 4;
            }
        }
    }
}
