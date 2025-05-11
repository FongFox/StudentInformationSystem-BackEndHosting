package vn.hsu.StudentInformationSystem.service.impl;

import org.springframework.stereotype.Service;
import vn.hsu.StudentInformationSystem.model.Announcement;
import vn.hsu.StudentInformationSystem.repository.AnnouncementRepository;
import vn.hsu.StudentInformationSystem.service.AnnouncementService;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public List<Announcement> handleFetchAllAnnouncement() {
        return this.announcementRepository.findAll();
    }
}
