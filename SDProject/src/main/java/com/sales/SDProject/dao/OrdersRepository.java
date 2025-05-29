package com.sales.SDProject.dao;

import com.sales.SDProject.model.ordersEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository<ordersEntity,String> {

}
