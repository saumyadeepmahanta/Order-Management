package com.sales.SDProject.service;

import com.sales.SDProject.dao.ItemsRepository;
import com.sales.SDProject.dao.OrdersRepository;
import com.sales.SDProject.model.itemsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    public List<itemsEntity> saveItems(List<itemsEntity> items){
        for (itemsEntity item : items) {
            item.generateId();
        }
        return itemsRepository.saveAll(items);
    }

    public List<itemsEntity> getAllItems() {
        return itemsRepository.findAll();
    }

    public List<itemsEntity> getItemsByOrderNo(String orderNo) {
        return itemsRepository.findByOrderNo(orderNo);
    }


    public boolean findbyID(String id) {
        return itemsRepository.existsById(id);
    }

    public void deleteById(String id) {
        itemsRepository.deleteById(id);
    }
}
