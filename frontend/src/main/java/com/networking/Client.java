package com.networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {

    public static final int PORT = 24483;
    public static final String IP_ADDRESS = "100.68.170.117";

    public Socket socket;

    public BufferedInputStream is;
    public BufferedOutputStream os;

    public ObjectMapper mapper = new ObjectMapper();

    public ClientInfo myInfo;

    public Client() {

        myInfo = new ClientInfo("John Wick");
        try {
            System.out.println("attempting to connect to server at " + IP_ADDRESS + " on port " + PORT);
            socket = new Socket(IP_ADDRESS, PORT);
            os = new BufferedOutputStream(socket.getOutputStream());
            is = new BufferedInputStream(socket.getInputStream());
            System.out.println("connected to server!");
            System.out.println("sending info: ");

            os.write(mapper.writeValueAsString(myInfo).getBytes());
            os.flush();


            // os.write(mapper.writeValueAsString(s.nextLine()).getBytes());
            // os.flush();
            

            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // public void sendMessage(String message) {
    //     try {
    //         os.write(mapper.writeValueAsString(message).getBytes());
    //         os.flush();
    //     } catch (IOException e) {
    //         e.printStackTrace();

    //     }
    // }
    public String receiveMessages() {

        while (true) {

            try {
                byte[] buffer = new byte[4096];

                is.read(buffer);
                String received = new String(buffer).trim();
                System.out.println("rec: "+received);
                Packet packet = mapper.readValue(received, Packet.class);
                System.out.println("received message: " + packet.getMessage()+ " of type "+packet.getType());

                return packet.getMessage();
                // if(packet.getType() == -1) {
                //    System.out.println("done");
                //     break;
                // }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    // public void sendMessage(String message) {
    //     try {
    //         os.write(mapper.writeValueAsString(message).getBytes());
    //         os.flush();
    //         receiveMessages();
    //     } catch (IOException e) {
    //         e.printStackTrace();

    //     }
    // }
    public void sendMessage(String message) {
        Message msg = new Message("" + System.currentTimeMillis(), message, 10);
        try {
            os.write(mapper.writeValueAsString(msg).getBytes());
            os.flush();
            receiveMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Response {
        private String role;
        private String content;
        private int type;
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
        public String getContent() {
            return content;
        }
        public Response() {
        }
        public Response(String role, String content, int type) {
            this.role = role;
            this.content = content;
            this.type = type;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }

        


    }
    public static class ClientInfo {

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ClientInfo(String id) {
            this.id = id;
        }

    }

    public static class Packet {

        private String message;
        private int type;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Packet() {

        }

    }

    public static class Message {
        private String timestamp;
        private String content;
        private int limit =10;

        public Message() {
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public Message(String timestamp, String content, int limit) {
            this.timestamp = timestamp;
            this.content = content;
            this.limit = limit;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }
}
