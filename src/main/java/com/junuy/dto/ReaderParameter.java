package com.junuy.dto;

import java.util.List;

import lombok.Data;

/**
 * ReaderParameter对象
 * 
 * @author junuy 2021/2/24
 */
@Data
public class ReaderParameter {

    private List<String> column;
    private List<ReaderConnection> connection;
    private String password;
    private String username;
}
