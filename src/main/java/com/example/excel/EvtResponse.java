package com.example.excel;


import org.springframework.stereotype.Repository;

import java.util.List;



public class EvtResponse {
    String date;
    List<Integer> count;

    public EvtResponse(String date, int[] ints) {
    }

    public EvtResponse(String date, List<Integer> count) {
        setDate(date);
        setCount(count);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getCount() {
        return count;
    }

    public void setCount(List<Integer> count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "EvtResponse{" +
                "date='" + date + '\'' +
                ", count=" + count +
                '}';
    }
}
