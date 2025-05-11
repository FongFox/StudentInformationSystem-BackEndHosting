package vn.hsu.StudentInformationSystem.service.dto;

import java.util.List;

public class PhotocopyResponse {
    private long photocopyBalance;
    private List<PhotocopyTransactionDTO> photocopyTransactionDTOList;

    public PhotocopyResponse() {
    }

    public PhotocopyResponse(long photocopyBalance) {
        this.photocopyBalance = photocopyBalance;
    }

    public PhotocopyResponse(long photocopyBalance, List<PhotocopyTransactionDTO> photocopyTransactionDTOList) {
        this.photocopyBalance = photocopyBalance;
        this.photocopyTransactionDTOList = photocopyTransactionDTOList;
    }

    public long getPhotocopyBalance() {
        return photocopyBalance;
    }

    public void setPhotocopyBalance(long photocopyBalance) {
        this.photocopyBalance = photocopyBalance;
    }

    public List<PhotocopyTransactionDTO> getPhotocopyTransactionDTOList() {
        return photocopyTransactionDTOList;
    }

    public void setPhotocopyTransactionDTOList(List<PhotocopyTransactionDTO> photocopyTransactionDTOList) {
        this.photocopyTransactionDTOList = photocopyTransactionDTOList;
    }
}
