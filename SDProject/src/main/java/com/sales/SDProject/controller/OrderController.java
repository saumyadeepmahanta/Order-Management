package com.sales.SDProject.controller;


import com.sales.SDProject.model.itemsEntity;
import com.sales.SDProject.model.ordersEntity;
import com.sales.SDProject.service.ItemService;
import com.sales.SDProject.service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
   @Autowired
    private ItemService itemService;

   @Autowired
    private OrderService orderService;


//   @GetMapping("hello")
//   public String test(){
//       return "sam";
//   }

    @GetMapping("getOrders")
    public List<ordersEntity> getAllOrders(){
        return orderService.getAllOrders();
    }


    @GetMapping("getOrders/{id}")
    public ResponseEntity<?> getOrderbyID(@PathVariable String id) {
        ordersEntity order = orderService.getOrderbyID(id);
        if (order == null) {
            // Return 404 status with custom message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Order" + id + " not found.");
        }
        return ResponseEntity.ok(order);
    }



   @PostMapping("create")
    public ordersEntity createOrderwithItems(@RequestBody ordersEntity order){
       order.setDate(new Date());
       return orderService.saveOrder(order);
   }


   @PutMapping("status/{id}/{status}")
    public void setStatus(@PathVariable String id,@PathVariable String status){
        orderService.setStatus(id,status);
   }


   @DeleteMapping("deleteOrder/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id){
       if (!orderService.findByID(id)) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body("Order ID " + id + " does not exist.");
       }

       orderService.deleteById(id);
       return ResponseEntity.ok("Order deleted successfully.");
   }




    @GetMapping("/importOrderToExcel")
    public void importOrderToExcel(HttpServletResponse response) throws IOException {
        List<ordersEntity> orders = orderService.getAllOrders();

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String fileName = "orders_export.xlsx";
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Orders");

        // Header row
        Row headerRow = sheet.createRow(0);
        int col = 0;
        headerRow.createCell(col++).setCellValue("Order No");
        headerRow.createCell(col++).setCellValue("Customer No");
        headerRow.createCell(col++).setCellValue("Customer Name");
        headerRow.createCell(col++).setCellValue("Order Date");
        headerRow.createCell(col++).setCellValue("Created By");
        headerRow.createCell(col++).setCellValue("Status");

        headerRow.createCell(col++).setCellValue("Item No");
        headerRow.createCell(col++).setCellValue("Material");
        headerRow.createCell(col++).setCellValue("Amount");
        headerRow.createCell(col++).setCellValue("Quantity");

        int rowIdx = 1;

        for (ordersEntity order : orders) {
            // Fetch items for this order by orderNo
            List<itemsEntity> items = itemService.getItemsByOrderNo(order.getOrderNo());

            if (items.isEmpty()) {
                Row row = sheet.createRow(rowIdx++);
                col = 0;
                row.createCell(col++).setCellValue(order.getOrderNo());
                row.createCell(col++).setCellValue(order.getCustomerNo());
                row.createCell(col++).setCellValue(order.getCustomerName());
                row.createCell(col++).setCellValue(order.getDate() != null ? order.getDate().toString() : "");
                row.createCell(col++).setCellValue(order.getCreatedBy());
                row.createCell(col++).setCellValue(order.getStatus());
                // item columns empty
            } else {
                for (itemsEntity item : items) {
                    Row row = sheet.createRow(rowIdx++);
                    col = 0;

                    // Order details
                    row.createCell(col++).setCellValue(order.getOrderNo());
                    row.createCell(col++).setCellValue(order.getCustomerNo());
                    row.createCell(col++).setCellValue(order.getCustomerName());
                    row.createCell(col++).setCellValue(order.getDate() != null ? order.getDate().toString() : "");
                    row.createCell(col++).setCellValue(order.getCreatedBy());
                    row.createCell(col++).setCellValue(order.getStatus());

                    // Item details
                    row.createCell(col++).setCellValue(item.getItemNo());
                    row.createCell(col++).setCellValue(item.getMaterial());
                    row.createCell(col++).setCellValue(item.getAmount());
                    row.createCell(col++).setCellValue(item.getQuantity());
                }
            }
        }

        // Autosize columns
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }


}
