package com.junuy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.junuy.dto.*;
import com.junuy.mapper.InformationSchemaMapper;

/**
 * DataX工具类
 * 
 * @author junuy 2021/2/24
 */
@Component
public class DataXHelper {

    @Value("${dataX.dbName}")
    public String dbName;
    @Value("${dataX.source.url}")
    public String sourceUrl;
    @Value("${dataX.source.username}")
    public String sourceUsername;
    @Value("${dataX.source.pwd}")
    public String sourcePwd;
    @Value("${dataX.target.url}")
    public String targetUrl;
    @Value("${dataX.target.username}")
    public String targetUsername;
    @Value("${dataX.target.pwd}")
    public String targetPwd;
    @Value("${dataX.filePath}")
    public String filePath;
    @Value("${dataX.pyPath}")
    public String pyPath;

    @Autowired
    private InformationSchemaMapper informationSchemaMapper;

    private List<String> fileNames = new ArrayList<>();

    /**
     * 构造dataX的json对象
     * 
     * @param table 表名
     * @param columns 字段名
     * @return dataX的json对象
     */
    private DataXJson buildJson(String table, List<String> columns) {
        DataXJson dataXJson = new DataXJson();
        Job job = new Job();
        Content content = new Content();

        Reader reader = new Reader();
        reader.setName("mysqlreader");
        ReaderParameter readerParameter = new ReaderParameter();
        readerParameter.setColumn(columns);
        ReaderConnection readerConnection = new ReaderConnection();
        List<String> readerJdbcUrls = new ArrayList<>();
        readerJdbcUrls.add(sourceUrl);
        readerConnection.setJdbcUrl(readerJdbcUrls);
        List<String> readerTables = new ArrayList<>();
        readerTables.add(table);
        readerConnection.setTable(readerTables);
        List<ReaderConnection> readerConnections = new ArrayList<>();
        readerConnections.add(readerConnection);
        readerParameter.setConnection(readerConnections);
        readerParameter.setPassword(sourcePwd);
        readerParameter.setUsername(sourceUsername);
        reader.setParameter(readerParameter);
        content.setReader(reader);

        Writer writer = new Writer();
        writer.setName("mysqlwriter");
        WriterParameter writerParameter = new WriterParameter();
        writerParameter.setColumn(columns);
        WriterConnection writerConnection = new WriterConnection();
        writerConnection.setJdbcUrl(targetUrl);
        List<String> writerTables = new ArrayList<>();
        writerTables.add(table);
        writerConnection.setTable(writerTables);
        List<WriterConnection> writerConnections = new ArrayList<>();
        writerConnections.add(writerConnection);
        writerParameter.setConnection(writerConnections);
        writerParameter.setPassword(targetPwd);
        writerParameter.setUsername(targetUsername);
        writer.setParameter(writerParameter);
        content.setWriter(writer);

        List<Content> contents = new ArrayList<>();
        contents.add(content);
        job.setContent(contents);

        Setting setting = new Setting();
        Speed speed = new Speed();
        speed.setChannel("1");
        setting.setSpeed(speed);
        job.setSetting(setting);
        dataXJson.setJob(job);

        return dataXJson;
    }

    /**
     * 写入文件
     *
     * @param content 内容
     * @param tableName 表名
     */
    private void writeFile(String content, String tableName) {
        FileWriter writer;
        try {
            String fileName = dbName + "_" + tableName + ".json";
            fileNames.add(fileName);
            writer = new FileWriter(filePath + fileName);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入批量脚本
     */
    private void writeBatchScript() {
        StringBuffer sb = new StringBuffer();
        for (String fileName : fileNames) {
            sb.append("python datax.py ").append(pyPath).append(fileName).append("\n");
        }
        FileWriter writer;
        try {
            writer = new FileWriter(filePath + "batch.sh");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void run() {
        List<String> tableNames = informationSchemaMapper.selectTableNames(dbName);
        for (String tableName : tableNames) {
            List<String> columnNames = informationSchemaMapper.selectColumns(dbName, tableName);
            DataXJson dataXJson = buildJson(tableName, columnNames);
            writeFile(JSON.toJSONString(dataXJson), tableName);
            writeBatchScript();
        }
    }
}
