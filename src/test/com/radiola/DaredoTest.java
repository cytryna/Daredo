package com.radiola;

import com.radiola.data.model.DataColumnModel;
import com.radiola.data.model.DataTableModel;
import com.radiola.doc.model.DocumentModel;
import com.radiola.msdoc.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rwichrowski on 01.12.17.
 */
public class DaredoTest {

    private List<DataTableModel> dataTableModels;
    private DocumentModel docModel;

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
        columnModel1.setDescription("Description1");

        DataColumnModel columnModel2 = new DataColumnModel();
        columnModel2.setName("ColumnName2");
        columnModel2.setDataType("Datatype2");
        columnModel2.setPrimaryKey(false);
        columnModel2.setForeignKey(true);
        columnModel2.setNullable(true);
        columnModel2.setDescription("Description2");

        columnModels.add(columnModel1);
        columnModels.add(columnModel2);

        DataTableModel tableModel1 = new DataTableModel();
        tableModel1.setName("tableName1");
        tableModel1.setDescription("opis1");
        tableModel1.setColumnModel(columnModels);

        dataTableModels.add(tableModel1);
    }

    @Before
    public void generateDocumentModel() {
        docModel = new DocumentModel();
        docModel.setAuthor("beholder");
        docModel.setCreationYear(2017);
        docModel.setDocTitle("Database documentation");
        docModel.setVersion("1.0");
    }

    @Test
    public void test_generate_report() {
        //given

        //when
        Document doc = new Document(docModel, dataTableModels);
        doc.generateRaport("testReport.docx");
        //then

    }
}