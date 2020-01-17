package root.demo.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.NaucneOblasti;
import root.demo.repository.NaucneOblastiRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CreateNaucneOblastiHandler implements ExecutionListener {

    @Autowired
    private NaucneOblastiRepo naucneOblastiRepo;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Poceo proces");
        List<NaucneOblasti> naucneOblasti = naucneOblastiRepo.findAll();
        if(naucneOblasti.isEmpty()){
            naucneOblastiRepo.save(new NaucneOblasti("Bioloske nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Geo-nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Matematicke nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Fizicke nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Racunarske nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Arhitektura"));
            naucneOblastiRepo.save(new NaucneOblasti("Gradjevinsko inzenjerstvo"));
            naucneOblastiRepo.save(new NaucneOblasti("Tehnolosko inzenjerstvo"));
            naucneOblastiRepo.save(new NaucneOblasti("Ekonomscke nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Pravne nauke"));
            naucneOblastiRepo.save(new NaucneOblasti("Filozovija"));
        }
        naucneOblasti = naucneOblastiRepo.findAll();
        HashMap<String, String> lista = new HashMap<>();
        for( NaucneOblasti no : naucneOblasti){
            lista.put(no.getName(), no.getName());
        }

        delegateExecution.setVariable("sveNaucneOblasti", lista);
    }
}
