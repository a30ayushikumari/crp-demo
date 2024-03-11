package com.demoProject.crp.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ToDoDto {
    private Integer id;
    private Integer userId;
    private String title;
    private boolean completed;
}
