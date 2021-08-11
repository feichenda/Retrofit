package com.xinke.retrofit;

import java.util.List;

/**
 * @author feizai
 * @date 2021/8/11 0011 PM 6:32:46
 */
public class OutTask {
    private List<String> targetList;
    private List<String> detailList;
    private String stockOutTime;

    public OutTask() {
    }

    public OutTask(List<String> targetList, List<String> detailList, String stockOutTime, String stockOutInfo) {
        this.targetList = targetList;
        this.detailList = detailList;
        this.stockOutTime = stockOutTime;
        this.stockOutInfo = stockOutInfo;
    }

    @Override
    public String toString() {
        return "OutTask{" +
                "targetList=" + targetList +
                ", detailList=" + detailList +
                ", stockOutTime='" + stockOutTime + '\'' +
                ", stockOutInfo='" + stockOutInfo + '\'' +
                '}';
    }

    public List<String> getTargetList() {
        return targetList;
    }

    public void setTargetList(List<String> targetList) {
        this.targetList = targetList;
    }

    public List<String> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<String> detailList) {
        this.detailList = detailList;
    }

    public String getStockOutTime() {
        return stockOutTime;
    }

    public void setStockOutTime(String stockOutTime) {
        this.stockOutTime = stockOutTime;
    }

    public String getStockOutInfo() {
        return stockOutInfo;
    }

    public void setStockOutInfo(String stockOutInfo) {
        this.stockOutInfo = stockOutInfo;
    }

    private String stockOutInfo;
}
