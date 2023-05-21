package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee, Long> {
    @Query(value = "SELECT e.* FROM employee e INNER JOIN employee_days_available eda ON e.id = eda.employee_id WHERE eda.days_available = :availableDate", nativeQuery = true)
    List<Employee> finAllByDaysAvailable(@Param("availableDate") String date);
}
