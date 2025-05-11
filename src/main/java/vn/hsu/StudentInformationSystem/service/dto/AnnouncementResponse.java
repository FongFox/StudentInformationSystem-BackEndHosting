package vn.hsu.StudentInformationSystem.service.dto;

import vn.hsu.StudentInformationSystem.model.enumeration.AnnounceCategory;

public class AnnouncementResponse {
    private String title;
    private String linkURL;
    private String imageLinkUrl;
    private AnnounceCategory category;

    public AnnouncementResponse() {
    }

    public AnnouncementResponse(String title, String linkURL, String imageLinkUrl, AnnounceCategory category) {
        this.title = title;
        this.linkURL = linkURL;
        this.imageLinkUrl = imageLinkUrl;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getImageLinkUrl() {
        return imageLinkUrl;
    }

    public void setImageLinkUrl(String imageLinkUrl) {
        this.imageLinkUrl = imageLinkUrl;
    }

    public AnnounceCategory getCategory() {
        return category;
    }

    public void setCategory(AnnounceCategory category) {
        this.category = category;
    }
}
