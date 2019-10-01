package com.example.market.swingview.model;

import com.example.market.core.model.BaseModel;
import com.example.market.core.model.Named;

@Named("Сотрудник")
public class Employee extends BaseModel<Employee> {

    @Named("Имя")
    private String firstName;

    @Named("Фамилия")
    private String lastName;

    @Named("Отчество")
    private String middleName;

    @Named("Возраст")
    private int age;
}
