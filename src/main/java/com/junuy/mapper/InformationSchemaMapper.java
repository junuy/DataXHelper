package com.junuy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统表
 *
 * @author junuy 2021/2/24
 */
@Repository
public interface InformationSchemaMapper {

    /**
     * 根据数据库名，获取表名列表
     *
     * @param dbName 数据库名
     * @return 表名列表
     */
    List<String> selectTableNames(@Param("dbName") String dbName);

    /**
     * 根据表名，获取字段列表
     *
     * @param dbName 数据库名
     * @param tableName 表名
     * @return 字段列表
     */
    List<String> selectColumns(@Param("dbName") String dbName, @Param("tableName") String tableName);
}
