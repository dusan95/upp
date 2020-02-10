package root.demo.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Magazine;
import root.demo.model.User;
import root.demo.repository.MagazineRepository;
import root.demo.repository.UserRepository;

import java.util.List;

@Service
public class SaveMagazine implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("magazine");

        String name = magazine.get(0).getFieldValue();
        String issn = magazine.get(1).getFieldValue();
        Long issnLong = Long.parseLong(issn);
        String payment = magazine.get(2).getFieldListValue().get(0);
        List<String> sciFields = magazine.get(3).getFieldListValue();
        String chiefEditor = magazine.get(4).getFieldValue();

        String sciFieldsToString = "";

        for(String field: sciFields){
            sciFieldsToString += field + ",";
        }

        if (sciFieldsToString != null && sciFieldsToString.length() > 0 && sciFieldsToString.charAt(sciFieldsToString.length() - 1) == ',') {
            sciFieldsToString = sciFieldsToString.substring(0, sciFieldsToString.length() - 1);
        }

        execution.setVariable("sciFieldsToString",sciFieldsToString);

        Magazine newMagazine = new Magazine(name,issnLong,sciFieldsToString, payment);
        newMagazine.setActive(false);
        newMagazine.setChiefEditor(chiefEditor);

        try {
            magazineRepository.save(newMagazine);
            System.out.println("Casopis uspesno sacuvan");
        }catch(NullPointerException e) {
            System.out.println("Casopis nije sacuvan u bazu");
        }
    }
}
