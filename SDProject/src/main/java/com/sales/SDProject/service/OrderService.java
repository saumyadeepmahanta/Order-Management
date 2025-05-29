package com.sales.SDProject.service;


import com.sales.SDProject.dao.ItemsRepository;
import com.sales.SDProject.dao.OrdersRepository;
import com.sales.SDProject.model.ordersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    public ordersEntity saveOrder(ordersEntity order){
        return ordersRepository.save(order);
    }

    public List<ordersEntity> getAllOrders() {
        return ordersRepository.findAll();
    }

    public ordersEntity getOrderbyID(String id) {
        return ordersRepository.findById(id).orElse(null);
    }

    public void setStatus(String id, String status) {
        Optional<ordersEntity> orderOptional = ordersRepository.findById(id);

        if (orderOptional.isPresent()) {
            ordersEntity order = orderOptional.get();
            order.setStatus(status);
            ordersRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + id);
        }
    }

    public boolean findByID(String id) {
        return ordersRepository.existsById(id);
    }

    public void deleteById(String id) {
        // First delete all items that belong to the order
        itemsRepository.deleteByOrderNo(id);

        // Then delete the order itself
        ordersRepository.deleteById(id);
    }
}
