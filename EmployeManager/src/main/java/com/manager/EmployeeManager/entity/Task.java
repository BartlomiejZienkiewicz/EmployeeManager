package com.manager.EmployeeManager.entity;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_of_task")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "id_of_team")
    private Integer idOfTeam;

    @Column(name = "status")
    private String status;


    @Column(name = "minute_of_ending")
    private Integer minuteOfEnding;

    @Column(name = "hour_of_ending")
    private Integer hourOfEnding;

    @Column(name ="day_of_ending")
    private Integer dayOfEnding;

    @Column(name ="month_of_ending")
    private Integer monthOfEnding;

    @Column(name = "year_of_ending")
    private Integer yearOfEnding;

    @Column(name = "simple_date")
    private String simpleDate;

    public Task() {
        status = "active";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIdOfTeam() {
        return idOfTeam;
    }

    public void setIdOfTeam(int idOfTeam) {
        this.idOfTeam = idOfTeam;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMinuteOfEnding() {
        return minuteOfEnding;
    }

    public void setMinuteOfEnding(Integer minuteOfEnding) {
        this.minuteOfEnding = minuteOfEnding;
    }

    public Integer getHourOfEnding() {
        return hourOfEnding;
    }

    public void setHourOfEnding(Integer hourOfEnding) {
        this.hourOfEnding = hourOfEnding;
    }

    public Integer getDayOfEnding() {
        return dayOfEnding;
    }

    public void setDayOfEnding(Integer dayOfEnding) {
        this.dayOfEnding = dayOfEnding;
    }

    public Integer getMonthOfEnding() {
        return monthOfEnding;
    }

    public void setMonthOfEnding(Integer monthOfEnding) {
        this.monthOfEnding = monthOfEnding;
    }

    public Integer getYearOfEnding() {
        return yearOfEnding;
    }

    public void setYearOfEnding(Integer yearOfEnding) {
        this.yearOfEnding = yearOfEnding;
    }

    public String getSimpleDate() {
        return simpleDate;
    }

    public void setSimpleDate(String simpleDate) {
        this.simpleDate = simpleDate;
    }
}

