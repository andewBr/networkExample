package org.example.http2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Employee{
    public String id;
    public String name;
    public int salary;
    public int tax;
}