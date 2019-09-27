package ru.matprojects.sqlexcel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonConfigReader {
    private static String FILE_PATH = "./src/main/resources/jdbcconn.json";
    private final Logger log = LoggerFactory.getLogger(JsonConfigReader.class);

    public Map<String, Object> readJdbcPar(){
        JSONParser parser = new JSONParser();
        Map<String, Object> params = new HashMap<>();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(FILE_PATH));
            params.put("connection_string", jsonObject.get("connection_string").toString());
            params.put("username", jsonObject.get("username").toString());
            params.put("password", jsonObject.get("password").toString());
            Object object;
            JSONArray sqls = (JSONArray) jsonObject.get("exportSqls");
            List<String> sqlList = new ArrayList<>();
            sqlList.addAll(sqls);

            params.put("sqlList", sqlList);
        } catch (FileNotFoundException e) {
            log.error("Cannot find file {}", FILE_PATH);
        } catch (IOException e) {
            log.error("IOException while reading jdbc confin");
        } catch (ParseException e) {
            log.error("Json parse exception");
        }

        return params;
    }
}
