package ru.pandaprg.life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;

public class Life extends AppCompatActivity implements ILifeView {

    private int x = 500;
    private int y = 1000;
    LinkedList<Cell> field = new LinkedList<Cell>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));

        ILifeData data = new LifeData(x,y);
        LifePresenter presenter = new LifePresenter(this, data);
    }

    @Override
    public void setArr(LinkedList<Cell> field) {
        this.field = field;
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        Paint p;

        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while (retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                }
            }
        }

        class DrawThread extends Thread {

            private boolean running = false;
            private SurfaceHolder surfaceHolder;

            public DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            public void setRunning(boolean running) {
                this.running = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                while (running) {
                    canvas = null;
                    try {
                        //Log.d("LOG draw", " ... ");

                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null)
                            continue;

                        // заливка канвы цветом
                        canvas.drawARGB(80, 102, 204, 255);
                        // настройка кисти
                        // красный цвет
                        p.setColor(Color.GREEN);
                        // толщина линии = 10
                        p.setStrokeWidth(15);


                        int x, y;
                        for(Cell dot : field) {


                               // рисуем точку (x,y)
                                canvas.drawPoint(dot.getX(), dot.getY(), p);
                                //Log.d("LOG draw", "Draw x = " + x +"  y = " + y);

                        }


                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }

    }
}
