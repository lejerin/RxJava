package com.example.rxjava.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StockUpdateRoom {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "stock_symbol")
    public String STOCK_SYMBOL;

    @ColumnInfo(name = "price")
    public String PRICE;

    @ColumnInfo(name = "date")
    public String DATE;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setSTOCK_SYMBOL(String STOCK_SYMBOL) {
        this.STOCK_SYMBOL = STOCK_SYMBOL;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getSTOCK_SYMBOL() {
        return STOCK_SYMBOL;
    }

    public String getPRICE() {
        return PRICE;
    }

    public String getDATE() {
        return DATE;
    }
}
