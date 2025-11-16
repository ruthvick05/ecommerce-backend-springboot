package com.scaler.productservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@MappedSuperclass
// @MappedSuperClass - No table for base model , but all its attributes will be present in child class tables.
public class BaseModel {
    @Id //This marks the field as the primary key of the table.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Long id;
//    @GeneratedValue is a JPA annotation.
//    It tells Hibernate how to generate the primary key (id) automatically.
//    It works only with fields marked as @Id.
//    strategy = → Defines which generation strategy to use.
//    GenerationType.IDENTITY → Uses MySQL's AUTO_INCREMENT feature.
//    The database itself will increment the id automatically.

    private Date createdAt;
    private Date lastModifiedAT;
}
