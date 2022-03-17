package club.hosppy.hospital.service;

import club.hosppy.common.error.ServiceException;
import club.hosppy.hospital.dto.ScheduleDto;
import club.hosppy.hospital.model.Schedule;
import club.hosppy.hospital.repo.ScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepo scheduleRepo;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public ScheduleDto create(ScheduleDto scheduleDto) {
        Schedule schedule = this.convertToModel(scheduleDto);

        try {
            Schedule newSchedule = scheduleRepo.save(schedule);
            return this.convertToDto(newSchedule);
        } catch (Exception e) {
            throw new ServiceException("Could not create schedule", e);
        }
    }

    public ScheduleDto get(Integer scheduleId) {
        Schedule schedule = scheduleRepo.findById(scheduleId).orElseThrow(() -> {
            throw new ServiceException("Schedule not found");
        });
        return this.convertToDto(schedule);
    }

    public ScheduleDto update(ScheduleDto scheduleDto) {
        Schedule existingSchedule = scheduleRepo.findById(scheduleDto.getId()).orElseThrow(() -> {
            throw new ServiceException("Schedule not found");
        });
        entityManager.detach(existingSchedule);

        Schedule scheduleToUpdate = this.convertToModel(scheduleDto);
        try {
            return this.convertToDto(scheduleToUpdate);
        } catch (Exception e) {
            throw new ServiceException("Could not update scheduleDto", e);
        }
    }

    public Page<ScheduleDto> list(Pageable pageRequest) {
        return scheduleRepo.findAll(pageRequest).map(this::convertToDto);
    }

    public ScheduleDto convertToDto(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleDto.class);
    }

    public Schedule convertToModel(ScheduleDto scheduleDto) {
        return modelMapper.map(scheduleDto, Schedule.class);
    }
}
