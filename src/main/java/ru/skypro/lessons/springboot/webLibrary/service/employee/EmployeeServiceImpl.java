package ru.skypro.lessons.springboot.webLibrary.service.employee;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Employee;
import ru.skypro.lessons.springboot.webLibrary.domains.entity.Position;
import ru.skypro.lessons.springboot.webLibrary.models.dto.EmployeeDTO;
import ru.skypro.lessons.springboot.webLibrary.models.mapper.EmployeeDTOMapper;
import ru.skypro.lessons.springboot.webLibrary.models.projections.EmployeesInfo;
import ru.skypro.lessons.springboot.webLibrary.repository.EmployeeRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static ru.skypro.lessons.springboot.webLibrary.utility.Validation.modelValidation;

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
        return employeeRepository.returnMinSalaryEmployees().stream().map(EmployeeDTOMapper::fromEmployee).toList();
    }

    @Override
    public List<EmployeeDTO> getMaxSalaryEmployee() {
        return employeeRepository.returnMaxSalaryEmployees().stream().map(EmployeeDTOMapper::fromEmployee).toList();
    }

    @Override
    public List<EmployeeDTO> getAboveAveragePaidEmployees() {
        return employeeRepository.getAboveAveragePaidEmployees().stream().map(EmployeeDTOMapper::fromEmployee).toList();
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithSalaryMoreThan(double salary) {
        modelValidation(salary);
        return employeeRepository.findAllBySalaryIsBiggerThan(salary).stream().map(EmployeeDTOMapper::fromEmployee).toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {
        modelValidation(id);
        return EmployeeDTOMapper.fromEmployee(employeeRepository.getById(id));
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        modelValidation(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public void changeEmployeeById(EmployeeDTO employeeDTO, Integer id) {
        Employee resultEmployee = EmployeeDTOMapper.toEmployee(employeeDTO);
        modelValidation(resultEmployee);
        resultEmployee.setId(employeeRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new)
                .getId());
        employeeRepository.save(resultEmployee);
    }

    @Override
    public void createEmployees(List<EmployeesInfo> listOfNewEmployeesDTO) {
        if (listOfNewEmployeesDTO != null) {
            List<Employee> resultList = new ArrayList<>();
            for (EmployeesInfo employeesInfo : listOfNewEmployeesDTO) {
                Position position = employeeRepository.findPositionByName(employeesInfo.getPositionName());
                resultList.add(new Employee(
                        employeesInfo.getName(),
                        employeesInfo.getSalary(),
                        position != null ?
                                position :
                                new Position(employeesInfo.getPositionName())));
            }
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
                    .map(EmployeeDTOMapper::fromEmployee)
                    .toList();
        } else {
            try {
                return employeeRepository.returnAllByPositionId(Integer.parseInt(position)) //не знаю что лучше это (try) или instance of
                        .stream()
                        .map(EmployeeDTOMapper::fromEmployee)
                        .toList();
            } catch (NumberFormatException e) {
                return employeeRepository.returnAllByPositionName(position)
                        .stream()
                        .map(EmployeeDTOMapper::fromEmployee)
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
            return employeeRepository.findAll(pageRequest).stream().map(EmployeeDTOMapper::fromEmployee).toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void createEmployeesByJson(MultipartFile file) throws IOException {
        List<EmployeeDTO> list = new ObjectMapper().readValue(file.getInputStream(), new TypeReference<>() {
        });
        employeeRepository.saveAll(list.stream()
                .map(EmployeeDTOMapper::toEmployee)
                .toList());
    }
}
