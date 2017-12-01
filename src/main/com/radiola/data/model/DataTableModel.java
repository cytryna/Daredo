package com.radiola.data.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by rwichrowski on 01.12.17.
 */
public class DataTableModel {

    private String name;
    private String description;
    private List<DataColumnModel> columnModel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataColumnModel> getColumnModel() {
        return Collections.unmodifiableList(columnModel);
    }

    public void setColumnModel(List<DataColumnModel> columnModel) {
        this.columnModel = columnModel;
    }
}