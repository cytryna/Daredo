package com.radiola.msdoc;

import com.radiola.data.model.DataTableModel;
import com.radiola.doc.model.DocumentModel;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by aswiecicki on 01.12.17.
 */
public class Document {

    private static Logger logger = LoggerFactory.getLogger(Document.class);

    private static final long LEFT_MARGIN = 1170L;
    private static final long RIGHT_MARGIN = 1200L;

    private FileOutputStream out;
    private XWPFDocument document;

    private DocumentModel docModel;
    private List<DataTableModel> modelList;

    public Document(DocumentModel model, List<DataTableModel> modelList) {
        this.docModel = model;
        this.modelList = modelList;

        document = new XWPFDocument();

        setDocumentMargin();

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
        SchemaDBPage schemaPage = new SchemaDBPage(document, modelList);
        schemaPage.generateSchemaPages();
    }

    private void setDocumentMargin() {
        CTSectPr sectpr = document.getDocument().getBody().addNewSectPr();
        CTPageMar margin = sectpr.addNewPgMar();
        margin.setLeft(BigInteger.valueOf(LEFT_MARGIN));
        margin.setRight(BigInteger.valueOf(RIGHT_MARGIN));

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