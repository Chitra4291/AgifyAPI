package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {

    public static List<Object[]> readExcelData(String filePath) throws IOException {
        List<Object[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() == 0) continue; // Skip header row
            int count = (int) row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();
            int age = (int) row.getCell(2).getNumericCellValue();
             // Ensure this matches your Excel structure
            data.add(new Object[]{count, name, age});
        }

        workbook.close();
        fis.close();
        return data;
    }
}
