package vn.hsu.StudentInformationSystem.service.dto;

import java.time.LocalDate;

public class PhotocopyTransactionDTO {
    private LocalDate date;
    private long amount;

    public PhotocopyTransactionDTO() {
    }

    public PhotocopyTransactionDTO(LocalDate date, long amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
