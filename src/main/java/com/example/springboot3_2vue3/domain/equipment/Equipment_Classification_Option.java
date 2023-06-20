package com.example.springboot3_2vue3.domain.equipment;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/20 12:26
 **/
public class Equipment_Classification_Option {
    private Long eid;

    private  int optionId;

    @Override
    public String toString() {
        return "Equipment_Classification_Option{" +
                "eid=" + eid +
                ", optionId=" + optionId +
                '}';
    }

    public Long getEid() {

        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
