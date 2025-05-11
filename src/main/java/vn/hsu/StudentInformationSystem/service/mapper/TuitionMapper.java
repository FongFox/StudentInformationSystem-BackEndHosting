package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Tuition;
import vn.hsu.StudentInformationSystem.service.dto.TuitionResponse;

@Component
public class TuitionMapper {
    public TuitionResponse toDto(Tuition tuition) {
        return new TuitionResponse(tuition.getSemester().getCode(), tuition.getTotal(), tuition.getPaid(), tuition.getRefund(), tuition.getBalance(), tuition.isPaid());
    }
}
