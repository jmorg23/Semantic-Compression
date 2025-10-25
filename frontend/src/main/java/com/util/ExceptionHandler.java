package com.util;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;

public class ExceptionHandler {

    public static void handle(Exception e) {
        if (e instanceof LineUnavailableException) {
            showOptionPane("Cannot load all sound effects", e, false);
        } else if (e instanceof UnsupportedAudioFileException) {
            showOptionPane("Cannot load all sound effects", e, false);
        } else if (e instanceof JavaLayerException) {
            showOptionPane("Songs not loading", e, false);
        } else if (e instanceof IOException) {
            showOptionPane("Reading File", e, true);
        } else if (e instanceof NullPointerException) {
            showOptionPane("Null Pointer Exception, Something is left blank", e, false);
        } else if (e instanceof ArrayIndexOutOfBoundsException) {
            showOptionPane("Array Index Wronge", e, false);
        } else if (e instanceof IllegalArgumentException) {
            showOptionPane("IllegalArgumentException may do with sound effects", e, false);
        } else if (e instanceof IndexOutOfBoundsException) {
            showOptionPane("Array Index Wrong", e, false);
        } else if (e instanceof NumberFormatException) {
            showOptionPane("number format exceptione", e, true);
        } else if (e instanceof StringIndexOutOfBoundsException) {
            showOptionPane("String Index out of boundse", e, true);
        } else if (e instanceof ClassCastException) {
            showOptionPane("Class Cast Exceptione", e, true);
        } else if (e instanceof ArithmeticException) {
            showOptionPane("Arithmetic Exceptione", e, true);
        } else if (e instanceof SecurityException) {
            showOptionPane("Security exceptione", e, true);
        } else if (e instanceof UnsupportedOperationException) {
            showOptionPane("Unsupported Operation", e, true);
        } else if (e instanceof IllegalStateException) {
            showOptionPane("Wrong state exception", e, true);
        }

    }

    private static void showOptionPane(String message, Exception e, boolean fatalError) {
        JOptionPane.showMessageDialog(null, message,
                "Error involving: " + message + " will Attempt to run game, game experience may decline",
                JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
        if (fatalError) {
            System.exit(1);
        }
    }

}
