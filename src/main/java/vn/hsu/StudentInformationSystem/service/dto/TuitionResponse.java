package vn.hsu.StudentInformationSystem.service.dto;

public class TuitionResponse {
    private long semesterCode;
    private long total;
    private long paid;
    private long refund;
    private long balance;
    private boolean isPaid;

    public TuitionResponse() {
    }

    public TuitionResponse(long semesterCode, long total, long paid, long refund, long balance, boolean isPaid) {
        this.semesterCode = semesterCode;
        this.total = total;
        this.paid = paid;
        this.refund = refund;
        this.balance = balance;
        this.isPaid = isPaid;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(long semesterCode) {
        this.semesterCode = semesterCode;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPaid() {
        return paid;
    }

    public long getRefund() {
        return refund;
    }

    public void setRefund(long refund) {
        this.refund = refund;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(long paid) {
        this.paid = paid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
