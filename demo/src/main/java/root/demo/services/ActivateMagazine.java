package root.demo.services;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.repository.MagazineRepository;

import java.util.List;

@Service
public class ActivateMagazine implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("activateMagazine");
        String activated = magazine.get(0).getFieldValue();
        String nameMagazine = magazine.get(1).getFieldValue();

        List<Magazine> magazines = magazineRepository.findAll();

        if (!magazines.isEmpty()) {
            for (Magazine temp : magazines) {
                if (temp.getName().equals(nameMagazine)) {
                    Boolean act = activated == "true" ? true : false;
                    temp.setActive(act);
                    magazineRepository.save(temp);
                    break;
                }
            }
        }
    }
}
