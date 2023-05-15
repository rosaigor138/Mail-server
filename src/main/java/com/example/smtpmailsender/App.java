package com.example.smtpmailsender;

import com.example.smtpmailsender.mailReceiver.MailReceiver;
import com.example.smtpmailsender.mailSender.MailSender;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        final String fromMail = "testederedes1406@gmail.com";
        final String password = "cezgwsdoxrhrbgxi";

        System.out.println("Email sender Start");
        Properties SMTPprops = new Properties();
        SMTPprops.put("mail.smtp.host", "smtp.gmail.com");
        SMTPprops.put("mail.smtp.socketFactory.port", "465");
        SMTPprops.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        SMTPprops.put("mail.smtp.auth", "true");
        SMTPprops.put("mail.smtp.port", "465");

        Properties POP3props = new Properties();
        POP3props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        POP3props.put("mail.pop3.socketFactory.fallback", "false");
        POP3props.put("mail.pop3.socketFactory.port", "995");
        POP3props.put("mail.pop3.port", "995");
        POP3props.put("mail.pop3.host", "pop.gmail.com");
        POP3props.put("mail.pop3.user", fromMail);
        POP3props.put("mail.store.protocol", "pop3");
        POP3props.put("mail.pop3.ssl.protocols", "TLSv1.2");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, password);
            }
        };
        boolean sendAndReceive = true;
        Scanner scan = new Scanner(System.in);
        int att = 0;
        String toMail = "";
        String subject = "";
        String body = "";
        while (sendAndReceive){
            int receiveOrSend = 0;
            System.out.println("""
                    1- To receive Mails
                    2- To send Mails""");
            receiveOrSend = scan.nextInt();
            if (receiveOrSend == 1){
                Session POP3session = Session.getDefaultInstance(POP3props, auth);
                System.out.println("Session created");
                MailReceiver.receiveMail(POP3session, fromMail, password);
                receiveOrSend = 0;
            }
            else{
                Session SMTPsession = Session.getDefaultInstance(SMTPprops, auth);
                System.out.println("Session created");
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
                    if (choice == 1){MailSender.sendAttachmentEmail(SMTPsession,toMail,subject,body,
                            "C:\\Users\\Igor Rosa\\Desktop\\GITHUB-PROJECTS\\SMTP-MailSender\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\rede.png");}
                    else{ MailSender.sendAttachmentEmail(SMTPsession,toMail,subject,body,
                            "C:\\Users\\Igor Rosa\\Desktop\\GITHUB-PROJECTS\\SMTP-MailSender\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\attachment.txt");}
                }else {
                    MailSender.sendEmail(SMTPsession, toMail, subject, body);
                }
                System.out.println("""
                            You wanna send another Email or receive the emails?
                            True to yes
                            False to no
                            """);
                sendAndReceive = scan.nextBoolean();
                receiveOrSend = 0;
        }}
    }
}
