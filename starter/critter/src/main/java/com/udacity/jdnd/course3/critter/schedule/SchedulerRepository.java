package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchedulerRepository extends JpaRepository<Schedule, Long> {
    @Query(value = "SELECT sch.* FROM schedule sch INNER JOIN schedule_pet sp ON sch.id = sp.schedule_id WHERE sp.pet_id = :petId", nativeQuery = true)
    public List<Schedule> findAllByPetId(long petId);

    @Query(value = "SELECT sch.* FROM schedule sch INNER JOIN schedule_employee se ON sch.id = se.schedule_id WHERE se.employee_id = :employeeId", nativeQuery = true)
    List<Schedule> findAllByEmployeeId(long employeeId);

    @Query(value =
            "SELECT sch.* FROM schedule sch INNER JOIN schedule_pet sp ON sch.id = sp.schedule_id INNER JOIN pet p ON sp.pet_id = p.id WHERE p.customer_id = :customerId",
          nativeQuery = true)
    public List<Schedule> findAllByCustomerId(long customerId);
}
