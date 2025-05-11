package vn.hsu.StudentInformationSystem.service.mapper;

import org.springframework.stereotype.Component;
import vn.hsu.StudentInformationSystem.model.Announcement;
import vn.hsu.StudentInformationSystem.service.dto.AnnouncementResponse;

@Component
public class AnnouncementMapper {
    public AnnouncementResponse toDto(Announcement announcement) {
        return new AnnouncementResponse(
                announcement.getTitle(),
                announcement.getLinkURL(),
                announcement.getImageLinkUrl(),
                announcement.getCategory()
        );
    }
}
