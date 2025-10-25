package com.ui;

import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.networking.Client;

public class Panel extends JPanel implements ActionListener {
    private static JFrame frame = new JFrame("Semantic Compression");
    private static UserInputField inputArea;
    private static ArrayList<ChatBubble> chatBubbles = new ArrayList<>();
    private static ArrayList<Response> responses = new ArrayList<>();

    private static BufferedImage backgroundImage;
    private static BufferedImage base;

    public static int changey = 0;
    public static int nexty = 100;


    static {
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);


    }

    public void initGraphics(Client client) {
        try{
            backgroundImage = ImageIO.read(Panel.class.getResourceAsStream("/im/backgrounds/outline.png"));
            base = ImageIO.read(Panel.class.getResource("/im/backgrounds/base.png"));
        }catch(Exception e){
            e.printStackTrace();
        }

        setLayout(null);
        setSize(1920, 1080);
        inputArea = new UserInputField(this, client);

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                changey -= notches * 20;
                if(changey > 0) changey = 0;
                if(changey < - nexty+200) changey = -nexty+200;
            }

        });

        add(inputArea);
        
        frame.setContentPane(this);
        //frame.add(this);
        frame.setVisible(true);

        Timer t = new Timer(33, this);
        t.start();

    }

    public Panel(Client client) {
        initGraphics(client);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Panel(new Client());
        });
        

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(base, 545, 86, null);

        inputArea.draw(g2);
        for(ChatBubble cb : chatBubbles){
            cb.draw(g2);
        }

        for(Response r : responses){
           r.draw(g2);
        }


        g2.drawImage(backgroundImage, 0, 0, null);

    }   

    public void sendMessage(String message, Client client) {
        // JLabel label = new JLabel();
        // label.setText(message);
        // label.setBounds(500, 300, 400, 50);
        ChatBubble chatBubble = new ChatBubble(message, nexty);
        chatBubbles.add(chatBubble);
        add(chatBubble);
        System.out.println("asking server: "+message);
        client.sendMessage(message);
        responses.add(new Response(client.receiveMessages(), nexty));
        add(responses.get(responses.size()-1).label); 
        //add(label);

        // revalidate();
        // frame.revalidate();
    }

    @Override  
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
