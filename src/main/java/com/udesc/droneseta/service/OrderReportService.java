package com.udesc.droneseta.service;

import com.udesc.droneseta.model.dto.OrderPendingDTO;
import com.udesc.droneseta.model.enumerator.OrderStatus;
import com.udesc.droneseta.repository.OrderRepository;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class OrderReportService {
    public OrderReportService(OrderRepository repository) {
        this.repository = repository;
    }

    public OrderReportService() {
    }

    @Autowired
    private OrderRepository repository;
    public String generateReport() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Relatório");

        XSSFCellStyle headerCellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerCellStyle.setFont(font);


        XSSFRow headerRow = sheet.createRow(0);
        XSSFCell cell1 = headerRow.createCell(0);
        XSSFCell cell2 = headerRow.createCell(1);
        XSSFCell cell3 = headerRow.createCell(2);
        XSSFCell cell4 = headerRow.createCell(3);


        cell1.setCellValue("Nome da Pessoa");
        cell2.setCellValue("Número do Cartão de Crédito");
        cell3.setCellValue("Valor da Compra");
        cell4.setCellValue("Status");

        cell1.setCellStyle(headerCellStyle);
        cell2.setCellStyle(headerCellStyle);
        cell3.setCellStyle(headerCellStyle);
        cell4.setCellStyle(headerCellStyle);

        List<OrderPendingDTO> orders = repository.findAllOrderFilter(Arrays.asList(OrderStatus.ENTREGUE, OrderStatus.CONFIRMADO, OrderStatus.TRANSITO));

        int row = 0;
        for (OrderPendingDTO order : orders) {
            row++;
            XSSFRow dataRow = sheet.createRow(row);
            dataRow.createCell(0).setCellValue(order.getName());
            dataRow.createCell(1).setCellValue(order.getCreditCard());
            dataRow.createCell(2).setCellValue(order.getPrice());
            dataRow.createCell(3).setCellValue(order.getStatus().getValue());
        }

        FileOutputStream outputStream = new FileOutputStream("OrderReport.xlsx");
        workbook.write(outputStream);
        workbook.close();
        File file = new File("OrderReport.xlsx");
        return file.getAbsolutePath();
    }
}
