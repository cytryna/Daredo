package com.radiola;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by aswiecicki on 01.12.17.
 */
public class POItest {

    @Test
    public void testDoc() throws IOException {
        XWPFDocument document = new XWPFDocument(OPCPackage.create("asdasd.doc"));

        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(new File("table.docx"));

        //Write first Text in the beginning
        XWPFParagraph para = document.createParagraph();
        XWPFRun run = para.createRun();
        run.setText("Hi");

        //create table
        XWPFTable table = document.createTable();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("col one, row one");
        tableRowOne.addNewTableCell().setText("col two, row one");
        tableRowOne.addNewTableCell().setText("col three, row one");
        //create second row
        XWPFTableRow tableRowTwo = table.createRow();
        tableRowTwo.getCell(0).setText("col one, row two");
        tableRowTwo.getCell(1).setText("col two, row two");
        tableRowTwo.getCell(2).setText("col three, row two");
        //create third row
        XWPFTableRow tableRowThree = table.createRow();
        tableRowThree.getCell(0).setText("col one, row three");
        tableRowThree.getCell(1).setText("col two, row three");
        tableRowThree.getCell(2).setText("col three, row three");

        //Write second Text after the table (by creating a new paragraph)
        XWPFParagraph para2 = document.createParagraph();
        XWPFRun run2 = para2.createRun();
        run2.setText("Bye");

        document.write(out);
        out.close();
        System.out.println("create_table.docx written successully");
    }
}
