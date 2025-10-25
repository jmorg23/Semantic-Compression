package com.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class sTick {

    // CALC
    long tickInterval;
    long tickRate;
    // STATE
    boolean runState;
    double actualTickRate;
    long latency;

    private Runnable target;
    private ScheduledFuture<?> tickLoopSchedule;


    public sTick(long tickRate, Runnable target) {
        setTickRate(tickRate);
        setTarget(target);
    }

    public void start() {
        runState = true;
        if(tickLoopSchedule==null)
        tickLoopSchedule = scheduleTickLoop();
    }

    public void stop() {
        runState = false;
        if (tickLoopSchedule != null) {
            tickLoopSchedule.cancel(false);
        }
    }

    private ScheduledFuture<?> scheduleTickLoop() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        return executor.scheduleAtFixedRate(this::tickLoop, 0, (long) tickInterval, TimeUnit.MILLISECONDS);
    }

    /**
     * Called at constant rate
     */
    private void tickLoop() {

        long startTime = System.currentTimeMillis();
        target.run(); // Run the main logic
        

        long elapsedTime = System.currentTimeMillis() - startTime;
        long sleepTime = (long) (tickInterval - elapsedTime);

        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.latency = (tickInterval - sleepTime);
        actualTickRate = (elapsedTime > 0) ? (1000.0 / elapsedTime) : (1000.0 / tickInterval);

    }

    // SETTERS GETTERS
    public void setTickRate(long tickRate) {
        if (tickRate <= 0)
            throw new IllegalArgumentException("'tickRate' cannot be less than 1");

        // CALC INTERVAL
        this.tickRate = tickRate;
        this.tickInterval = (long) (1000.0 / tickRate);

        if (tickLoopSchedule != null) {
            tickLoopSchedule.cancel(false);
            tickLoopSchedule = scheduleTickLoop();
        }
    }

    public void setTarget(Runnable target) {
         if (target == null)
            throw new NullPointerException("'target' Runnable cannot be null");
        this.target = target;
    }

    public double getActualTickRate() {
        if (actualTickRate == 0)
            return tickRate;
        else
            return actualTickRate;
    }

    public long getLatency() {
        return latency;
    }

    public String getInfo() {
        return "FPS: " + String.format("%.1f", actualTickRate) + "/" + tickRate + ", latency: " + latency + "/"
                + tickInterval + "ms";
    }

    public boolean getIsRunning() {
        return runState;
    }
}
