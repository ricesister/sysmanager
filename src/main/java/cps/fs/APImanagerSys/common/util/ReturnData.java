package cps.fs.APImanagerSys.common.util;


import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * 返回数据包装类
 * @author fs
 * @date 2018年9月12日
 * @description
 * @param <T>
 */
public  class ReturnData<T> {
    private Integer total;

    private String list;

    private String result;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getList(Class<T> t) {
        return JSONArray.parseArray(list,t);
    }

    public void setList(String list) {
        this.list = list;
    }

    public List<T> getResult(Class<T> t) {
        return JSONArray.parseArray(result,t);
    }

    public void setResult(String result) {
        this.result = result;
    }
}
