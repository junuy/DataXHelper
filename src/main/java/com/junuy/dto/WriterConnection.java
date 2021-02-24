package com.junuy.dto;

import java.util.List;

import lombok.Data;

/**
 * WriterConnection对象
 * 
 * @author junuy 2021/2/24
 */
@Data
public class WriterConnection {

    private String jdbcUrl;
    private List<String> table;
}
