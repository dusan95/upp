package root.demo.camunda.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import root.demo.model.*;
import root.demo.repository.*;

import java.util.HashMap;
import java.util.List;

@Service
public class TestHandler implements ExecutionListener {

    @Autowired
    private NaucneOblastiRepo naucneOblastiRepo;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MagazineRepository magazineRepository;

    @Autowired
    PretplateRepository pretplateRepository;

    @Autowired
    JournalPlanRepository journalPlanRepository;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Poceo proces obrade podnetog teksta");
        if(userRepository.findByUsername("mary") == null){
            userRepository.save(new User("Mary", "Mary", "City", "country", "title", "dusanmiljkovic_95@hotmail.com", "mary", "mary", "editor", "", "1,3"));
            userRepository.save(new User("John", "John", "City", "country", "title", "dusanmiljkovic_95@hotmail.com", "john", "john", "editor", "", "2,4,5"));
            userRepository.save(new User("Peter", "Peter", "City", "country", "title", "dusanmiljkovic_95@hotmail.com", "peter", "peter", "editor", "", "1,2"));
        }
        if(magazineRepository.findByName("casopis 1") == null){
            magazineRepository.save(new Magazine(true, "peter", "urednik1,urednik2", "recenzent1,recenzent2", "casopis 1", (long)111, "1,2", "Citaoci"));
            magazineRepository.save(new Magazine(true, "mary", "urednik1", "recenzent2,recenzent3", "casopis 2", (long)222, "3", "Autori"));
            magazineRepository.save(new Magazine(true, "john", "urednik2", "recenzent1", "casopis 3", (long)333, "2", "Autori"));
            pretplateRepository.save(new Pretplate("casopis 2", (long)222, "dusan"));
        }
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
    }
}
