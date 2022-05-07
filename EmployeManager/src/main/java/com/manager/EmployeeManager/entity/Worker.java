package com.manager.EmployeeManager.entity;


import javax.persistence.*;

@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="job_title")
    private String jobTitle;

    @Column(name="id_of_team")
    private Integer idOfTeam;

    @Column(name = "id_of_boss")
    private Integer idOfBoss;

    public Worker() {
        idOfTeam = 0;
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getJobTitle() {
        return jobTitle;
    }

    public final void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public final Integer getIdOfTeam() {
        return idOfTeam;
    }

    public final void setIdOfTeam(Integer idOfTeam) {
        this.idOfTeam = idOfTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getIdOfBoss() {
        return idOfBoss;
    }

    public void setIdOfBoss(Integer idOfBoss) {
        this.idOfBoss = idOfBoss;
    }
}
