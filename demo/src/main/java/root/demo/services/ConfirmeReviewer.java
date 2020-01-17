package root.demo.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;
import root.demo.model.User;
import root.demo.repository.UserRepository;

@Service
public class ConfirmeReviewer implements JavaDelegate {

    @Autowired
    UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // TODO Auto-generated method stub
        List<FormSubmissionDto> registration = (List<FormSubmissionDto>) execution.getVariable("approvedrev");
        String approved = registration.get(0).getFieldValue();
        String username = registration.get(1).getFieldValue();

        List<User> allUsers = userRepository.findAll();

        if (!allUsers.isEmpty()) {
            for (User uu : allUsers) {
                if (uu.getUsername().equals(username)) {
                    if (!approved.equals("true")) {
                        uu.setRole("user");
                        userRepository.save(uu);
                        break;
                    }
                }
            }
        }
    }
}