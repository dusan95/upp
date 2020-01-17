package root.demo.controller;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import root.demo.model.FormFieldsDto;
import root.demo.model.FormSubmissionDto;
import root.demo.repository.HashCodeRepository;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/casopis")
public class MagazineController {
    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    HashCodeRepository codeRepository;

    @Autowired
    ExternalTaskService externalTaskService;

    @GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody
    FormFieldsDto get() {

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("KreiranjeCasopisa");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "magazine", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/addEditorsAndReviewers/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity addEditorsAndReviewers(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "form2", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/unos-recenzenata-i-urednika/{processInstanceId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getForm2(@PathVariable String processInstanceId) {

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).
                taskDefinitionKey("unosRecenzenataiUrednika").singleResult();;

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDto(task.getId(), "", properties);
    }

    @GetMapping(path = "/getActivateMagazineForm/{processInstanceId}", produces = "application/json")
    public @ResponseBody FormFieldsDto getActivateForm(@PathVariable String processInstanceId) {

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).
                taskDefinitionKey("admin_provera").singleResult();;

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDto(task.getId(), "", properties);
    }

    @PostMapping(path = "/putActivateMagazine/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity putActivateMagazine(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {

        HashMap<String, Object> map = this.mapListToDto(dto);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "activateMagazine", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            if(temp.getFieldId().equals("scientificFields"))
                map.put(temp.getFieldId(), temp.getFieldListValue().get(0));
            else if(temp.getFieldId().equals("payment"))
                map.put(temp.getFieldId(), temp.getFieldListValue().get(0));
            else if(temp.getFieldId().equals("reviewers"))
                map.put(temp.getFieldId(), temp.getFieldListValue().get(0));
            else if(temp.getFieldId().equals("editors"))
                map.put(temp.getFieldId(), temp.getFieldListValue().get(0));
            else
                map.put(temp.getFieldId(), temp.getFieldValue());
        }
        return map;
    }
}
