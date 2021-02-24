package com.junuy.dto;

import java.util.List;

import lombok.Data;

/**
 * ReaderConnection对象
 * 
 * @author junuy 2021/2/24
 */
@Data
public class ReaderConnection {

    private List<String> jdbcUrl;
    private List<String> table;
}
