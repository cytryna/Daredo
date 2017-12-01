package com.radiola;

import com.radiola.data.model.DataColumnModel;
import com.radiola.data.model.DataTableModel;
import com.radiola.msdoc.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rwichrowski on 01.12.17.
 */
public class DaredoTest {

    private List<DataTableModel> dataTableModels;

    @Before
    public void genereteList() {
        dataTableModels = new ArrayList<>();

        List<DataColumnModel> columnModels = new ArrayList<DataColumnModel>();

        DataColumnModel columnModel1 = new DataColumnModel();
        columnModel1.setName("ColumnName1");
        columnModel1.setDataType("Datatype1");
        columnModel1.setPrimaryKey(true);
        columnModel1.setForeignKey(false);
        columnModel1.setNullable(true);
        columnModel1.setDescription("Descrption1");

        DataColumnModel columnModel2 = new DataColumnModel();
        columnModel2.setName("ColumnName2");
        columnModel2.setDataType("Datatype2");
        columnModel2.setPrimaryKey(false);
        columnModel2.setForeignKey(true);
        columnModel2.setNullable(true);
        columnModel2.setDescription("Descrption2");

        columnModels.add(columnModel1);
        columnModels.add(columnModel2);

        DataTableModel tableModel1 = new DataTableModel();
        tableModel1.setName("tableName1");
        tableModel1.setDescription("opis1");
        tableModel1.setColumnModel(columnModels);

        dataTableModels.add(tableModel1);
    }

    @Test
    public void test_generate_report() {
        //given
        Document doc = new Document(dataTableModels);
        //when
        doc.generateRaport("testReport.docx");
        //then

    }

}