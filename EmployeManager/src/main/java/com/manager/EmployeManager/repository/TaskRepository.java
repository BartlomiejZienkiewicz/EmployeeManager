package com.manager.EmployeManager.repository;

import com.manager.EmployeManager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
