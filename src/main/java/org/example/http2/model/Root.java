package org.example.http2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class Root{
    public String info;
    public ArrayList<Employee> employees;
}