package club.hosppy.hospital.controller;

import club.hosppy.hospital.dto.ScheduleDto;
import club.hosppy.hospital.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleDto create(@RequestBody @Valid ScheduleDto scheduleDto) {
        return scheduleService.create(scheduleDto);
    }

    @GetMapping("/{scheduleId}")
    public ScheduleDto getSchedule(@PathVariable Integer scheduleId) {
        return scheduleService.get(scheduleId);
    }

    @GetMapping
    public Page<ScheduleDto> listSchedules(Pageable pageRequest) {
        return scheduleService.list(pageRequest);
    }

    @PutMapping
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto) {
        return scheduleService.update(scheduleDto);
    }
}
