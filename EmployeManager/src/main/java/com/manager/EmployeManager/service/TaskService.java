package com.manager.EmployeManager.service;

import com.manager.EmployeManager.entity.Task;
import com.manager.EmployeManager.entity.Worker;
import com.manager.EmployeManager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        tasks = taskRepository.findAll();
        return tasks;
    }

    public Task findTaskById(Integer id) {
        Task task = new Task();
        task = taskRepository.findAll().stream().filter(task1 -> task1.getId().equals(id)).findFirst().get();
        return task;
    }

    public void giveTaskToTeam(Integer idOfTeam, Integer idOfTask) {
        String sql = "UPDATE tasks SET id_of_team =" + idOfTeam + " WHERE id = " + idOfTask + " ;";
        jdbcTemplate.update(sql);

    }

    public void saveTask(String name, String description, Integer idOfTeam) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setIdOfTeam(idOfTeam);

        taskRepository.save(task);
    }

    public void saveTask(String name, Integer idOfTeam) {

        Task task = new Task();
        task.setIdOfTeam(idOfTeam);
        task.setName(name);
        taskRepository.save(task);
    }
    public void changeIdOfTeam(Integer idOfTask, Integer idOfTeam){
        String sql = "UPDATE tasks SET id_of_team = "+idOfTeam +" WHERE id= " + idOfTask + ";";
        jdbcTemplate.update(sql);
    }

    public Task findTaskByIdOfTeam(Integer idOfTeam) {
        List<Task> tasks = findAll();
        Optional<Task> task = Optional.of(tasks.stream().filter(task1 -> task1.getIdOfTeam().equals(idOfTeam)).findFirst().get());
        if (task.isPresent()){
            return task.get();
        }

        return null;
    }

    public List<Task> findActiveTasksByIdOfTeam(Integer idOfTeam) {
        List<Task> tasks = new ArrayList<>();
        tasks =
                findAll().stream().filter(task -> task.getIdOfTeam().equals(idOfTeam) && task.getStatus().equals(
                        "active")).collect(Collectors.toList());
        return tasks;
    }

    public void deleteTaskById(Integer taskId) {
        taskRepository.deleteById(taskId);
    }
    public void saveTask(Task task){
        taskRepository.save(task);
    }

    public void setTaskAsUnfinished(Integer taskId) {

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day  = calendar.get(Calendar.DAY_OF_MONTH); // OR Calendar.DATE
        int month= calendar.get(Calendar.MONTH) + 1;
        int year= calendar.get(Calendar.YEAR);



        Task newTask = findTaskById(taskId);
        newTask.setStatus("unfinished");

        newTask.setMinuteOfEnding(dt.getMinutes());
        newTask.setHourOfEnding(dt.getHours());
        newTask.setDayOfEnding(day);
        newTask.setMonthOfEnding(month);
        newTask.setYearOfEnding(year);

        String simpleDate = dt.getHours()+":"+
                dt.getMinutes()+"  "+
                day+"/"+
                month+"/"+
                year;
        newTask.setSimpleDate(simpleDate);

        deleteTaskById(taskId);
        saveTask(newTask);


    }

    public void setTaskAsFinished(Integer taskId) {

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day  = calendar.get(Calendar.DAY_OF_MONTH); // OR Calendar.DATE
        int month= calendar.get(Calendar.MONTH) + 1;
        int year= calendar.get(Calendar.YEAR);



        Task newTask = findTaskById(taskId);
        newTask.setStatus("finished");

        newTask.setMinuteOfEnding(dt.getMinutes());
        newTask.setHourOfEnding(dt.getHours());
        newTask.setDayOfEnding(day);
        newTask.setMonthOfEnding(month);
        newTask.setYearOfEnding(year);

        String simpleDate = dt.getHours()+":"+
                dt.getMinutes()+"  "+
                day+"/"+
                month+"/"+
                year;
        newTask.setSimpleDate(simpleDate);

        deleteTaskById(taskId);
        saveTask(newTask);
    }

    public List<Task> findInactiveTasks(Integer teamId) {
        List<Task> tasks = findAll().stream().filter(task -> task.getIdOfTeam().equals(teamId) &&
                                                                !task.getStatus().equals("active")).
                                                                collect(Collectors.toList());
        return tasks;
    }
}
