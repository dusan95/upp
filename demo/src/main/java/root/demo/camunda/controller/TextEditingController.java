package root.demo.camunda.controller;

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
import root.demo.model.*;
import root.demo.repository.*;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/obradaTeksta")
public class TextEditingController {

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
    HistoryService historyService;

    @Autowired
    HashCodeRepository codeRepository;

    @Autowired
    ExternalTaskService externalTaskService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    PretplateRepository pretplateRepository;

    @Autowired
    JournalPlanRepository journalPlanRepository;

    @GetMapping(path = "/get/{username}", produces = "application/json")
    public @ResponseBody
    FormFieldsDto get( @PathVariable String username) {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("obrada_podnetog_teksta");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"username", username);
        User autor = userRepository.findByUsername(username);
        runtimeService.setVariable(processInstanceId,"startedUserVariable", username);
        runtimeService.setVariable(processInstanceId,"autorMail", autor.getEmail());

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/form/{taskId}/{username}")
    public ResponseEntity<Object> postProtected(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId,  @PathVariable String username) throws URISyntaxException {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId,"selectedCasopis", map);

        Integer idCasopisa = Integer.valueOf(dto.get(0).getFieldValue());
        User glavniUrednik = userRepository.getOne((long)idCasopisa);
        runtimeService.setVariable(processInstanceId, "glavniUrednikMail", glavniUrednik.getEmail());
        runtimeService.setVariable(processInstanceId,"glavniUrednikCasopisa", glavniUrednik.getUsername());


        formService.submitTaskForm(taskId, map);
        task = taskService.createTaskQuery().processInstanceId(processInstanceId).list().get(0);
        HashMap<String, String> returnMap = new HashMap<>();

        Magazine casopis = magazineRepository.getOne((long)idCasopisa);
        JournalPlan journalPlan = journalPlanRepository.findJournalPlanByJournal(casopis.getName());
        runtimeService.setVariable(processInstanceId, "journalPlanId", journalPlan.getId());
        Long id = (long) runtimeService.getVariable(processInstanceId, "journalPlanId");
        returnMap.put("journalPlanId", ""+id);
        if(task.getName().equals("Platite clanarinu"))
            returnMap.put("url", "uplatite_clanarinu");
        else
            returnMap.put("url", "unesite_rad");
        return new ResponseEntity<Object>(returnMap, HttpStatus.OK);
    }

    @GetMapping(path = "/form/get/{processInstance}", produces = "application/json")
    public @ResponseBody FormFieldsDto getService(@PathVariable String processInstance) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstance).list().get(0);
        String logedIn = (String) runtimeService.getVariable(processInstance, "username");
        if(task.getAssignee().equals(logedIn)) {
            TaskFormData tfd = formService.getTaskFormData(task.getId());
            List<FormField> properties = tfd.getFormFields();
            return new FormFieldsDto(task.getId(), processInstance, properties);
        }else{
            return new FormFieldsDto(task.getId(), processInstance, null);
        }
    }

    @GetMapping(path = "/pogledajRad/{processInstance}", produces = "application/json")
    public @ResponseBody FormFieldsDto pogledajRad(@PathVariable String processInstance) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstance).list().get(0);
        String logedIn = (String) runtimeService.getVariable(processInstance, "username");
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new FormFieldsDto(task.getId(), processInstance, properties);
    }

    @PostMapping(path = "/form/{taskId}")
    public ResponseEntity postData(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) throws URISyntaxException {
        HashMap<String, Object> map = this.mapListToDto(dto);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId,"podaciOk", true);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            if(temp.getFieldId().equals("naucneOblasti")) {
                if(temp.getFieldListValue().size()>0)
                    map.put(temp.getFieldId(), temp.getFieldListValue().get(0));
                else
                    map.put(temp.getFieldId(), temp.getFieldValue());
            }
            else
                map.put(temp.getFieldId(), temp.getFieldValue());
        }
        return map;
    }
}
