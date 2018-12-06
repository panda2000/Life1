package ru.pandaprg.life;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public class LifeData implements ILifeData {

    HashMap<Integer, Integer> connections;

    HashSet<Integer> cells;

    public LifeData (int x, int y){
        connections = new HashMap<Integer, Integer>();
        cells = new HashSet<Integer>();

    }

    public LifeData (HashSet<Integer> field){
        this.cells = field;
        this.connections = new HashMap<Integer, Integer>();
    }

    public LifeData (HashSet<Integer> field, HashMap<Integer, Integer> conn){
        this.cells = field;
        this.connections = conn;
    }


    public void setCell (Cell cell){

        if (!cells.contains(cell.getHash())){
            newCell(cells,cell.getHash());
            newConnections(connections,cell.getHash());}
//        Log.d("Log ", "Set Cell " + x + " , " + y);

    }

    void newCell (HashSet<Integer> field, Integer hash){
        field.add(hash);
    }
/*
    void bitrhCell (int x, int y){
        Integer hash = x << 16;
        hash = hash | y;
        connections.put(hash, scan(x, y));

    }

    Integer scan (int x, int y){
        Integer res = 0;
        Integer hash;

        hash = (x-1) << 16;
        hash = hash | (y-1);
        if (connections.containsKey(hash))
            res ++;


        hash = (x) << 16;
        hash = hash | (y-1);
        if (connections.containsKey(hash))
            res ++;

        hash = (x+1) << 16;
        hash = hash | (y-1);
        if (connections.containsKey(hash))
            res ++;

        hash = (x-1) << 16;
        hash = hash | (y);
        if (connections.containsKey(hash))
            res ++;

        hash = (x+1) << 16;
        hash = hash | (y);
        if (connections.containsKey(hash))
            res ++;

        hash = (x-1) << 16;
        hash = hash | (y+1);
        if (connections.containsKey(hash))
            res ++;

        hash = (x) << 16;
        hash = hash | (y+1);
        if (connections.containsKey(hash))
            res ++;

        hash = (x+1) << 16;
        hash = hash | (x+1);
        if (connections.containsKey(hash))
            res ++;

        return res;
    }

*/
    void editConnections (HashMap<Integer, Integer> field, Integer newHash, int add) {
        Integer res=0;
        if (field.containsKey(newHash))
            res = field.get(newHash);
        else
            res = 0;
        field.put(newHash, res + add);
    }



    void newConnections (HashMap<Integer, Integer> field, Integer hash){

        Cell cell = new Cell(hash);
        Cell temp;
        int x = cell.getX();
        int y = cell.getY();

        temp= new Cell(x-1,y-1);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x-1,y);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x-1,y+1);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x,y+1);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x,y-1);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x+1,y-1);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x+1,y);
        editConnections(field, temp.getHash(), 1);

        temp= new Cell(x+1,y+1);
        editConnections(field, temp.getHash(), 1);
    }
/*
    void killCell (HashMap<Integer, Integer> field, Integer hash){
        Integer newHash=0;
        //Integer res=0;

        field.put(hash, 0); // (x)(y)

        newHash = hash - 0x10000 - 0x1; // (x-1)(y-1)
        editConnections(field, newHash, -1);


        newHash = hash - 0x0 - 0x1; // (x)(y-1)
        editConnections(field, newHash, -1);

        newHash = hash + 0x10000 - 0x1; // (x+1)(y-1)
        editConnections(field, newHash, -1);

        newHash = hash - 0x10000 - 0x0; // (x-1)(y)
        editConnections(field, newHash, -1);

        newHash = hash + 0x10000 - 0x0; // (x+1)(y)
        editConnections(field, newHash, -1);


        newHash = hash - 0x10000 + 0x1; // (x-1)(y+1)
        editConnections(field, newHash, -1);

        newHash = hash - 0x0 + 0x1; // (x)(y+1)
        editConnections(field, newHash, -1);

        newHash = hash + 0x10000 + 0x1; // (x+1)(y+1)
        editConnections(field, newHash, -1);
    }
/*
    @Override
    public void addCell(int x, int y) {

    }
*/
    @Override
    public void killAll() {

    }

    @Override
    public void killCell(int x, int y) {

    }

    @Override
    public LinkedList<Cell> nextGen() {

        HashSet<Integer> newGen = new HashSet <Integer> ();
        HashMap<Integer, Integer> newGenConn = new HashMap<Integer, Integer> ();

        LinkedList <Cell> cellsList =  new LinkedList <Cell>();

        if (!connections.isEmpty() && !cells.isEmpty())
        for(Map.Entry <Integer, Integer> conn : connections.entrySet()) {
        //    int x = conn.getKey() >> 16;
        //    int y = conn.getKey() & 0xFFFF;
            if (conn.getValue() == 2 && cells.contains(conn.getKey())) {
//                Log.d("Log ", "Save Cell "  + x + " , " + y);
                newCell (newGen, conn.getKey());
                cellsList.add(new Cell(conn.getKey()));
                newConnections(newGenConn, conn.getKey());

            } else if (conn.getValue() == 3 ) {
//                Log.d("Log ", "New Cell "  + x + " , " + y);
                newCell (newGen, conn.getKey());
                cellsList.add(new Cell(conn.getKey()));
                newConnections(newGenConn, conn.getKey());

            } else {
//                Log.d("Log ", "Kill Cell "  + x + " , " + y);
//                killCell (newGen, cell.getKey());
            }
        }
        connections = newGenConn;
        cells = newGen;
        //cells = (HashSet<Integer>) newGen.clone();

        return cellsList;
    }
}
