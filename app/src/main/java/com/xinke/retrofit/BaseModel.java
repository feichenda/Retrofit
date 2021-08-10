package com.xinke.retrofit;

import java.util.List;

public class BaseModel<T> {
    private int code;
    private String message;
    private T data;
    private List<T> datas;

    public BaseModel(int code, String message, T data, List<T> datas) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.datas = datas;
    }

    public BaseModel() {
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", datas=" + datas +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
