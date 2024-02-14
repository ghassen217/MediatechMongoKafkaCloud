package com.training.mediatech.mediatechMongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Customer")
public class CustomerModel {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String phone;
    private Date createdDate;
    private Date updatedDate;

}
