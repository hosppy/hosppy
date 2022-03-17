package club.hosppy.hospital.controller;


import club.hosppy.hospital.dto.DepartmentDto;
import club.hosppy.hospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public DepartmentDto create(@RequestBody @Valid DepartmentDto departmentDto) {
        return departmentService.create(departmentDto);
    }

    @GetMapping("/{departmentId}")
    public DepartmentDto getDepartment(@PathVariable String departmentId) {
        return departmentService.get(departmentId);
    }

    @GetMapping
    public Page<DepartmentDto> listDepartments(Pageable pageRequest) {
        return departmentService.list(pageRequest);
    }

    @PutMapping
    public DepartmentDto updateDepartment(DepartmentDto departmentDto) {
        return departmentService.update(departmentDto);
    }
}
