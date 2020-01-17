package root.demo.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.FormSubmissionDto;
import root.demo.model.User;
import root.demo.repository.UserRepository;

import java.util.List;

@Service
public class SaveRegistration implements JavaDelegate {


    @Autowired
    IdentityService identityService;

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
        String name = registration.get(0).getFieldValue();
        String lastName = registration.get(1).getFieldValue();
        String city = registration.get(2).getFieldValue();
        String country = registration.get(3).getFieldValue();
        String title = registration.get(4).getFieldValue();
        String email = registration.get(5).getFieldValue();
        String username = registration.get(6).getFieldValue();
        String password = registration.get(7).getFieldValue();
        String reviewer = registration.get(8).getFieldValue();
        List<String> sciFields = registration.get(9).getFieldListValue();

        String sciFieldsToString = "";

        for(String field: sciFields){
            sciFieldsToString += field + ",";
        }

        if (sciFieldsToString != null && sciFieldsToString.length() > 0 && sciFieldsToString.charAt(sciFieldsToString.length() - 1) == ',') {
            sciFieldsToString = sciFieldsToString.substring(0, sciFieldsToString.length() - 1);
        }

        User user = new User(name,lastName,city,country,title,email,username,password,"","",sciFieldsToString);

        if(reviewer.equals("true")){
            user.setRole("reviewer");
        }else{
            user.setRole("user");
        }

        try {
            userRepository.save(user);
            System.out.println("Uspesno sacuvan");
        }catch(NullPointerException e) {
            System.out.println("Nije sacuvano u bazu");
        }
    }
}
