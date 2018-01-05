package com.app.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.model.Uom;

@Component
public class UomUtil {

	public Map<String,String> getUomTypes(){

		Map<String,String> uomTypes=new LinkedHashMap<String,String>();
		uomTypes.put("PACK", "PACKING");
		uomTypes.put("NOPACK", "NO PACKING");
		uomTypes.put("NA", "-NA-");

		return uomTypes;
	}

	public List<Uom> replaceWitValues(List<Uom> uomList){
		Iterator<Uom> uomItr=uomList.iterator();
		while (uomItr.hasNext()) {
			Uom uom = (Uom) uomItr.next();
			uom.setUomType(getUomTypes().get(uom.getUomType()));
		}
		return uomList;
	}
	public List<Uom> getUomsFromSheet(MultipartFile file){
		List<Uom> uomList=null;
		if(file!=null){

			try {
				XSSFWorkbook book=new XSSFWorkbook(file.getInputStream());
				XSSFSheet sheet=book.getSheet("Uoms");
				if(sheet!=null){
					Iterator<Row> rows=sheet.iterator();
					uomList=new ArrayList<Uom>();
					while (rows.hasNext()) {
						Row row = (Row) rows.next();
						if(row.getRowNum()==0) continue;
						uomList.add(
								new Uom(row.getCell(0)!=null?row.getCell(0).getStringCellValue():"",
										row.getCell(1)!=null?row.getCell(1).getStringCellValue():"",
										row.getCell(2)!=null?row.getCell(2).getStringCellValue():"",
										new Date()
										)
								);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return uomList;
	}
}
