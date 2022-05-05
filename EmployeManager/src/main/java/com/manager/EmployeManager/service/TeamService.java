package com.manager.EmployeManager.service;

import com.manager.EmployeManager.entity.Group;
import com.manager.EmployeManager.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

import java.sql.JDBCType;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Group> getAll(){
        try {
            List<Group> groupList = groupRepository.findAll();
            return groupList;

        }
        catch (Exception e){
            return null;
        }
    }
    public void createNewGroup(String nameOfGroup){
        String sql = "INSERT INTO groups (name) VALUES ('"+nameOfGroup+"');";
        jdbcTemplate.update(sql);
    }
    public void deleteGroupById(Integer id){
        String sql = "DELETE FROM groups WHERE id = "+id+";";
        jdbcTemplate.update(sql);
    }
    public void changeNameOfGroup(Integer id, String nameOfGroup){
        String sql = "UPDATE groups SET name = "+nameOfGroup+" WHERE id ="+id+";";
        jdbcTemplate.update(sql);
    }




}
