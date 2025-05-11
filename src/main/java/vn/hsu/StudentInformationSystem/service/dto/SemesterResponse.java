package vn.hsu.StudentInformationSystem.service.dto;

public class SemesterResponse {
    private long code;
    private int year;
    private String shortDescription;

    public SemesterResponse() {
    }

    public SemesterResponse(long code, int year, String shortDescription) {
        this.code = code;
        this.year = year;
        this.shortDescription = shortDescription;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
