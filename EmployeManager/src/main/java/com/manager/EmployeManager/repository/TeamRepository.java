package com.manager.EmployeManager.repository;

import com.manager.EmployeManager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
