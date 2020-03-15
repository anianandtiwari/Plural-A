package com.plurals.android.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.plurals.android.Activity.MainActivity;
import com.plurals.android.Activity.OtpActivity;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void, Void, Void> {

    //Declaring Variables
    private Context context;
    private Session session;


    //Information to send email
    private String email = "volunteer.plurals@gmail.com";
    private String subject , message, attachment;
    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendMail(Context context, String subject , String message , String attachment ){
        //Initializing variables
        this.context = context;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
    }
    public SendMail(Context context, String subject , String message ){
        //Initializing variables
        this.context = context;
        this.subject = subject;
        this.message = message;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        /*progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();*/
        //progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false);
    }

   /* @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        //progressDialog.dismiss();


        //Showing a success message
       // Toast.makeText(context,"Thanks!!"+"\n"+"your content has been sent for Review", Toast.LENGTH_LONG).show();
    }*/

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Constants.EMAIL, Constants.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Constants.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);

            BodyPart messagebody = new MimeBodyPart();
            messagebody.setText(message);

            Multipart multipart = new MimeMultipart();
            if (attachment!=null) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                mimeBodyPart.setDataHandler(new DataHandler(source));
                mimeBodyPart.setFileName(new File(attachment).getName());
                multipart.addBodyPart(mimeBodyPart);
            }

            multipart.addBodyPart(messagebody);

             mm.setContent(multipart);
            //Sending email
            Transport.send(mm);
            Log.d("mail","mail Sent");

        } catch (MessagingException e) {
            Log.d("mail","mail failed");
            e.printStackTrace();
        }
        return null;
    }
}