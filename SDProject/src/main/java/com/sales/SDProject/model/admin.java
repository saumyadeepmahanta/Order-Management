package com.sales.SDProject.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="admin")
public class admin {
    @Id
    private String username;
    private String password;
}
