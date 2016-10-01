package anakiou.com.picontrol.domain;

public class Output {

    private Long id;

    private String name;

    private Integer outputNumber;

    private Integer outputStatus;

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

    public Integer getOutputNumber() {
        return outputNumber;
    }

    public void setOutputNumber(Integer outputNumber) {
        this.outputNumber = outputNumber;
    }

    public Integer getOutputStatus() {
        return outputStatus;
    }

    public void setOutputStatus(Integer outputStatus) {
        this.outputStatus = outputStatus;
    }

    public Long getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Long dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
