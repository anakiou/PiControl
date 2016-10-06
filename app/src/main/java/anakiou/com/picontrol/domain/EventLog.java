/*
 * Copyright 2016 . Anargyros Kiourkos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

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
