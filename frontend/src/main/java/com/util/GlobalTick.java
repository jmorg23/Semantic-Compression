package com.util;

public class GlobalTick {

    private static Tick myTick;

    public GlobalTick(int fps, Runnable r) {
        myTick = new Tick(fps);
        setTarget(r);
    }

    public static void startTick() {
        myTick.start();
    }

    public static void stopTick() {
        myTick.stop();
    }

    public static double getFps() {
        // return myTick.getCurrentFPS();
        // System.out.println("FPS: "+myTick.getActualTickRate());
        // System.out.println("FPS as double: "+(double)myTick.getActualTickRate());

        return 60.0;

    }

    public static void setTick(int fps, Runnable r) {
        myTick = new Tick(fps);
    

    }
    // public static void setTick(int fps, Runnable r, Runnable con){
    // myTick = new sTick(fps, r, con);

    // }
    public static void setTick(int fps) {
        myTick = new Tick(fps);

    }

    public static void setTarget(Runnable r) {

        // myTick.setUpdates(r);
        myTick.setUpdates(r);
        // myTick.setTarget(r);
    }

}
