package com.util;

public class HeapMonitor {

    private static long lastUsedMemory = 0;

    public static void logMemory(String label) {
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long max = runtime.maxMemory();
        long used = total - free;
    
        System.out.printf("[HeapMonitor] %-20s Used: %6.2f MB | Total: %6.2f MB | Max: %6.2f MB | Delta: %+6.2f MB%n",
                label,
                used / 1024.0 / 1024.0,
                total / 1024.0 / 1024.0,
                max / 1024.0 / 1024.0,
                (used - lastUsedMemory) / 1024.0 / 1024.0
        );
    
        lastUsedMemory = used;
    }
    
    public static void forceGC(String label) {
        System.gc();
        try {
            Thread.sleep(100); // give GC a moment
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logMemory(label + " (after GC)");
    }
}
