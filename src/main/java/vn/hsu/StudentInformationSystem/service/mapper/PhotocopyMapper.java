package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.PhotocopyTransaction;
import vn.hsu.StudentInformationSystem.model.Student;
import vn.hsu.StudentInformationSystem.service.dto.PhotocopyResponse;
import vn.hsu.StudentInformationSystem.service.dto.PhotocopyTransactionDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class PhotocopyMapper {
    public PhotocopyResponse toDto(Student student, List<PhotocopyTransaction> photocopyTransactionList) {
        PhotocopyResponse photocopyResponse = new PhotocopyResponse(student.getPhotocopyBalance());

        List<PhotocopyTransactionDTO> photocopyTransactionDTOList = new ArrayList<>();
        for (PhotocopyTransaction photocopyTransaction : photocopyTransactionList) {
            photocopyTransactionDTOList.add(
                    new PhotocopyTransactionDTO(
                            photocopyTransaction.getDate(),
                            photocopyTransaction.getAmount()
                    )
            );
        }
        photocopyResponse.setPhotocopyTransactionDTOList(photocopyTransactionDTOList);

        return photocopyResponse;
    }
}
