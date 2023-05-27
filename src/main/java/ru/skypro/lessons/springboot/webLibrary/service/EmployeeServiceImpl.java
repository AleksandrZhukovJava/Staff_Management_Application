package ru.skypro.lessons.springboot.webLibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.projections.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.pojo.Employee;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import ru.skypro.lessons.springboot.webLibrary.utility.ModelValidation;

import java.util.List;
import java.util.stream.StreamSupport;

import static ru.skypro.lessons.springboot.webLibrary.utility.ModelValidation.modelValidation;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public double getSalarySum() {
        return employeeRepository.returnSumSalary();
    }

    @Override
    public List<EmployeeDTO> getMinSalaryEmployee() {
        return employeeRepository.returnMinSalaryEmployees().stream().map(EmployeeDTO::fromEmployee).toList();
    }

    @Override
    public List<EmployeeDTO> getMaxSalaryEmployee() {
        return employeeRepository.returnMaxSalaryEmployees().stream().map(EmployeeDTO::fromEmployee).toList();
    }

    @Override
    public List<EmployeeDTO> getAboveAveragePaidEmployees() {
        return employeeRepository.getAboveAveragePaidEmployees().stream().map(EmployeeDTO::fromEmployee).toList();
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithSalaryMoreThan(double salary) throws IllegalArgumentException {
        modelValidation(salary);
        return employeeRepository.findAllBySalaryIsBiggerThan(salary).stream().map(EmployeeDTO::fromEmployee).toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) throws IllegalArgumentException {
        modelValidation(id);
        return EmployeeDTO.fromEmployee(employeeRepository.getById(id));
    }

    @Override
    public void deleteEmployeeById(Integer id) throws IllegalArgumentException {
        modelValidation(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public void changeEmployeeById(EmployeeDTO employeeDTO, Integer id) throws IllegalArgumentException {
        Employee resultEmployee = employeeDTO.toEmployee();
        modelValidation(resultEmployee);
        resultEmployee.setId(employeeRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getId());
        employeeRepository.save(resultEmployee);
    }

    @Override
    public void createEmployees(List<EmployeeDTO> listOfNewEmployeesDTO) throws IllegalArgumentException {
        if (listOfNewEmployeesDTO != null) {
            List<Employee> resultList = listOfNewEmployeesDTO.stream().map(EmployeeDTO::toEmployee).toList();
            resultList.forEach(ModelValidation::modelValidation);
            employeeRepository.saveAll(resultList);
        } else throw new IllegalArgumentException();
    }

    @Override
    public List<EmployeesInfo> findAllEmployeesView() {
        return employeeRepository.findAllEmployeesView();
    }

    @Override
    public List<EmployeeDTO> returnEmployeesByPosition(String position) {
        if (position == null || position.isBlank()) {
            return StreamSupport.stream(employeeRepository.findAll().spliterator(), false)
                    .toList()
                    .stream()
                    .map(EmployeeDTO::fromEmployee)
                    .toList();
        } else {
            try {
                return employeeRepository.returnAllByPositionId(Integer.parseInt(position)) //не знаю что лучше это (try) или instance of
                        .stream()
                        .map(EmployeeDTO::fromEmployee)
                        .toList();
            } catch(NumberFormatException e){
                return employeeRepository.returnAllByPositionName(position)
                        .stream()
                        .map(EmployeeDTO::fromEmployee)
                        .toList();
            }
        }
    }

    @Override
    public List<EmployeesInfo> returnAllEmployeesView(Integer id) {
        modelValidation(id);
        return employeeRepository.findEmployeeByIdView(id);
    }

    @Override
    public List<EmployeeDTO> returnEmployeesByPageNumber(String number) {
        try {
            int pageNumber = number.isBlank() ? 0 : Integer.parseInt(number);
            Pageable pageRequest = PageRequest.of(pageNumber, 10);
            return employeeRepository.findAll(pageRequest).stream().map(EmployeeDTO::fromEmployee).toList();
        } catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }
}
