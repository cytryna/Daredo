package com.radiola.msdoc;

import com.radiola.data.model.DataColumnModel;
import com.radiola.data.model.DataTableModel;
import com.radiola.doc.model.DocumentModel;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * Created by aswiecicki on 01.12.17.
 */
public class Document {

    private static Logger logger = LoggerFactory.getLogger(Document.class);

    private static final String BOOLEAN_TRUE_VALUE = "X";
    private static final String BOOLEAN_FALSE_VALUE = EMPTY;

    private final String TITLE = "%Database documentation%";
    private final Date CREATION_DATE = new Date(0);
    private final String COMPANY_NAME = "%Company_name%";

    private FileOutputStream out;
    private XWPFDocument document;

    private DocumentModel docModel;
    private List<DataTableModel> modelList;

    public Document(DocumentModel model, List<DataTableModel> modelList) {
        this.docModel = model;
        this.modelList = modelList;

        document = new XWPFDocument();

        createTitlePage();
        addTOCToDocument();

        processDatabaseModel();
    }

    private void createTitlePage() {
        TitlePage titlePage = new TitlePage(document, docModel.getDocTitle(), docModel.getCreationYear(), docModel.getAuthor(), docModel.getVersion());
        titlePage.generateTitlePage();
    }

    private void addTOCToDocument() {
        TOCPage tocPage = new TOCPage(document);
        tocPage.generateTOC();
    }

    private void processDatabaseModel() {
        modelList.forEach(this::processTableModel);
    }

    private void processTableModel(DataTableModel tableModel) {
        XWPFParagraph nameParagraph = document.createParagraph();
        nameParagraph.setStyle("Heading 1");
        XWPFRun run1 = nameParagraph.createRun();
        run1.setText(tableModel.getName());

        XWPFParagraph descParagraph = document.createParagraph();
        XWPFRun run2 = descParagraph.createRun();
        run2.setText(tableModel.getDescription());


        XWPFTable table = document.createTable();
        addInfoRow(table.getRow(0));
        for(DataColumnModel model : tableModel.getColumnModel()) {
            processColumnModel(table, model);
        }
    }

    private void processColumnModel(XWPFTable table, DataColumnModel columnModel) {
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
        tableRow.getTableCells().stream().forEach(a -> a.setColor("c1fc00"));
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
            out.flush();
            out.close();
            Desktop.getDesktop().open(file);
            logger.info("File generated OK");
        } catch (java.io.IOException e) {
            logger.error("Wrong path", e);
        }
    }
}