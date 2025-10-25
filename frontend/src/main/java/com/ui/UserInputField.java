package com.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextArea;

public class UserInputField extends JTextArea{


    private RoundRectangle2D bounds;

    private Panel panel;
    public UserInputField(Panel pan){
        super();
        this.panel = pan;
        bounds = new RoundRectangle2D.Double(80, 785, 1000, 50, 25, 25);
        setBounds(100, 800, 1000, 50);
        setOpaque(false); // removes default background
        setBackground(new Color(0, 0, 0, 0)); // fully transparent
        setForeground(Color.GRAY); // text color still visible
        //setBorder(null); // optional: remove border
        setCaretColor(Color.BLACK); // so you can see the caret
        setEditable(false);
        setWrapStyleWord(true);
        setLineWrap(true);


        setText("Type your message here...");

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                
                setText("");
                setEditable(true);

                setForeground(Color.BLACK); 
                
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    String message = getText().trim();
                    panel.sendMessage("Me: " + message);
                    setText("");
                    evt.consume(); // prevent adding a new line
                    setEditable(false);
                    setForeground(Color.GRAY);
                    setText("Type your message here...");

                }
            }
        });


    }


    public void draw(Graphics2D g2){
        
        g2.draw(bounds);

    }
}