package root.demo.camunda.listeners;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.Magazine;
import root.demo.repository.MagazineRepository;

import java.util.List;

@Service
public class SetSelectedJournalCompleteListener implements TaskListener {

    @Autowired
    FormService formService;

    @Autowired
    MagazineRepository magazineRepository;

    public void notify(DelegateTask delegateTask) {
        List<FormField> formFieldList = formService.getTaskFormData(delegateTask.getId()).getFormFields();
        if (formFieldList != null) {
            for (FormField field : formFieldList) {
                if (field.getId().equals("casopis")) {
                    Magazine casopis = magazineRepository.getOne(Long.valueOf(field.getValue().getValue().toString()));
                    if(casopis.getPayment().equals("Autori")){
                        delegateTask.getExecution().setVariable("autorPlaca", true);
                    }else {
                        delegateTask.getExecution().setVariable("autorPlaca", false);
                    }
                    //delegateTask.getExecution().setVariable("casopis", casopisPV);
                }
            }
        }
    }
}
