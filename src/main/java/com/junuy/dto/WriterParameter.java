package com.junuy.dto;

import java.util.List;

import lombok.Data;

/**
 * WriteParameter对象
 * 
 * @author junuy 2021/2/24
 */
@Data
public class WriterParameter {

    private List<String> column;
    private List<WriterConnection> connection;
    private String password;
    private String username;
}
