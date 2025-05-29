package com.sales.SDProject.controller;


import com.sales.SDProject.model.itemsEntity;
import com.sales.SDProject.model.ordersEntity;
import com.sales.SDProject.service.ItemService;
import com.sales.SDProject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
