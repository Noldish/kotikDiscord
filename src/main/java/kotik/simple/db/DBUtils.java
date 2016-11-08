package kotik.simple.db;

import kotik.simple.service.commands.AbstractCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Romique on 31.10.2016.
 */
@Service
public class DBUtils {

    @Value("${DriverClassName}")
    private String DRIVER;

    @Value("${URL}")
    private String URL;

    @Value("${name}")
    private String NAME;

    @Value("${Password}")
    private String PASSWORD;

    private Connection connection;

    @PostConstruct
    public void init(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            System.out.println("DB connected");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy(){
        try {
            connection.close();
            System.out.println("DB disconnected");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public ResultSet execQuery(String query){
        try {
            System.out.println("Executing query: "+query);
//            return getConnection().createStatement().executeQuery(query);
            Statement statement = getConnection().createStatement();
            if (statement.execute(query)){
                return statement.getResultSet();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public void execEmptyQuery(String query){
        try {
            getConnection().createStatement().executeQuery(query);
        } catch (SQLException e){
        }
    }

    public void insertBean(Object o){
        try {
            StringBuilder sb = new StringBuilder();
            Field table = o.getClass().getDeclaredField("TABLE");
            table.setAccessible(true);
            Object value = table.get(o);
            sb.append("insert into ").append(value.toString());
            sb.append(" values (");
            for (Field field : o.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object val = field.get(o);
                if ((!field.getName().equals("TABLE"))&&(val!=null)){
                    if (!field.getType().isPrimitive()){
                        sb.append("'").append(val.toString()).append("',");
                    } else {
                        sb.append(val.toString()).append(",");
                    }
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
            String query = sb.toString();
            execQuery(query);
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public void saveBean(Object o){
        try {
            StringBuilder sb = new StringBuilder();
            Field table = o.getClass().getDeclaredField("TABLE");
            table.setAccessible(true);
            Object value = table.get(o);
            sb.append("update ").append(value.toString());
            sb.append(" set ");
            for (Field field : o.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object val = field.get(o);
                if ((!field.getName().equals("TABLE"))&&(!field.getName().equals("id"))&&(val!=null)){
                    if (!field.getType().isPrimitive()){
                        sb.append(field.getName()).append(" = ").append("'").append(val.toString()).append("',");
                    } else {
                        sb.append(field.getName()).append(" = ").append(val.toString()).append(",");
                    }
                }
            }
            sb.deleteCharAt(sb.length()-1);
            Field idField = o.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            sb.append(" where id = '").append(idField.get(o).toString()).append("'");
            String query = sb.toString();
            execQuery(query);
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public void upsert(Object o){
        try {
            Field table = o.getClass().getDeclaredField("TABLE");
            table.setAccessible(true);
            Object tableValue = table.get(o);

            Field idField = o.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            StringBuilder sb = new StringBuilder();
            sb.append("select * from " + tableValue.toString()+ " where id='").append(idField.get(o).toString()).append("'");
            String query = sb.toString();
            ResultSet rs = execQuery(query);
            if (rs.next()) {
                saveBean(o);
            } else {
                insertBean(o);
            }
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteBean(Object o){
        try {
            Field table = o.getClass().getDeclaredField("TABLE");
            table.setAccessible(true);
            Object tableValue = table.get(o);

            Field id = o.getClass().getDeclaredField("id");
            id.setAccessible(true);
            Object idValue = id.get(o);
            String query = "delete from "+tableValue.toString()+" where id = '"+idValue.toString()+"'";
            execQuery(query);
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public <T> List<T> getAllBeans(Class<T> clazz){
        try {
            Field table = clazz.getDeclaredField("TABLE");
            table.setAccessible(true);
            Object tableValue = table.get(clazz);

            Field[] fields = clazz.getDeclaredFields();

            String query = "select * from " + tableValue.toString();
            ResultSet rs = execQuery(query);

            List<T> beanList = new ArrayList<T>();
            while (rs.next()){
                T bean = (T) clazz.newInstance();
                for (Field field : fields){
                    if (!field.getName().equals("TABLE")) {
                        field.setAccessible(true);
                        field.set(bean, rs.getObject(field.getName()));
                    }
                }
                beanList.add(bean);
            }
            return beanList;
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (InstantiationException e){
            e.printStackTrace();
        }

        return null;
    }

    public <T> T getBean(T o){
        try {
            Field table = o.getClass().getDeclaredField("TABLE");
            table.setAccessible(true);
            Object tableValue = table.get(o);

            Field[] fields = o.getClass().getDeclaredFields();
            StringBuilder sb = new StringBuilder();
            sb.append("select * from " + tableValue.toString()+ " where ");
            for (Field field : fields){
                field.setAccessible(true);
                Object val = field.get(o);
                if ((!field.getName().equals("TABLE"))&&(val!=null)) {
                    if (!val.toString().equals("")) {
                        addField(sb, o, field);
                    }
                }
            }

            sb.setLength(sb.length()-5);

            String query = sb.toString();
            ResultSet rs = execQuery(query);
            while (rs.next()){
                for (Field field : fields){
                    if (!field.getName().equals("TABLE")) {
                        field.set(o, rs.getObject(field.getName()));
                    }
                }
                return o;
            }
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public <T> T getBeanById(T o){
        try {
            Field table = o.getClass().getDeclaredField("TABLE");
            table.setAccessible(true);
            Object tableValue = table.get(o);

            Field idField = o.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            StringBuilder sb = new StringBuilder();
            sb.append("select * from " + tableValue.toString()+ " where id='").append(idField.get(o).toString()).append("'");
            String query = sb.toString();
            ResultSet rs = execQuery(query);
            while (rs.next()){
                for (Field field : o.getClass().getDeclaredFields()){
                    field.setAccessible(true);
                    if (!field.getName().equals("TABLE")) {
                        field.set(o, rs.getObject(field.getName()));
                    }
                }
                return o;
            }
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public StringBuilder addField(StringBuilder sb, Object o, Field field){
        try {
            StringBuilder newSb = new StringBuilder();
            Object val = field.get(o);
            if (val.toString().equals("")){
                return sb;
            }
            if (field.getType().isPrimitive()) {
                sb.append(field.getName() + " = " + val.toString() + " and ");
            } else {
                sb.append(field.getName() + " = '" + val.toString() + "' and ");
            }


        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public void insertData(DBData dbData) {
        List<String> columns = new ArrayList<>();
        List<String> vals = new ArrayList<>();
        for (Map.Entry<String, String> e : dbData.getValues().entrySet()) {
            columns.add(e.getKey());
            vals.add(e.getValue());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("insert into ").append(dbData.getTable()).append(" (");
        for (String col : columns) {
            sb.append(col).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") values (");
        for (String val : vals) {
            sb.append("'").append(val).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        String query = sb.toString();
        execQuery(query);
    }

    public List<DBData> getData(String table){
        try {
            List<DBData> resultList = new ArrayList<>();
            ResultSet rs = execQuery("select * from " + table);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                DBData dbData = new DBData();
                dbData.setTable(table);
                for (int i=1; i<=columns; i++){
                    if (rs.getObject(i)!=null) {
                        dbData.add(md.getColumnName(i), rs.getObject(i).toString());
                    }
                }
                resultList.add(dbData);
            }
            return resultList;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
/*
    public void updateData(DBData dbData) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(dbData.getTable()).append(" set ");
        for (Map.Entry<String, String> e : dbData.getValues().entrySet()) {
            sb.append(e.getKey()).append("='").append(e.getValue()).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        String query = sb.toString();
        execQuery(query);
    }

*/
}
