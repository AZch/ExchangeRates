package com.wcreators.kafkastarter.mappers;

public interface Mapper<T, U> {

    U modelToDto(T model);

    T dtoToModel(U dto);
}
