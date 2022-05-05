package com.manager.EmployeManager.service;

import com.manager.EmployeManager.entity.Team;
import com.manager.EmployeManager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public final List<Team> getAll(){
        try {
            List<Team> teamList = teamRepository.findAll();
            return teamList;

        }
        catch (Exception e){
            return null;
        }
    }

    public final void deleteTeamById(Integer id){
        String sql = "DELETE FROM teams WHERE id = "+id+";";
        jdbcTemplate.update(sql);
        String sql2 = "UPDATE workers SET id_of_team = NULL WHERE id_of_team ="+id+";";
        jdbcTemplate.update(sql2);
    }
    public final void changeTeamOfGroup(Integer id, String nameOfGroup){
        String sql = "UPDATE teams SET name = "+nameOfGroup+" WHERE id ="+id+";";
        jdbcTemplate.update(sql);
    }
    public List<Team> getTeamsByIdOfOwner(Integer id){
        List<Team> teamList = teamRepository.findAll().stream().filter(team ->team.getIdOfUser().equals(id)).collect(Collectors.toList());
        return teamList;
    }
    public void saveTeam(Team team){
        teamRepository.save(team);

    }
    public Team getTeamById(Integer id){
        Team team = new Team();
        team = teamRepository.getById(id);
        return team;
    }


    public List<Team> getTeamsByUsernameOfOwner(String username) {
        Integer idOfOwner = userService.getIdOfUserByHisUsername(username);
        List<Team> teamList = getTeamsByIdOfOwner(idOfOwner);
        return teamList;
    }
}
