package com.hackerrank.sample.models.hackerrank;


import com.hackerrank.sample.models.hackerrank.Data;

import java.util.*;

public class Response {
    int total_pages;
    List<Data> data;

    public List<Data> getData() {
        return this.data;
    }

    public int getTotalPages() {
        return total_pages;
    }
}