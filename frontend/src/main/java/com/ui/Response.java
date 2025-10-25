package com.ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

public class Response  {
    
    public String message;
    public JLabel label;
    
    private int y;
    private int x = 550;

    private int index = 0;
    // private String con;
    // private String con = "Yes — you can make a `JTextArea` **transparent** (invisible background) while still being **fully functional and editable**.\r\n" + //

    //             "\r\n" + //
    //             "* `setOpaque(false)` tells Swing not to paint the background.\r\n" + //
    //             "* Setting `new Color(0,0,0,0)` ensures any background color is *fully transparent*.\r\n" + //
    //             "* It will still **receive focus, caret, and key events**, so typing works normally.\r\n" + //
    //             "* You can use this trick to overlay text input on top of images or game scenes.\r\n" + //
    //             "\r\n" + //
    //             "---\r\n" + //
    //             "\r\n" + //
    //             "Would you like me to show how to overlay a transparent `JTextArea` **on top of a background image or custom canvas** (like in a chat UI or game)?\r\n" + //
    //             "";
    public Response(String message, int ypos, boolean inc){
        this.message = message;
        this.y = ypos;
        // message = con;
        // this.message = con;
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(parser.parse(message));
        label = new JLabel();
        label.setVerticalAlignment(SwingConstants.TOP);
        // label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        label.setForeground(java.awt.Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 30));

        this.message = "<html>" + html + "</html>";
        label.setText(message);
        label.setBounds(x, y, 1000, 2000);
        Dimension size = label.getPreferredSize();
        Panel.nexty+=size.height+50;
        if(!inc){

        label.setText("");
        }else{
            index = message.length();
        }




    }

    public void draw(java.awt.Graphics2D g2){
        label.setBounds(label.getX(), y+Panel.changey, label.getWidth(), label.getHeight());
        if(index < message.length()){
            label.setText(message.substring(0,index++));

        }

        // g2.setColor(new Color(50, 50, 50, 200));
        // g2.fillRoundRect(x, y, 600, 100, 25, 25);
        // g2.setColor(Color.WHITE);
        // g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        // g2.drawString(message, x + 20, y + 30);


    }
}
