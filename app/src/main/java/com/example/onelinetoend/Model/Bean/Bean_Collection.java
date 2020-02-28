package com.example.onelinetoend.Model.Bean;

public class Bean_Collection {
    private int rows,columns,difficulties,sum=0;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void addSum(){
        sum++;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getDifficulties() {
        return difficulties;
    }

    public void setDifficulties(int difficulties) {
        this.difficulties = difficulties;
    }

    public Bean_Collection(int rows, int columns, int difficulties) {
        this.rows = rows;
        this.columns = columns;
        this.difficulties = difficulties;
    }

    public boolean equals(Bean_Collection collection) {
        return this.columns==collection.columns
                &&this.difficulties==collection.difficulties
                &&this.rows==collection.rows;
    }
}
