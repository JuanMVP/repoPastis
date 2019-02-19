package com.st.pillboxapp.models;

import java.util.List;

public class ResponseContainer<T> {

    private int count;
    private List<T> rows;

    public ResponseContainer(){}
    public ResponseContainer(int count, List<T> rows) {
        this.count = count;
        this.rows = rows;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ResponseContainer{" +
                "count=" + count +
                ", rows=" + rows +
                '}';
    }
}
