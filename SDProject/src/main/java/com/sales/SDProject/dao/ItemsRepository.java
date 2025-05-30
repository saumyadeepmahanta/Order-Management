package com.sales.SDProject.dao;
import java.util.List;
import com.sales.SDProject.model.itemsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemsRepository extends MongoRepository<itemsEntity,String> {

    List<itemsEntity> findByOrderNo(String orderNo);
    void deleteByOrderNo(String orderNo);
}
    