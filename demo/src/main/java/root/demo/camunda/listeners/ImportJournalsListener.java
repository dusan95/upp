package root.demo.camunda.listeners;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.Magazine;
import root.demo.repository.MagazineRepository;

import java.util.List;
import java.util.Map;

@Service
public class ImportJournalsListener implements TaskListener {
    @Autowired
    FormService formService;

    @Autowired
    MagazineRepository magazineRepository;

    @Override
    public void notify(DelegateTask delegateTask) {

        List<FormField> formFieldList=formService.getTaskFormData(delegateTask.getId()).getFormFields();
        if(formFieldList!=null){
            for(FormField field : formFieldList){
                if( field.getId().equals("casopis")){
                    Map<String,String> items = ((EnumFormType)field.getType()).getValues();
                    for(Magazine casopis : magazineRepository.findAll()){
                        if(casopis.getActive())
                            items.put(casopis.getId().toString(),casopis.getName());
                    }
                }

            }
        }
    }
}
