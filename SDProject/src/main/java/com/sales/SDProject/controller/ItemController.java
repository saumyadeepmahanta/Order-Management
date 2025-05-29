package com.sales.SDProject.controller;

import com.sales.SDProject.dao.OrdersRepository;
import com.sales.SDProject.model.itemsEntity;
import com.sales.SDProject.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemsService;

    @Autowired
    private OrdersRepository ordersRepository;

//    @PostMapping("/create")
//    public itemsEntity createItem(@RequestBody itemsEntity item) {
//        return itemsService.saveItem(item);
//    }


    @GetMapping("getItems")
    public List<itemsEntity> getAllItems(){
        return itemsService.getAllItems();
    }

    @GetMapping("getItems/{id}")
    public ResponseEntity<?> getItemsbyID(@PathVariable String id) {
        List<itemsEntity> items = itemsService.getItemsByOrderNo(id);

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Items don't exist for Order ID: " + id);
        } else {
            return ResponseEntity.ok(items);
        }
    }


    @PostMapping("/createAll")
    public ResponseEntity<?>  createItems(@RequestBody List<itemsEntity> items) {
        for (itemsEntity item : items) {
            if (!ordersRepository.existsById(item.getOrderNo())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Order ID: " + item.getOrderNo() + " doesn't exist.");
            }
        }
        List<itemsEntity> savedItems = itemsService.saveItems(items);
        return ResponseEntity.ok(savedItems);
    }


    @DeleteMapping("deleteItems/{orderNo}/{itemNo}")
    public ResponseEntity<?>  deleteItems(@PathVariable String orderNo,@PathVariable String itemNo){
        String id=itemNo+"_"+orderNo;
        if (!itemsService.findbyID(id)) {  // check if item does NOT exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item doesn't exist.");
        }
        itemsService.deleteById(id);
        return ResponseEntity.ok("Item Deleted");
    }
}
