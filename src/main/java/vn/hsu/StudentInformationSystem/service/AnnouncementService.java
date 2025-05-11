package vn.hsu.StudentInformationSystem.service;

import vn.hsu.StudentInformationSystem.model.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> handleFetchAllAnnouncement();
}
