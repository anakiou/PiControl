package anakiou.com.picontrol.domain;

public class EventLog {

    private Long id;

    private String msg;

    private Boolean outputValue;

    private Integer pinNumber;

    private Long dateCreated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Boolean outputValue) {
        this.outputValue = outputValue;
    }

    public Integer getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Integer pinNumber) {
        this.pinNumber = pinNumber;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
