package com.manager.EmployeeManager.service;

import com.manager.EmployeeManager.entity.Worker;
import com.manager.EmployeeManager.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    public List<Worker> getAll(){
        return workerRepository.findAll();
    }
    public Worker findById(Integer id){
        List<Worker> workerList = getAll();

        try{
            Worker worker = workerList.stream().filter(worker1 -> worker1.getId().equals(id)).findFirst().get();
            return worker;
        }
        catch (Exception e){
            return null;
        }
    }
    public final List<Worker> findByIdOfTeam(Integer id){
        List<Worker> workerList = getAll().stream().filter(worker -> worker.getIdOfTeam().equals(id)).collect(Collectors.toList());
        return workerList;
    }

    public List<Worker> findByJobTitleOfWorker(String work){
        List<Worker> workerList = getAll().stream().filter(worker -> worker.getJobTitle().equals(worker)).collect(Collectors.toList());
        return workerList;
    }
    public List<Worker> findWorkersByTeamId(Integer id){
        List<Worker> workerList =
                getAll().stream().filter(worker -> worker.getIdOfTeam().equals(id)).collect(Collectors.toList());
        return workerList;
    }

    public void saveWorker(Worker worker){
        workerRepository.save(worker);
    }

    public void giveWorkerNewTeamById(Integer idOfWorker, Integer teamId){
        String sql = "UPDATE workers SET id_of_team = "+teamId+" WHERE id = "+idOfWorker+" ;";
        jdbcTemplate.update(sql);
    }
    public void deleteWorkerById(Integer id){
        workerRepository.deleteById(id);
    }

    public Integer getNumberOfWorkersInTeam(Integer idOfTeam){
        List<Worker> workerList = getAll();
        Integer numberOfWorkers = 0;
        for (Worker worker:workerList) {
            if(worker.getIdOfTeam().equals(idOfTeam)){
                numberOfWorkers++;
                System.out.println(numberOfWorkers);
            }
        }

        return numberOfWorkers;

    }


    public void deleteWorkersFromTeam(Integer idOfTeam) {
        List<Worker> workerList = getAll();
        for(Worker worker: workerList){
            if(worker.getIdOfTeam().equals(idOfTeam)){
                worker.setIdOfTeam(null);
            }
        }
    }

    public Integer updateWorkerAndGiveNewId(Integer intIdOfWorker, String name, String lastName, String jobTitle) {
        Worker worker = findById(intIdOfWorker);

        worker.setName(name);
        worker.setLastName(lastName);
        worker.setJobTitle(jobTitle);

        deleteWorkerById(intIdOfWorker);

        workerRepository.save(worker);

        Integer newid = getAll().size();
        return newid;
    }

    public List<Worker> getWorkersThatWorkForUser(String username){
        List<Worker> finalWorkerList = new ArrayList<>();
        Integer idOfBoss = userService.getIdOfUserByHisUsername(username);
        List<Worker> allWorkers = getAll();

        for(Worker worker: allWorkers){
            if(worker.getIdOfBoss().equals(idOfBoss)){
                finalWorkerList.add(worker);
            }
        }

        return finalWorkerList;

    }



    public List<Worker> findWorkersFromOtherTeams(Integer idOfTeam, Integer idOfBoss) {

        List<Worker> result = new ArrayList<Worker>();
        List<Worker> allWorkers = getAll();

        for (Worker worker: allWorkers){
            if(!worker.getIdOfTeam().equals(idOfTeam) && worker.getIdOfBoss().equals(idOfBoss)){
                result.add(worker);
            }
        }

        return result;
    }
}
