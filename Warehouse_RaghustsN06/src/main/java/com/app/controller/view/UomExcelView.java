package com.app.controller.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.app.model.Uom;

public class UomExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Uom> uomList=(List<Uom>) model.get("uomList");
		response.setHeader("Content-Disposition", "attachment; filename=\"UOMData.xlsx\"");
		Sheet sheet=workbook.createSheet("Uom");
		setHead(sheet);
		setBody(sheet, uomList);
	}
	private void setHead(Sheet sheet){
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("UOM ID");
		row.createCell(1).setCellValue("UOM TYPE");
		row.createCell(2).setCellValue("UOM MODEL");
		row.createCell(3).setCellValue("CREATED");
		row.createCell(4).setCellValue("MODIFIED");
		row.createCell(5).setCellValue("NOTES");
	}
	private void setBody(Sheet sheet,List<Uom> uomList){
		int rowId=1;
		for(Uom uom:uomList){
			Row row=sheet.createRow(rowId++);
			row.createCell(0).setCellValue(uom.getUomId());
			row.createCell(1).setCellValue(uom.getUomType());
			row.createCell(2).setCellValue(uom.getUomModel());
			row.createCell(3).setCellValue(uom.getCreatedDate().toString());
			row.createCell(4).setCellValue(uom.getLastModifiedDate()!=null?
					uom.getLastModifiedDate().toString():"-NA-");
			row.createCell(5).setCellValue(uom.getDescription());
		}
	}

	
}
