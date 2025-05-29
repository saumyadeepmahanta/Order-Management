package com.sales.SDProject.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "items")
public class itemsEntity {
    @Id
    private String id;

    private String itemNo;
    private String material;
    private double amount;
    private int quantity;
    private String orderNo;

    public void generateId() {
        this.id = itemNo + "_" + orderNo;
    }

}
