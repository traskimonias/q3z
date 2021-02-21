package com.example.q3z;

public class GameMap {
    int level;
    int rooms;
    int maxMapSize=10;
    public int[][] map= new int[10][10];
    public GameMap(int rooms){
        this.rooms=rooms;
        clearMap();
        fillMap();
    }
    void fillMap(){
        int x=5,y=5,r=0;
        map[x][y]=2;
        for (int i = 0; i < rooms; i++) {
            r=(int)(Math.random()*4);
            switch (r){
                case 0:
                    x+=1;
                case 1:
                    x-=1;
                case 2:
                    y+=1;
                case 3:
                    y-=1;
            }
            if(x<0)
                x=0;
            if(x>9)
                x=9;
            if(y<0)
                y=0;
            if(y>9)
                y=9;
            if(map[x][y]==0)
                map[x][y]=1;
        }
        map[x][y]=4;
        //trampear();
    }
    void clearMap(){
        for (int i = 0; i < maxMapSize; i++) {
            for (int j = 0; j < maxMapSize; j++) {
                map[i][j]=0;
            }
        }
    }
    void trampear(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map[4+i][4+j]=1;
            }
        }
    }
}
