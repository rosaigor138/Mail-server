package com.example.smtpmailsender.mailReceiver;

import javax.mail.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class MailReceiver {
    private static OutputStream outputStream;

    static {
        try {
            outputStream = new FileOutputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\input_text");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void receiveMail(Session session, String username, String password) throws Exception {
        Store store = session.getStore("pop3");
        store.connect("pop.gmail.com", username, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        if (messages != null) {
            for (Message message : messages) {
                message.writeTo(System.out);
                message.writeTo(outputStream);
            };
        }else {
            InputStream inputstream;
            try {
                inputstream = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\input_text");

                int data = inputstream.read();
                while (data != -1) {
                    System.out.println(data);

                    data = inputstream.read();

                }
                inputstream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        inbox.close(false);
        store.close();
    }
}