package com.udacity.jdnd.course3.critter.user;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
         return CustomerDTO.fromEntity(this.userService.saveCustomer(customerDTO.toEntity()));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return this.userService.getAllCustomers().stream().map(CustomerDTO::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return CustomerDTO.fromEntity(this.userService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return EmployeeDTO.fromEntity(this.userService.saveEmployee(employeeDTO.toEntity()));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws EntityNotFoundException {
        return EmployeeDTO.fromEntity(this.userService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.userService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return this.userService.getEmployeesForService(
                employeeRequestDTO.getSkills(),
                employeeRequestDTO.getDate()).
            stream().
            map(EmployeeDTO::fromEntity).
            collect(Collectors.toList());
    }


}
