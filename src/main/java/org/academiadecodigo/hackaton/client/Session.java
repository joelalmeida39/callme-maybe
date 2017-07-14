package org.academiadecodigo.hackaton.client;

import org.academiadecodigo.hackaton.client.controller.ControllerGame1;
import org.academiadecodigo.hackaton.client.service.game.GameService;
import org.academiadecodigo.hackaton.shared.Message;
import org.academiadecodigo.hackaton.shared.Type;

import java.io.*;
import java.net.Socket;

/**
 * @author by André Martins <Code Cadet>
 *         Project stalkers (13/07/17)
 *         <Academia de Código_>
 */
public class Session {

    private static Session instance;

    private String username;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Session() {
        connect();
    }

    private void connect() {

        try {

            socket = new Socket("localhost", 9999);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println(in);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(Message message) {
        System.out.println("----++++++ Write Object Before");
        try {
            out.writeObject(message);
            out.flush();
            System.out.println("-----+++++ Write Object after");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object read() {

        Message<String> message = null;
        System.out.println("OOOOOOOOOON READ MSG");

        try {

            message = (Message<String>) in.readObject();
            System.out.println("AFTER READ MSG");
            System.out.println(message.getContent() + " " + message.getType());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;

    }

    public void close() {

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Session getInstance() {

        if (instance == null) {

            synchronized (Session.class) {

                if (instance == null) {
                    instance = new Session();
                }

            }

        }

        return instance;

    }

    public void setUsername(String username) {
        this.username = username;
    }

}
