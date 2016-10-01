package anakiou.com.picontrol.domain;


public class Input {

    private Long id;

    private String name;

    private Integer inputNumber;

    private Integer inputStatus;

    private Long dateUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInputNumber() {
        return inputNumber;
    }

    public void setInputNumber(Integer inputNumber) {
        this.inputNumber = inputNumber;
    }

    public Integer getInputStatus() {
        return inputStatus;
    }

    public void setInputStatus(Integer inputStatus) {
        this.inputStatus = inputStatus;
    }

    public Long getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Long dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
