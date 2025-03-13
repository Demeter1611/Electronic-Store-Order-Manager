package com.example.proiectlab4.domain;

public interface IEntityFactory<T extends Entity>{
    T createEntity(String line);
}
