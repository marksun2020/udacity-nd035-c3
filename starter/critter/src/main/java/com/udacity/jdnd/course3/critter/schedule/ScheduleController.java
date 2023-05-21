package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

//    https://knowledge.udacity.com/questions/725770

    private final ScheduleService service;


    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return this.service.save(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return this.service.getAllSchedules();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return this.service.getSchedulesForPet(petId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return this.service.getSchedulesForEmployee(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return this.service.getSchedulesForCustomer(customerId);
    }
}
