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

    private FileOutputStream out;
    private XWPFDocument document;
    private XWPFTable table;

    public Document(List<DataTableModel> modelList) {
        document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("hi");

        table = document.createTable(1, 7);

        modelList.forEach(this::asdf);
    }

    private void asdf(DataTableModel tableModel) {
        addInfoRow(table.getRow(0));
        tableModel.getColumnModel().forEach(this::zxcv);
    }

    private void zxcv(DataColumnModel columnModel) {
        XWPFTableRow tableRow = table.createRow();
        tableRow.getCell(0).setText(Integer.toString(1));
        tableRow.getCell(1).setText(columnModel.getName());
        tableRow.getCell(2).setText(columnModel.getDataType());
        tableRow.getCell(3).setText(Boolean.toString(columnModel.isPrimaryKey()));
        tableRow.getCell(4).setText(Boolean.toString(columnModel.isForeignKey()));
        tableRow.getCell(5).setText(Boolean.toString(columnModel.isNullable()));
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