package com.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.stream.ImageInputStream;


import javax.imageio.*;

public class GifPlayer {
    // public static BufferedImage[] readGif(String gifFilePath) {
    // try {
    // ImageReader reader = getImageReader();
    // ImageInputStream imageInputStream = new
    // FileImageInputStream(getClass().getResourceAsStream(gifFilePath));
    // reader.setInput(imageInputStream);

    // int numFrames = reader.getNumImages(true);
    // BufferedImage[] frames = new BufferedImage[numFrames];
    // System.out.println(numFrames+" = number of frames");
    // for (int i = 0; i < numFrames; i++) {
    // frames[i] = reader.read(i);
    // }

    // imageInputStream.close();
    // return frames;
    // } catch (IOException e) {
    // e.printStackTrace();
    // return null;
    // }
    // }
    // private static ImageReader getImageReader() {
    // Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");
    // if (readers.hasNext()) {
    // return readers.next();
    // } else {
    // throw new RuntimeException("No GIF reader found");
    // }
    // }
    public static BufferedImage[] readGif(String filePath) {
        try {
            InputStream file = GifPlayer.class.getResourceAsStream(filePath);
            ImageInputStream stream = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");

            if (!readers.hasNext()) {
                throw new IOException("No GIF reader available");
            }

            ImageReader reader = readers.next();
            reader.setInput(stream);

            int numberOfFrames = reader.getNumImages(true);
            System.out.println("Frames: " + numberOfFrames);
            List<BufferedImage> frames = new ArrayList<>(numberOfFrames);

            for (int i = 0; i < numberOfFrames; i++) {
                try {
                    BufferedImage frame = reader.read(i);
                    // if (frame == null) {
                    // System.err.println("Frame " + i + " is null.");
                    // } else {
                    // System.out.println("Frame " + i + " read successfully. Dimensions: " +
                    // frame.getWidth() + "x" + frame.getHeight());
                    // }
                    frames.add(frame);
                } catch (IOException e) {
                    System.err.println("Error reading frame " + i + ": " + e.getMessage());
                }
            }

            reader.dispose();
            stream.close();
            return frames.toArray(new BufferedImage[0]);

        } catch (Exception e) {
            return null;
        }
    }

    // public static BufferedImage createCompatibleImage(BufferedImage i) {
    //     GraphicsConfiguration gc = Camera.GC;
    //     BufferedImage compatibleImage = gc.createCompatibleImage(i.getWidth(), i.getHeight(), Transparency.OPAQUE);
    //     Graphics g = compatibleImage.getGraphics();
    //     g.drawImage(i, 0, 0, null);
    //     g.dispose();
    //     return i;
    // }
    public static BufferedImage createCompatibleImage(BufferedImage image) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
    
        // Create a compatible image with the same dimensions and transparency
        BufferedImage compatibleImage = gc.createCompatibleImage(
            image.getWidth(),
            image.getHeight(),
            image.getTransparency()
        );
    
        // Copy the original image's content to the compatible image
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    
        return compatibleImage;
    }
    

    public static BufferedImage reverse(BufferedImage i) {
        int width = i.getWidth();

        // Create a horizontal flip transformation
        AffineTransform flip = AffineTransform.getScaleInstance(-1, 1);
        flip.translate(-width, 0);

        // Create an AffineTransformOp object and apply the transformation
        AffineTransformOp flipOp = new AffineTransformOp(flip, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return flipOp.filter(i, null);

    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }
        /**
     * Returns a safe subimage (deep copy) from the given source image.
     * This ensures the returned image is standalone and doesn't reference the source.
     */
    public static BufferedImage getSafeSubImage(BufferedImage src, int x, int y, int w, int h) {
        BufferedImage copy = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = copy.createGraphics();
        g2.drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        g2.dispose();
        return copy;
    }

}
