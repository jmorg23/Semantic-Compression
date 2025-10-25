package com.util;

public class Tick2 {
    private int fps;
    private Runnable runnable;
    private Runnable conRunnable;

    private boolean running;
    private Thread thread;
    private int actualFps;

    public void setConRunnable(Runnable r){
        conRunnable = r;
    }
    public Tick2(int fps, Runnable runnable) {
        this.fps = fps;
        this.runnable = runnable;
        this.running = false;
    }
    public Tick2(int fps, Runnable runnable, Runnable conRunnable) {
        this.fps = fps;
        this.runnable = runnable;
        this.conRunnable = conRunnable;
        this.running = false;
    }
    public void setTarget(Runnable r){
        runnable = r;
    }
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this::run);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getActualTickRate() {
        return actualFps;
    }

private void run() {
    long lastTime = System.nanoTime();
    long now;
    double nsPerTick = 1000000000.0 / fps;
    double delta = 0;
    int ticks = 0;
    long timer = System.nanoTime();

    while (running) {
        now = System.nanoTime();
        delta += (now - lastTime) / nsPerTick;
        lastTime = now;

        while (delta >= 1) {
            if(conRunnable != null)
            conRunnable.run();
            runnable.run();
            ticks++;
            delta--;
        }

        // Ensure the thread sleeps to maintain target FPS
        long sleepTime = (long)(lastTime + nsPerTick - System.nanoTime());
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime / 1000000, (int)(sleepTime % 1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (System.nanoTime() - timer > 1000000000) { // 1 second in nanoseconds
            timer += 1000000000;
            actualFps = ticks;
            ticks = 0;
        }
    }
}

}
