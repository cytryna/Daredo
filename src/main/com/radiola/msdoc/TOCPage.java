package com.radiola.msdoc;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

/**
 * Created by aswiecicki on 01.12.17.
 *
 * generate Table of Content
 * to znaczy spsis tresci
 */
public class TOCPage {

    private XWPFDocument document;

    public TOCPage(XWPFDocument document) {
        this.document = document;
    }

    public void generateTOC() {
        document.createTOC();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setPageBreak(true);
    }
}