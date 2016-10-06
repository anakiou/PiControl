package anakiou.com.picontrol.domain;

public class Output {

    private String name;

    private Integer outputNumber;

    private Integer outputStatus;

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

    public boolean getBoolStatus() {
        return outputStatus != null && outputStatus > 0;
    }
}
