package com.bimodo.documentmetadata;

import android.content.Intent;
import android.util.Log;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.io.IOException;

public class ReadPdfMetadata {


    String pdfPath;


    ReadPdfMetadata(Intent data) {
        File file = new File(data.getData().getPath());
        String p = file.getAbsolutePath();
        String path = p.substring(p.indexOf(":") + 1);
        pdfPath = path.trim();
        Log.i("LOG", pdfPath);
        try {
            getPdfMetadata();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String[] getPdfMetadata() throws IOException {
        File pdfFile = new File(pdfPath);
        PDDocument doc = PDDocument.load(pdfFile.getAbsoluteFile());
        PDDocumentInformation md = doc.getDocumentInformation();

        String[] metadata = {md.getProducer() +" ", md.getTitle()+" ", md.getKeywords()+" ", md.getAuthor()+" ",
                md.getSubject()+" ", md.getCreationDate().getTime()+" ",
                md.getModificationDate().getTime() + " "};

        /*
        #1 - Producer
        #2 - Title
        #3 - Keywords
        #4 - Author
        #5 - Subject
        #6 - Creation Date
        #7 - Modification Date
         */
        return metadata;

    }


}
