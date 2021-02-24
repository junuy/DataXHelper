package com.junuy.dto;

import lombok.Data;

import java.util.List;

/**
 * Job对象
 * 
 * @author junuy 2021/2/24
 */
@Data
public class Job {

    private List<Content> content;
    private Setting setting;
}
