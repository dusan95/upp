package root.demo.services;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.HashCodeConfirm;
import root.demo.model.User;
import root.demo.repository.HashCodeRepository;
import root.demo.repository.UserRepository;

@Service
public class SendMail implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HashCodeRepository hashRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Slanje mejla");
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
        String email = registration.get(5).getFieldValue();
        String username = registration.get(6).getFieldValue();

        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(registration.get(6).getFieldValue());

        HashCodeConfirm code = new HashCodeConfirm(username, sha256hex, "ne");

        try {
            hashRepository.save(code);
            System.out.println("Uspesno sacuvan u bazi");
        }catch(NullPointerException e) {
            System.out.println("Nije sacuvano u bazi");
        }

        send("upp201920","UPP2019#.", email,"Potvrda ragistracije","Da bi potvrdili registraciju kliknite na link ispod: \n http://localhost:8080/welcome/checkHash/" + sha256hex + "/" + username);
    }

    public void send(String from,String password,String to,String sub,String msg){
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("Poruka je uspesno poslata");
        } catch (MessagingException e) {throw new RuntimeException(e);}
    }
}
