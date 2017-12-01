package com.radiola.msdoc;

import org.apache.poi.xwpf.usermodel.*;

/**
 * Created by aswiecicki on 01.12.17.
 */
public class TitlePage {

    private static String VERSION = "Version";
    private static String COPYRIGHT = "Copyright (C)" ;

    private XWPFDocument document;
    private String title;
    private int creationYear;
    private String author;
    private String version;

    public TitlePage(XWPFDocument document, String title, int creationYear, String author, String version) {
        this.document = document;
        this.title = title;
        this.creationYear = creationYear;
        this.author = author;
        this.version = version;
    }

    public void generateTitlePage() {
        XWPFParagraph para1 =  document.createParagraph();
        para1.setAlignment(ParagraphAlignment.CENTER);
        para1.setVerticalAlignment(TextAlignment.TOP);
        para1.setSpacingBefore(10);

        XWPFRun run1 = para1.createRun();
        run1.setBold(true);
        run1.setFontSize(22);
        run1.setText(title);

        XWPFParagraph para2 =  document.createParagraph();
        para2.setAlignment(ParagraphAlignment.CENTER);
        //para2.setVerticalAlignment(TextAlignment.BOTTOM);

        XWPFRun run2 = para2.createRun();
        run2.setBold(true);
        run2.setFontSize(10);
        run2.setText(String.format("%s: %s", VERSION, version));

        XWPFParagraph para3 =  document.createParagraph();
        para3.setAlignment(ParagraphAlignment.CENTER);
        //para3.setVerticalAlignment(TextAlignment.BOTTOM);

        XWPFRun run3 = para3.createRun();
        run3.setBold(true);
        run3.setFontSize(10);
        run3.setText(String.format("%s %d %s", COPYRIGHT, creationYear, author));

        XWPFParagraph para4 =  document.createParagraph();
        para4.setPageBreak(true);
    }
}
