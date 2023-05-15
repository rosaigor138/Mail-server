package com.example.smtpmailsender.mailSender;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {

    public  static void sendEmail(Session session, String toMail, String subject, String body) throws Exception{
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no-reply@example.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));
            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("Email sent Successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void sendAttachmentEmail(Session session, String toMail, String subject, String body,String fileName){
        try{
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type","text/HTML;charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding","8bit");

            msg.setFrom(new InternetAddress("no_reply@example","NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

            msg.setSubject(subject,"UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail,false));

            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            if (fileName.equals("C:\\Users\\Igor Rosa\\Desktop\\GITHUB-PROJECTS\\SMTP-MailSender\\src\\main\\java\\com\\example\\smtpmailsender\\resources\\rede.png")){
                messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent("<h1>Attached Image</h1>" +
                        "<img src='cid:image_id'>", "text/html");
                multipart.addBodyPart(messageBodyPart);
            }
            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("Email Sent Successfully with the "+fileName);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
