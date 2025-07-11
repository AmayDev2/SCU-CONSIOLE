package com.amay.scu.report.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class JasperHelper<T> {
	JasperReport jasperReport;
	JRBeanCollectionDataSource dataSource;
	Map<String, Object> parameters = new HashMap<>();
	JasperPrint jasperPrint;
	
	public JasperHelper(InputStream reportStream, List<T> collection) {
		try {
			jasperReport = JasperCompileManager.compileReport(reportStream);
			dataSource = new JRBeanCollectionDataSource(collection);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportPdfReport() {
		try {
			getJasperPrinter();
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/reports/report.pdf");
			System.out.println("Exported Successfully . . .");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exportExcelReport() {
		try {
			getJasperPrinter();
			// Create the XLSX Exporter
            JRXlsxExporter exporter = new JRXlsxExporter();

            // Set export configurations
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false); // Set true to split content on multiple sheets
            configuration.setDetectCellType(true); // Automatically format cell types
            configuration.setCollapseRowSpan(false); // Allow row span collapsing

            // Set the exporter input and output
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream("C:/reports/report.xlsx")));
            exporter.setConfiguration(configuration);

            // Export the report to XLSX
            exporter.exportReport();

			System.out.println("Exported Successfully . . .");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getJasperPrinter() {
		try {
			jasperPrint =  JasperFillManager.fillReport(jasperReport,parameters, dataSource);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
