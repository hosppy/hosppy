package club.hosppy.hospital.service;

import club.hosppy.common.api.ResultCode;
import club.hosppy.common.error.ServiceException;
import club.hosppy.hospital.dto.HospitalDto;
import club.hosppy.hospital.model.Hospital;
import club.hosppy.hospital.repo.HospitalRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepo hospitalRepo;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public HospitalDto create(HospitalDto hospitalDto) {
        Hospital hospital = this.convertToModel(hospitalDto);

        try {
            Hospital newHospital = hospitalRepo.save(hospital);
            return this.convertToDto(newHospital);
        } catch (Exception e) {
            throw new ServiceException("Could not create hospital", e);
        }
    }

    public HospitalDto get(String hospitalId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).orElseThrow(() -> {
            throw new ServiceException(ResultCode.NOT_FOUND, "Hospital not found");
        });
        return this.convertToDto(hospital);
    }

    public Page<HospitalDto> list(Pageable pageRequest) {
        return hospitalRepo.findAll(pageRequest).map(this::convertToDto);
    }

    public HospitalDto update(HospitalDto hospitalDto) {
        Hospital existingHospital = hospitalRepo.findById(hospitalDto.getId()).orElseThrow(() -> {
            throw new ServiceException(ResultCode.NOT_FOUND, "Hospital not found");
        });
        entityManager.detach(existingHospital);

        Hospital hospitalToUpdate = this.convertToModel(hospitalDto);
        try {
            return this.convertToDto(hospitalRepo.save(hospitalToUpdate));
        } catch (Exception e) {
            throw new ServiceException("Could not update the hospitalDto", e);
        }
    }

    public Hospital convertToModel(HospitalDto hospitalDto) {
        return this.modelMapper.map(hospitalDto, Hospital.class);
    }

    public HospitalDto convertToDto(Hospital hospital) {
        return this.modelMapper.map(hospital, HospitalDto.class);
    }
}
