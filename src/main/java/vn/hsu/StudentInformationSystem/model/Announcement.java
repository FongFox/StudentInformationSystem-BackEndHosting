package vn.hsu.StudentInformationSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.hsu.StudentInformationSystem.model.enumeration.AnnounceCategory;

@Entity
@Table(name = "announcements")
@Getter
@Setter
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(name = "link_url", columnDefinition = "TEXT")
    private String linkURL;

    @Column(name = "image_link_url", columnDefinition = "TEXT")
    private String imageLinkUrl;

    @Enumerated(EnumType.STRING)
    private AnnounceCategory category;

    public Announcement() {
    }
}
