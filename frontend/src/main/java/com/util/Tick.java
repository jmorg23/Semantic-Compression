package com.util;

public class Tick implements Runnable {
    private final double timePerFrame; // Time per frame in nanoseconds
    private boolean running;
    private double currentFPS;
    private Runnable updates;

    public Tick(int targetFPS, Runnable updates) {
        this.currentFPS = targetFPS;
        this.timePerFrame = 1_000_000_000.0 / targetFPS; // Nanoseconds per frame
        this.updates = updates;
    }

    public Tick(int targetFPS) {
        this(targetFPS, null); 
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public void stop() {
        running = false;
    }

    public double getCurrentFPS() {
        return currentFPS;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long fpsTimer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            double delta = (now - lastTime) / timePerFrame;

            if (delta >= 1) { // Process updates and rendering if enough time has passed
                if (updates != null)
                    update();

                frames++;
                lastTime += timePerFrame; // Advance the lastTime to stay in sync
            }

            // FPS Calculation (every second)
            if (System.currentTimeMillis() - fpsTimer >= 1000) {
                currentFPS = frames;
                frames = 0;
                fpsTimer += 1000;
            }

            // Sleep to avoid CPU overuse
            try {
                long sleepTime = (long) ((lastTime + timePerFrame - System.nanoTime()) / 1_000_000);
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUpdates(Runnable updates) {
        this.updates = updates;
    }

    private void update() {
        // Game logic update (movement, physics, etc.)
        // System.out.println("Updating");
        updates.run();
    }

}
