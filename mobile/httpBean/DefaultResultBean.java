package com.example.YunKeTong.httpBean;

public class DefaultResultBean<T> {
    /**
     * data : null
     * request_id : 请求id
     * result_code : http状态码，如200
     * result_desc : 状态码描述，如request successful
     */

    private T data;

    private String result_code;
    private String result_desc;

    private long request_id;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(long request_id) {
        this.request_id = request_id;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

}
