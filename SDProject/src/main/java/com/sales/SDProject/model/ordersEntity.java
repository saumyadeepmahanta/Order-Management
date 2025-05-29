package com.sales.SDProject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@Document(collection = "orders")
public class ordersEntity {
    @Id
    private String orderNo;
    private String customerNo;
    private String customerName;
    private Date date;
    private String createdBy;
    private String status;
}
