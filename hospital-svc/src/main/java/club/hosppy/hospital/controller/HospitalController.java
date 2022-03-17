package club.hosppy.hospital.controller;

import club.hosppy.hospital.dto.HospitalDto;
import club.hosppy.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("/{hospitalId}")
    public HospitalDto getHospital(@PathVariable String hospitalId) {
        return hospitalService.get(hospitalId);
    }

    @GetMapping
    public Page<HospitalDto> listHospitals(Pageable pageRequest) {
        return hospitalService.list(pageRequest);
    }

    @PostMapping
    public HospitalDto createHospital(@RequestBody @Valid HospitalDto hospitalDto) {
        return hospitalService.create(hospitalDto);
    }

    @PutMapping
    public HospitalDto updateHospital(@RequestBody @Valid HospitalDto hospitalDto) {
        return hospitalService.update(hospitalDto);
    }
}
