package com.util;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;


public class Grid implements MouseListener {

    public boolean allowClick = false;
    private int fx, fy, rows, colls, width, height;
    private Point sel;
    @SuppressWarnings("unused")
    private BufferedImage boxImage;

    public Grid(int r, int c, int w, int h, int x, int y) {
        fx = x;
        fy = y;
        height = h;
        width = w;
        colls = c;
        rows = r;
    }

    public Grid(int r, int c, int w, int h, int x, int y, BufferedImage customBox) {
        fx = x;
        fy = y;
        height = h;
        width = w;
        colls = c;
        rows = r;
        boxImage = customBox;
    }

    public void drawGrid(Graphics g) {
        for (int i = 1; i <= colls; i++) {
            for (int j = 1; j <= rows; j++) {

                g.fillRect(fx * j, fy * i, width, height);

            }

        }
    }

    public Point getSel(){
        return sel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (allowClick) {
            for (int i = 0; i < colls; i++) {
                for (int j = 0; j < rows; j++)
                    if (((x < fx + (i * width) + width) && (x > fx + (i * width))) && ((y > fy) && (y < fy + height))) {
                        sel.x = j;
                        sel.y = i;
                        System.out.println("clicked");
                    }

            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
