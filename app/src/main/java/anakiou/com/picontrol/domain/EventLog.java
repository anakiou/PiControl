package anakiou.com.picontrol.domain;

public class EventLog {

    private String msg;

    private String ioName;

    private Integer ioValue;

    private Integer ioNumber;

    private Long dateCreated;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIoName() {
        return ioName;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    public Integer getIoValue() {
        return ioValue;
    }

    public void setIoValue(Integer ioValue) {
        this.ioValue = ioValue;
    }

    public Integer getIoNumber() {
        return ioNumber;
    }

    public void setIoNumber(Integer ioNumber) {
        this.ioNumber = ioNumber;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
