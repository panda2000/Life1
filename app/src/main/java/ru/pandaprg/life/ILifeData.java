package ru.pandaprg.life;


import java.util.LinkedList;

public interface ILifeData {
    public void setCell(Cell cell);
    public void killAll ();
    public void killCell(int x, int y);
    public LinkedList<Cell> nextGen ();
}
