package com.monstrous;

public class Day4 {

    private final int width;
    private final int height;
    private final char[][] grid;

    public Day4() {
        System.out.println("Day 4");
        final long startTime = System.currentTimeMillis();

        FileInput input = new FileInput("data/day4.txt");

        width = input.lines.get(0).length();
        height = input.lines.size();
        grid = new char[height][width];

        int ty = 0;
        for(String line : input.lines ){
            for(int x = 0; x < line.length(); x++)
                grid[ty][x] = line.charAt(x);
            ty++;
        }

        long sum = 0;
        long sum2 = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(grid[y][x] == 'X')
                    sum += countWords(x, y);
                if(grid[y][x] == 'A')
                    sum2 += tryCrossMas(x, y);
            }
        }
        System.out.println("part 1:  "+ sum);
        System.out.println("part 2:  "+ sum2);

        final long endTime = System.currentTimeMillis();
        System.out.println("\nTotal execution time : " + (endTime - startTime) + " ms");
    }


    // 8 directions
    int[] dx = { 1, -1, 0, 0, 1, 1, -1, -1 };
    int[] dy = { 0, 0,  1, -1,-1, 1, -1, 1 };
    char[] word = { 'X', 'M', 'A', 'S'};

    private int countWords(int x, int y){
        int count = 0;
        for(int dir = 0; dir < 8; dir++){
            count += tryDirection(x, y, dx[dir], dy[dir]);
        }
        return count;
    }

    private int tryDirection(int sx, int sy, int dx, int dy ){
        for(int i = 0; i < 4; i++){
            int x = sx + i*dx;
            int y = sy + i*dy;
            if(x < 0 || y < 0 || x >= width || y >= height )
                return 0;
            if(grid[y][x] != word[i])
                return 0;
        }
        return 1;
    }

    private int tryCrossMas(int sx, int sy){
        if(grid[sy][sx] != 'A')
            return 0;
        if(sx == 0 || sy == 0 || sx == width-1 || sy == height -1)
            return 0;
        char c1 = grid[sy-1][sx-1];
        char c2 = grid[sy+1][sx+1];
        if( (c1 == 'M' && c2 == 'S') || (c1 == 'S' && c2 == 'M') ){
            c1 = grid[sy-1][sx+1];
            c2 = grid[sy+1][sx-1];
            if( (c1 == 'M' && c2 == 'S') || (c1 == 'S' && c2 == 'M') )
                return 1;

        }
        return 0;
    }

}
