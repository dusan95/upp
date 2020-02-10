package root.demo.camunda.serviceTasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.JournalPlan;
import root.demo.model.Magazine;
import root.demo.model.Pretplate;
import root.demo.repository.JournalPlanRepository;
import root.demo.repository.MagazineRepository;
import root.demo.repository.PretplateRepository;

@Service
public class ProveriPretplatu implements JavaDelegate  {
    @Autowired
    PretplateRepository pretplateRepository;

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    JournalPlanRepository journalPlanRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //dohvati ulogovanog korisnika
        String idCasopisa = (String) execution.getVariable("casopis");
        String username = (String) execution.getVariable("username");
        Integer id = Integer.valueOf(idCasopisa);
        Magazine casopis = magazineRepository.getOne((long)id);
        Pretplate pretplata = pretplateRepository.getByCasopisIssnAndAndUserName(casopis.getIssn(), username);
        if(pretplata != null)
            execution.setVariable("x", true);
        else
            execution.setVariable("x", false);
    }
}
