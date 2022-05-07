package com.manager.EmployeeManager.entity;

import javax.persistence.*;

@Entity
@Table(name="teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="id_of_owner")
    private Integer idOfUser;

    @Column(name="id_of_task")
    private Integer idOfTask;


    public Team() {
        idOfTask = 0;
    }


    public Integer getIdOfTask() {
        return idOfTask;
    }

    public void setIdOfTask(Integer idOfTask) {
        this.idOfTask = idOfTask;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdOfUser() {
        return idOfUser;
    }

    public void setIdOfUser(Integer idOfUser) {
        this.idOfUser = idOfUser;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idOfUser=" + idOfUser +
                '}';
    }
}
