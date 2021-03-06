package root.demo.camunda.serviceTasks;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class PosaljiMejlAutoruIGlavnomUredniku implements JavaDelegate {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Slanje mejla");
        String oneUser = (String) execution.getVariable("oneUser");
        Integer loopNo = (Integer) execution.getVariable("loops");
        /*if(loopNo == 2)
            send("upp201920","UPP2019#.", oneUser,"Uspesna registracija rada","Vas rad je poslat na pregled");
        else
            send("upp201920","UPP2019#.", oneUser,"Novi rad","Dobili ste novi rad na pregled.");*/
        execution.setVariable("loops",1);
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
