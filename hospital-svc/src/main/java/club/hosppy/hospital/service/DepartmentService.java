package club.hosppy.hospital.service;

import club.hosppy.common.error.ServiceException;
import club.hosppy.hospital.dto.DepartmentDto;
import club.hosppy.hospital.model.Department;
import club.hosppy.hospital.repo.DepartmentRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepo departmentRepo;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public DepartmentDto create(DepartmentDto departmentDto) {
        Department department = this.convertToModel(departmentDto);

        try {
            Department newDepartment = departmentRepo.save(department);
            return this.convertToDto(newDepartment);
        } catch (Exception e) {
            throw new ServiceException("Could not create department", e);
        }
    }

    public DepartmentDto get(String departmentId) {
        Department department = departmentRepo.findById(departmentId).orElseThrow(() -> {
            throw new ServiceException("Department not found");
        });
        return this.convertToDto(department);
    }

    public DepartmentDto update(DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepo.findById(departmentDto.getId()).orElseThrow(() -> {
            throw new ServiceException("Department not found");
        });
        entityManager.detach(existingDepartment);

        Department departmentToUpdate = this.convertToModel(departmentDto);
        try {
            return this.convertToDto(departmentToUpdate);
        } catch (Exception e) {
            throw new ServiceException("Could not update departmentDto", e);
        }
    }

    public Page<DepartmentDto> list(Pageable pageRequest) {
        return departmentRepo.findAll(pageRequest).map(this::convertToDto);
    }

    public DepartmentDto convertToDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }

    public Department convertToModel(DepartmentDto departmentDto) {
        return modelMapper.map(departmentDto, Department.class);
    }
}
