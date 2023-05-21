package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleService {
    private final SchedulerRepository scheduleRepository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(SchedulerRepository scheduleRepository, PetRepository petRepository, EmployeeRepository employeeRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
    }

    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        return ScheduleDTO.fromEntity(scheduleRepository.save(scheduleDTO.toEntity()));
    }

    public List<ScheduleDTO> getAllSchedules() {
        return this.scheduleRepository.findAll().stream().map(ScheduleDTO::fromEntity).collect(Collectors.toList());
    }

        public List<ScheduleDTO> getSchedulesForPet(long petId) {
            List<Schedule> schedules = this.petRepository
                    .findById(petId)
                    .map(p -> this.scheduleRepository.findAllByPetId(p.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(String.format("A pet with id '%d' not found", petId)));

            return schedules.stream().map(ScheduleDTO::fromEntity).collect(Collectors.toList());
        }

        public List<ScheduleDTO> getSchedulesForEmployee(long employeeId) {
            List<Schedule> schedules = this.employeeRepository
                    .findById(employeeId)
                    .map(e -> this.scheduleRepository.findAllByEmployeeId(e.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(String.format("An employee with id '%d' not found", employeeId)));

            return schedules.stream().map(ScheduleDTO::fromEntity).collect(Collectors.toList());
        }

    public List<ScheduleDTO> getSchedulesForCustomer(long customerId) {
        List<Schedule> schedules = this.customerRepository
                .findById(customerId)
                .map(c -> this.scheduleRepository.findAllByCustomerId(c.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("An employee with id '%d' not found", customerId)));

        return schedules.stream().map(ScheduleDTO::fromEntity).collect(Collectors.toList());
    }
}
