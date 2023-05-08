package com.example.smtpmailsender;

import com.example.smtpmailsender.mailSender.MailSender;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws MessagingException {
        final String fromMail = "testederedes1406@gmail.com";
        final String password = "cezgwsdoxrhrbgxi";

        System.out.println("Email sender Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, password);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");
        boolean send = true;
        Scanner scan = new Scanner(System.in);
        int att = 0;
        String toMail = "";
        String subject = "";
        String body = "";
        while (send){
            att = 0;
            System.out.println("""
                    Enter the email you wanna send a message, his subject and body
                    First insert the Email
                    Second insert the subject
                    Third insert the body
                    And if you wanna select the attachment
                    """);
            System.out.println("""
                    You wanna send with attachment ?
                    1- Yes
                    2- No""");
            att = scan.nextInt();
            System.out.println("Enter the mail");
            toMail = scan.next();
            System.out.println("Now enter the subject");
            subject = scan.next();
            System.out.println("Now enter the body");
            body = scan.next();
            if(att == 1){
                System.out.println("""
                        1 to img
                        2 to txt""");
                int choice = 0;
                choice = scan.nextInt();
                if (choice == 1){MailSender.sendAttachmentEmail(session,toMail,subject,body,
                        "C:\\Users\\Igor Rosa\\Desktop\\GITHUB-PROJECTS\\SMTP-MailSender\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\rede.png");}
                else{ MailSender.sendAttachmentEmail(session,toMail,subject,body,
                        "C:\\Users\\Igor Rosa\\Desktop\\GITHUB-PROJECTS\\SMTP-MailSender\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\attachment.txt");}
            }else {
                MailSender.sendEmail(session, toMail, subject, body);
            }
            System.out.println("""
                        You wanna send another Email?
                        True to yes
                        False to no
                        """);
            send = scan.nextBoolean();
        }
    }
}
