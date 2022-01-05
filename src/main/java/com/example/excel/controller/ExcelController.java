package com.example.excel.controller;

import com.example.excel.EvtResponse;
import com.example.excel.ExcelApplication;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ExcelController {




    @GetMapping("/excel")
    public void downExcel(HttpServletResponse response) throws IOException {


        Logger logger = LoggerFactory.getLogger(ExcelController.class);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("이벤트 발생 조회");
        Map<String, List<Integer>> map = new HashMap<>();
        Cell cell = null;


        int rowNo =0;

        Row headerRow = sheet.createRow(rowNo++);

        headerRow.createCell(0).setCellValue("날짜");
        headerRow.createCell(1).setCellValue("이벤트(화재)");
        headerRow.createCell(2).setCellValue("이벤트(구조)");
        headerRow.createCell(3).setCellValue("이벤트(구급)");
        headerRow.createCell(4).setCellValue("이벤트(기타)");

        List<Integer> count = new ArrayList<>();
        count.add(1);
        count.add(3);
        count.add(0);
        count.add(5);

        List<Integer> count2 = new ArrayList<>();
        count2.add(3);
        count2.add(4);
        count2.add(5);
        count2.add(6);


        map.put("202001", count);
        map.put("202004",count2);

        logger.info("map : {}", map);



        for(String date : map.keySet()){
            Row row = sheet.createRow(rowNo++);

            row.createCell(0).setCellValue(date);
            for(int i = 1 ; i < map.get(date).size()+1 ; i++){
                logger.info("rowNo : {}",map.get(date));
                row.createCell(i).setCellValue(map.get(date).get(i-1));
            }
            for (int i = 0; i<map.get(date).size()+1; i++){
                sheet.setColumnWidth(i, (sheet.getColumnWidth(i))+512);
            }
        }



        String fileName = "eventStat.xlsx";
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");




        try{

            String downFile = "C:\\";
            File file = new File(downFile);

            response.setContentType("application/x-msdownload; text/html; charset=UTF-8;");
            response.setContentType("application/octet-stream; text/html; charset=UTF-8;");
            response.setContentType("application/download;charset=utf-8");
            response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");





            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);

            FileOutputStream fileOutputStream = new FileOutputStream(file+fileName);
            workbook.write(fileOutputStream);
            logger.info("성공했습니다.");
            logger.info("succeed? : {} ",file.isDirectory());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
        }

        finally {
            workbook.close();
        }


    }

}
