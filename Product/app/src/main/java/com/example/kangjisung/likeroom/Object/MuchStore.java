package com.example.kangjisung.likeroom.Object;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by pjh99 on 2017-01-20.
 */

public class MuchStore {
    String name;
    String date;
    int much;

    public MuchStore(String name, String date, int much) {
        this.name = name;
        this.date = date;
        this.much = much;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        return transFormat.parse(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMuch() {
        return much;
    }

    public void setMuch(int much) {
        this.much = much;
    }
}

