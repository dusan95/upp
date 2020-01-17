package root.demo.services;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.demo.model.FormSubmissionDto;

@Service
public class TestService implements JavaDelegate{

	@Autowired
	IdentityService identityService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("pocinje validacija");
		List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
		boolean rez = true;

		for (FormSubmissionDto oneData : registration ) {
			if(oneData.getFieldId().equals("reviewer")){
				if (oneData.getFieldValue().isEmpty()){
					oneData.setFieldValue("false");
				}
			}
			else if (!oneData.getFieldId().equals("titula") && !oneData.getFieldId().equals("naucneOblasti")){
				if (oneData.getFieldValue().isEmpty()){
					rez = false;
					break;
				}
			}
			else if(oneData.getFieldId().equals("naucneOblasti")){
				int velicina = oneData.getFieldListValue().size();
				if(velicina > 0) {
				}else{
					rez = false;
					break;
				}
			}
		}
		execution.setVariable("rez", rez);
	}
}
