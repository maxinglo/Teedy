package com.sismics.docs.core.util;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.sismics.docs.core.model.jpa.File;
import com.sismics.util.mime.MimeType;

import org.junit.Assert;

/**
 * Test of the file entity utilities.
 * 
 * @author bgamard
 */
public class TestFileUtil {
    @Test
    public void extractContentOpenDocumentTextTest() throws Exception {
        try (InputStream inputStream = Resources.getResource("file/document.odt").openStream()) {
            File file = new File();
            file.setMimeType(MimeType.OPEN_DOCUMENT_TEXT);
            try (InputStream pdfInputStream = PdfUtil.convertToPdf(file, inputStream, false)) {
                String content = FileUtil.extractContent(null, file, inputStream, pdfInputStream);
                Assert.assertTrue(content.contains("Lorem ipsum dolor sit amen."));
            }
        }
    }
    
    @Test
    public void extractContentOfficeDocumentTest() throws Exception {
        try (InputStream inputStream = Resources.getResource("file/document.docx").openStream()) {
            File file = new File();
            file.setMimeType(MimeType.OFFICE_DOCUMENT);
            try (InputStream pdfInputStream = PdfUtil.convertToPdf(file, inputStream, false)) {
                String content = FileUtil.extractContent(null, file, inputStream, pdfInputStream);
                Assert.assertTrue(content.contains("Lorem ipsum dolor sit amen."));
            }
        }
    }
    
    @Test
    public void convertToPdfTest() throws Exception {
        try (InputStream inputStream0 = Resources.getResource("file/apollo_landscape.jpg").openStream();
                InputStream inputStream1 = Resources.getResource("file/apollo_portrait.jpg").openStream();
                InputStream inputStream2 = Resources.getResource("file/udhr_encrypted.pdf").openStream();
                InputStream inputStream3 = Resources.getResource("file/document.docx").openStream();
                InputStream inputStream4 = Resources.getResource("file/document.odt").openStream()) {
            // First file
            Files.copy(inputStream0, DirectoryUtil.getStorageDirectory().resolve("apollo_landscape"), StandardCopyOption.REPLACE_EXISTING);
            File file0 = new File();
            file0.setId("apollo_landscape");
            file0.setMimeType(MimeType.IMAGE_JPEG);
            
            // Second file
            Files.copy(inputStream1, DirectoryUtil.getStorageDirectory().resolve("apollo_portrait"), StandardCopyOption.REPLACE_EXISTING);
            File file1 = new File();
            file1.setId("apollo_portrait");
            file1.setMimeType(MimeType.IMAGE_JPEG);
            
            // Third file
            Files.copy(inputStream2, DirectoryUtil.getStorageDirectory().resolve("udhr"), StandardCopyOption.REPLACE_EXISTING);
            File file2 = new File();
            file2.setId("udhr");
            file2.setPrivateKey("OnceUponATime");
            file2.setMimeType(MimeType.APPLICATION_PDF);
            
            // Fourth file
            Files.copy(inputStream3, DirectoryUtil.getStorageDirectory().resolve("document_docx"), StandardCopyOption.REPLACE_EXISTING);
            File file3 = new File();
            file3.setId("document_docx");
            file3.setMimeType(MimeType.OFFICE_DOCUMENT);
            
            // Fifth file
            Files.copy(inputStream4, DirectoryUtil.getStorageDirectory().resolve("document_odt"), StandardCopyOption.REPLACE_EXISTING);
            File file4 = new File();
            file4.setId("document_odt");
            file4.setMimeType(MimeType.OPEN_DOCUMENT_TEXT);
            
            PdfUtil.convertToPdf(Lists.newArrayList(file0, file1, file2, file3, file4), true, 10).close();
        }
    }
}