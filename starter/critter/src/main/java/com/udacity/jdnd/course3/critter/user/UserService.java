package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        final List<Pet> pets = this.petRepository.findAll();
        final List<Customer> customers = this.customerRepository.findAll();
        customers.forEach(c -> c.setPets(pets.stream().filter(p -> p.getOwner().getId().equals(c.getId())).collect(Collectors.toSet())));

        return customers;
    }

    @Transactional
    public Customer getOwnerByPet(long petId) {
        Customer customer = this.customerRepository.getCustomerByPetId(petId);
        Set<Pet> pets = this.petRepository.getPetsByOwnerId(customer.getId());
        customer.setPets(pets);

        return customer;
    }

    public Employee getEmployee(long employeeId) throws EntityNotFoundException {
        return this.employeeRepository.
                        findById(employeeId).
                        orElseThrow(() -> new EntityNotFoundException(String.format("An employee with id '%d' is not found", employeeId)));
    }

    public List<Employee> getEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {
        return
                this.employeeRepository.
                finAllByDaysAvailable(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase()).stream().
                    filter(e -> e.getSkills().containsAll(skills)).
                    collect(Collectors.toList());
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) throws EntityNotFoundException {
        final Employee employee =getEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }
}
