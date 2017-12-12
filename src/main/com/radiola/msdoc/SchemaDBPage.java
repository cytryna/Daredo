package com.radiola.msdoc;

import com.radiola.data.model.DataColumnModel;
import com.radiola.data.model.DataTableModel;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;

import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * Created by aswiecicki on 01.12.17.
 */
public class SchemaDBPage {

    private static final String BOOLEAN_TRUE_VALUE = "X";
    private static final String BOOLEAN_FALSE_VALUE = EMPTY;

    private final int[] columnSizes = {
            1000,
            1448,
            2448,
            1872,
            4032,
            1728,
            1440
    };

    private XWPFDocument document;
    private List<DataTableModel> modelList;

    public SchemaDBPage(XWPFDocument document, List<DataTableModel> modelList) {
        this.document = document;
        this.modelList = modelList;
    }

    public void generateSchemaPages() {
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
        CTTblLayoutType type =  table.getCTTbl().getTblPr().addNewTblLayout();
        type.setType(STTblLayoutType.FIXED);

        CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
        width.setType(STTblWidth.DXA);
        width.setW(BigInteger.valueOf(9072));


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

        //setTableCellsSize(tableRow);
    }

    //TODO cell size
    private void setTableCellsSize(XWPFTableRow tableRow) {
        int columnCount = tableRow.getTableCells().size();
        for(int j=0;j<columnCount;j++) {
            XWPFTableCell cell = tableRow.getCell(j);
            CTTblWidth cellWidth = cell.getCTTc().addNewTcPr().addNewTcW();
            CTTcPr pr = cell.getCTTc().addNewTcPr();
//            pr.addNewNoWrap();
            cellWidth.setW(BigInteger.valueOf(columnSizes[j]));
        }
    }

    private String getBooleanValue(boolean value) {
        if(value) {
            return BOOLEAN_TRUE_VALUE;
        } else {
            return BOOLEAN_FALSE_VALUE;
        }
    }
}