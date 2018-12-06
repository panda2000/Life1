package ru.pandaprg.life;

public class Cell {
    private int x;
    private int y;
    private Integer hash;


    public Cell (int x, int y){
        this.x = x;
        this.y = y;
        getHash ();
    }

    public Cell (Integer hash){
        this.x = (hash >> 16) & 0xFFFF;
        this.y = hash & 0xFFFF;
    }

    public void setX (int x){
        this.x = x;
        getHash ();
    }
    public int getX () {return x;}
    public void setY (int y) {this.y = y;
        getHash ();}
    public int getY () {return y;}

    public Integer getHash (){
        hash = x << 16;
        hash = hash | y;
        return hash;
    }


}
