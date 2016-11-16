package kotik.simple.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romique on 08.11.2016.
 */
public class DBData {
    private String table;
    private Map<String, String> values = new HashMap<>();
    public void add(String key, String value){
        if (!values.containsKey(key)) {
            values.put(key, value);
        }
    }

    public String get(String key){
        if (values.containsKey(key)){
            return values.get(key);
        }
        return null;
    }

    public String getTable() {
        return table;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public DBData(String table, Map<String, String> values) {
        this.table = table;
        this.values = values;
    }
}
