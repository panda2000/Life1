package ru.pandaprg.life;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class LifePresenter {

    ILifeView iView;
    ILifeData iData;

    public LifePresenter (ILifeView iView, ILifeData iData){
        this.iView = iView;
        this.iData = iData;

        iView.setArr(nextGen());

        Runnable runnable = new MyRunnable(iView);
        Thread thread = new Thread(runnable);
        thread.start();
    }

/*
    @Override
    public void addCell(int x, int y) {

    }

    @Override
    public void killAll() {

    }

    @Override
    public void killCell(int x, int y) {

    }
*/
    public LinkedList<Cell> nextGen() {
        return iData.nextGen();
    }

    class MyRunnable implements Runnable {

        ILifeView iView;

        public MyRunnable (ILifeView iView){
            this.iView = iView;
        }

        @Override
        public void run() {
           // Log.i("Thread", "Новое поколение");
            iView.setArr(nextGen());
            // Переносим сюда старый код
            long endTime = System.currentTimeMillis()
                    + 2 * 1000;

            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime -
                                System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
           // run();
        }
    }
}
