package com.example.smtpmailsender.mailReceiver;

import javax.mail.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class MailReceiver {
    private static OutputStream outputStream;

    static {
        try {
            outputStream = new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\input_text");
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
            }
        }
        BufferedReader in =
                new BufferedReader(new FileReader(System.getProperty("user.dir") +
                        "\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\input_text"));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();


        inbox.close(false);
        store.close();
    }
}