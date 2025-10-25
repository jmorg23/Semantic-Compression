
package com.util;
public class Tick4 {
    private final int targetFPS;
    private final long targetFrameTimeNanos;
    private Runnable targetRunnable;
    private boolean running;
    private Thread tickThread;

    private long actualTickRate; // Tracks the actual tick rate
    private long runnableCount; // Counts how many times the Runnable is executed
    private long lastFpsUpdateTime; // Tracks the last time FPS was updated

    public Tick4(int targetFPS) {
        this.targetFPS = targetFPS;
        this.targetFrameTimeNanos = 1_000_000_000L / targetFPS;
        this.actualTickRate = 0;
        this.lastFpsUpdateTime = System.nanoTime();
    }

    public synchronized void setTargetRunnable(Runnable targetRunnable) {
        this.targetRunnable = targetRunnable;
    }

    public synchronized void start() {
        if (running) return;

        running = true;
        tickThread = new Thread(this::runLoop, "TickThread");
        tickThread.setDaemon(true);
        tickThread.start();
    }

    public synchronized void stop() {
        running = false;
        if (tickThread != null) {
            try {
                tickThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void runLoop() {
       // long lastTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            //long elapsedTime = currentTime - lastTime;
           // lastTime = currentTime;

            // Run the target logic and count its executions
            if (targetRunnable != null) {
                targetRunnable.run();
                synchronized (this) {
                    runnableCount++;
                }
            }

            // Update the actual tick rate every second
            long now = System.nanoTime();
            if (now - lastFpsUpdateTime >= 1_000_000_000L) {
                synchronized (this) {
                    actualTickRate = runnableCount; // Count the number of times the Runnable was executed
                    runnableCount = 0; // Reset for the next second
                    // System.out.println("FPS from tick: "+actualTickRate);
                }
                lastFpsUpdateTime += 1_000_000_000L;
            }

            // Calculate remaining time for the frame
            long sleepTime = targetFrameTimeNanos - (System.nanoTime() - currentTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000L, (int) (sleepTime % 1_000_000L));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                // Log a warning if the frame rate cannot be maintained
                System.err.println("Warning: Frame rate cannot keep up with target of " + targetFPS + " FPS!");
            }
        }
    }

    public synchronized long getActualTickRate() {
        return actualTickRate;
    }

    public boolean isRunning() {
        return running;
    }
}

// public class Tick {
//     private final int targetFPS;
//     private final long targetFrameTimeNanos;
//     private Runnable targetRunnable;
//     private boolean running;
//     private Thread tickThread;

//     private double actualTickRate; // Tracks the actual tick rate
//     private double frameCount; // Counts frames in the current second
//     private double fpsTimer; // Timer to calculate FPS

//     public Tick(int targetFPS) {
//         this.targetFPS = targetFPS;
//         this.targetFrameTimeNanos = 1_000_000_000L / targetFPS;
//         this.actualTickRate = 0;
//     }

//     public synchronized void setTargetRunnable(Runnable targetRunnable) {
//         this.targetRunnable = targetRunnable;
//     }

//     public synchronized void start() {
//         if (running) return;

//         running = true;
//         tickThread = new Thread(this::runLoop, "TickThread");
//         tickThread.setDaemon(true);
//         tickThread.start();
//     }

//     public synchronized void stop() {
//         running = false;
//         if (tickThread != null) {
//             try {
//                 tickThread.join();
//             } catch (InterruptedException e) {
//                 Thread.currentThread().interrupt();
//             }
//         }
//     }

//     private void runLoop() {
//         long lastTime = System.nanoTime();
//         frameCount = 0;
//         fpsTimer = System.currentTimeMillis();

//         while (running) {
//             long currentTime = System.nanoTime();
//             long elapsedTime = currentTime - lastTime;
//             lastTime = currentTime;

//             if (targetRunnable != null) {
//                 targetRunnable.run();
//             }

//             frameCount++;
//             if (System.currentTimeMillis() - fpsTimer >= 1000) {
//                 synchronized (this) {
//                     actualTickRate = frameCount;
//                 }
//                 frameCount = 0;
//                 fpsTimer += 1000;
//             }

//             long sleepTime = targetFrameTimeNanos - (System.nanoTime() - currentTime);
//             if (sleepTime > 0) {
//                 try {
//                     Thread.sleep(sleepTime / 1_000_000L, (int) (sleepTime % 1_000_000L));
//                 } catch (InterruptedException e) {
//                     Thread.currentThread().interrupt();
//                 }
//             } else {

//                 System.err.println("Warning: Frame rate cannot keep up with target of " + targetFPS + " FPS!");
//             }
//         }
//     }

//     public synchronized double getActualTickRate() {
//         return actualTickRate;
//     }

//     public boolean isRunning() {
//         return running;
//     }
// }
