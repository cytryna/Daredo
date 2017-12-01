package com.radiola.msdoc;

import com.radiola.data.model.DataColumnModel;
import com.radiola.data.model.DataTableModel;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by aswiecicki on 01.12.17.
 */
public class Document {

    private static Logger logger = LoggerFactory.getLogger(Document.class);

    private static final String BOOLEAN_TRUE_VALUE = "X";
    private static final String BOOLEAN_FALSE_VALUE = "";

    private FileOutputStream out;
    private XWPFDocument document;
    private List<DataTableModel> modelList;

    public Document(List<DataTableModel> modelList) {
        this.modelList = modelList;

        document = new XWPFDocument();

        addTOCToDocument();

        processDatabaseModel();
    }

    /**
     * generate Table of Content
     * to znaczy spsis tresci
     */
    private void addTOCToDocument() {
        document.createTOC();
    }

    private void processDatabaseModel() {
        modelList.forEach(this::processTableModel);
    }

    private void processTableModel(DataTableModel tableModel) {
        XWPFParagraph nameParagraph = document.createParagraph();
        nameParagraph.setStyle("Tier1Header");
        XWPFRun run1 = nameParagraph.createRun();
        run1.setText(tableModel.getName());

        XWPFParagraph descParagraph = document.createParagraph();
        XWPFRun run2 = descParagraph.createRun();
        run2.setText(tableModel.getDescription());

        XWPFTable table = document.createTable();
        addInfoRow(table.getRow(0));
        tableModel.getColumnModel().forEach(this::processColumnModel);
    }

    private void processColumnModel(DataColumnModel columnModel) {
        XWPFTableRow tableRow = table.createRow();
        tableRow.getCell(0).setText(Integer.toString(1));
        tableRow.getCell(1).setText(columnModel.getName());
        tableRow.getCell(2).setText(columnModel.getDataType());
        tableRow.getCell(3).setText(getBooleanValue(columnModel.isPrimaryKey()));
        tableRow.getCell(4).setText(getBooleanValue(columnModel.isForeignKey()));
        tableRow.getCell(5).setText(getBooleanValue(columnModel.isNullable()));
        tableRow.getCell(6).setText(columnModel.getDescription());
    }

    private void addInfoRow(XWPFTableRow tableRow) {
        tableRow.getCell(0).setText("#");
        tableRow.addNewTableCell().setText("Nazwa");
        tableRow.addNewTableCell().setText("Typ danych");
        tableRow.addNewTableCell().setText("PK");
        tableRow.addNewTableCell().setText("FK");
        tableRow.addNewTableCell().setText("Null");
        tableRow.addNewTableCell().setText("Opis");
    }

    private String getBooleanValue(boolean value) {
        if(value) {
            return BOOLEAN_TRUE_VALUE;
        } else {
            return BOOLEAN_FALSE_VALUE;
        }
    }

    public void generateRaport(String directoryPath) {
        try{
            File file = new File(directoryPath);
            out = new FileOutputStream(file);
            document.write(out);
            Desktop.getDesktop().open(file);
            logger.info("File generated OK");
        } catch (java.io.IOException e) {
            logger.error("Wrong path", e);
        }
    }
}