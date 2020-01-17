package root.demo.services;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.FormSubmissionDto;
import root.demo.model.Magazine;
import root.demo.repository.MagazineRepository;

import java.util.List;

@Service
public class SaveEditorsReviewers implements JavaDelegate {
    @Autowired
    IdentityService identityService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        List<FormSubmissionDto> magazine = (List<FormSubmissionDto>)execution.getVariable("form2");
        List<FormSubmissionDto> magazine1 = (List<FormSubmissionDto>)execution.getVariable("magazine");

        List<String> editors = magazine.get(0).getFieldListValue();
        List<String> reviewers = magazine.get(1).getFieldListValue();
        String nameMagazine = magazine1.get(0).getFieldValue();

        String reviewersToString = "";
        String editorsToString = "";

        for(String field: editors){
            editorsToString += field + ",";
        }

        if (editorsToString != null && editorsToString.length() > 0 && editorsToString.charAt(editorsToString.length() - 1) == ',') {
            editorsToString = editorsToString.substring(0, editorsToString.length() - 1);
        }

        for(String field: reviewers){
            reviewersToString += field + ",";
        }

        if (reviewersToString != null && reviewersToString.length() > 0 && reviewersToString.charAt(reviewersToString.length() - 1) == ',') {
            reviewersToString = reviewersToString.substring(0, reviewersToString.length() - 1);
        }

        try {

            //Magazine magazineFromDB = magazineRepository.findAll();
            List<Magazine> magazines = magazineRepository.findAll();
            for(Magazine temp : magazines){
                if(temp.getName().equals(nameMagazine)){
                    temp.setEditors(editorsToString);
                    temp.setReviewers(reviewersToString);
                    magazineRepository.save(temp);
                    System.out.println("Uspesno sacuvani urednici i recenzenti!");
                    break;
                }
            }
            //magazineRepository.save(newMagazine);
        }catch(NullPointerException e) {
            System.out.println("Urednici i recenzenti nisu sacuvani!");
        }
    }
}
