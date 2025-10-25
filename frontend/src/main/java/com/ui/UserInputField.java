package com.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextArea;

import com.networking.Client;

public class UserInputField extends JTextArea{


    private RoundRectangle2D bounds;

    private Panel panel;
    public UserInputField(Panel pan, Client client) {
        super();
        this.panel = pan;
        bounds = new RoundRectangle2D.Double(550, 930, 1000, 100, 25, 25);
        setBounds(550, 930, 1000, 100);
        setOpaque(false); // removes default background
        setBackground(new Color(0, 0, 0, 0)); // fully transparent
        setForeground(Color.GRAY); // text color still visible
        setBorder(null); // optional: remove border
        setCaretColor(Color.BLACK); // so you can see the caret
        setEditable(false);
        setWrapStyleWord(true);
        setLineWrap(true);
        setFont(new Font("Segoe UI", Font.PLAIN, 35));


        setText("Type your message here...");

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                
                setText("");
                setEditable(true);

                setForeground(Color.BLACK); 
                
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = getText().trim();
                    panel.sendMessage(message, client);
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