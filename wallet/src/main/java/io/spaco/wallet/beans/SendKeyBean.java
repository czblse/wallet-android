package io.spaco.wallet.beans;

/**
 * @author: lh on 2018/1/25 15:15.
 * Email:luchefg@gmail.com
 * Description: SendKey弹出框信息展示
 */

public class SendKeyBean {

    private String skyNum;
    private String skyDollar;
    private String status;
    private String form;
    private String date;
    private String notes;
    private String to;
    private String time;


    public String getSkyNum() {
        return skyNum;
    }

    public void setSkyNum(String skyNum) {
        this.skyNum = skyNum;
    }

    public String getSkyDollar() {
        return skyDollar;
    }

    public void setSkyDollar(String skyDollar) {
        this.skyDollar = skyDollar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SendKeyBean{" +
                "skyNum='" + skyNum + '\'' +
                ", skyDollar='" + skyDollar + '\'' +
                ", status='" + status + '\'' +
                ", form='" + form + '\'' +
                ", date='" + date + '\'' +
                ", notes='" + notes + '\'' +
                ", to='" + to + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
