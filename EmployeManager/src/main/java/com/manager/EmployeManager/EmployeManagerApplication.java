package com.manager.EmployeManager;

import com.manager.EmployeManager.entity.Task;
import com.manager.EmployeManager.entity.Team;
import com.manager.EmployeManager.entity.User;
import com.manager.EmployeManager.entity.Worker;
import com.manager.EmployeManager.repository.TeamRepository;
import com.manager.EmployeManager.repository.UserRepository;
import com.manager.EmployeManager.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@SpringBootApplication
public class EmployeManagerApplication implements CommandLineRunner{

	@Autowired
	private WorkerService workerService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;

	@Autowired
	private TeamRepository teamRepository;


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SignUpService signUpService;


	public static void main(String[] args) {
		SpringApplication.run(EmployeManagerApplication.class, args);
	}

	public void firstMethod(){
		String sql = "DROP TABLE IF EXISTS workers;\n" +
				"CREATE TABLE workers(id serial PRIMARY KEY," +
				" name VARCHAR(50)," +
				" last_name VARCHAR(50)," +
				" job_title VARCHAR(255)," +
				" id_of_team INTEGER," +
				" id_of_boss INTEGER);";
		jdbcTemplate.update(sql);

		String sql2 = "DROP TABLE IF EXISTS tasks;\n" +
				"CREATE TABLE tasks(id serial PRIMARY KEY," +
				" name_of_task VARCHAR (50)," +
				"description VARCHAR (200)," +
				"id_of_team INTEGER NOT NULL, " +
				"status VARCHAR(50)," +
				"minute_of_ending INTEGER," +
				"hour_of_ending INTEGER," +
				"day_of_ending INTEGER," +
				"month_of_ending INTEGER," +
				"year_of_ending INTEGER," +
				"simple_date VARCHAR(50)" +
				");";
		jdbcTemplate.update(sql2);

		String sql3 = "DROP TABLE IF EXISTS users;\n" +
				"CREATE TABLE users(id serial PRIMARY KEY,username VARCHAR (50) ,password VARCHAR (100) , name " +
				"VARCHAR " +
				"(50),last_name VARCHAR (50)," +
				"role VARCHAR (50),email VARCHAR (50) ,telephone_number INTEGER, enabled BOOLEAN," +
				" confirmation_token VARCHAR (50)" +
				");";
		jdbcTemplate.update(sql3);

		String sql4 = "DROP TABLE IF EXISTS teams;\n" +
				"CREATE TABLE teams(id serial PRIMARY KEY," +
				" name VARCHAR(255)," +
				" id_of_owner INTEGER," +
				"id_of_task VARCHAR(50));";
		jdbcTemplate.update(sql4);

		String sql5 = "CREATE TABLE IF NOT EXISTS workers (\n" +
				"   id serial PRIMARY KEY," +
				"	name VARCHAR(50)," +
				"	last_name VARCHAR(50)," +
				"   job_title VARCHAR(50)," +
				"   id_of_team INTEGER," +
				"   id_of_boss INTEGER );";
		jdbcTemplate.update(sql5);

		String sql6 = "CREATE TABLE IF NOT EXISTS teams(\n" +
				"id serial PRIMARY KEY," +
				"name VARCHAR (255), " +
				"id_of_owner INTEGER," +
				"id_of_task VARCHAR(50));" ;

		jdbcTemplate.update(sql6);

		String sql7 = "CREATE TABLE IF NOT EXISTS tasks(\n" +
				"id serial PRIMARY KEY," +
				"name_of_task VARCHAR (50)," +
				"description VARCHAR (200)," +
				"id_of_team INTEGER NOT NULL," +
				"status VARCHAR(50)," +
				"minute_of_ending INTEGER," +
				"hour_of_ending INTEGER," +
				"day_of_ending INTEGER," +
				"month_of_ending INTEGER," +
				"year_of_ending INTEGER," +
				"simple_date VARCHAR(50)" +
				");";
		jdbcTemplate.update(sql7);

		String sql8 = "CREATE TABLE IF NOT EXISTS users" +
				"(id serial PRIMARY KEY,username VARCHAR (50),password VARCHAR (100) , name VARCHAR " +
				"(50)," +
				"last_name VARCHAR (50),role VARCHAR (50),email VARCHAR(50) " +
				",telephone_number INTEGER, enabled BOOLEAN, confirmation_token VARCHAR (50));";
		jdbcTemplate.update(sql8);
	}




	@Override
	public void run(String... args) throws Exception {
		firstMethod();








	}
}
