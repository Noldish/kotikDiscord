package kotik.simple.service;

import kotik.simple.service.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Roman_Kuznetcov on 20.10.2016.
 */
@Repository
public class CommandRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public CommandRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Map<String, CommandInterface> getCommandsList() {
		List<Map<String, Object>> rows = this.jdbcTemplate
				.queryForList("SELECT name,context FROM commands");
		Map<String, CommandInterface> result = new HashMap<>();
		for (Map m : rows) {
			try {
				result.put((String) m.get("name"), (CommandInterface) deserialize((byte[]) m.get("context")));
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

	public boolean addCommand(String key, CommandInterface context) {
		try {
			if(isCommandExists(key)){
				this.jdbcTemplate.update("update commands set context = ? where name = ?", serialize(context), key);
			} else {
				this.jdbcTemplate.update("insert into commands (name, context) values (?, ?)", key, serialize(context));
			}
		} catch (DataAccessException | IOException e) {
            e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteCommand(String key) {
		try {
			this.jdbcTemplate.update("delete from commands where name = ?", key);
		} catch (DataAccessException e) {
			return false;
		}
		return true;
	}

	private byte[] serialize(Object obj) throws IOException {
		try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
			try (ObjectOutputStream o = new ObjectOutputStream(b)) {
				o.writeObject(obj);
			}
			return b.toByteArray();
		}
	}

	private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
			try (ObjectInputStream o = new ObjectInputStream(b)) {
				return o.readObject();
			}
		}
	}
	
	private boolean isCommandExists(String key){
		try{
			this.jdbcTemplate.queryForObject("select context from commands where name=?",CommandInterface.class,key);
		} catch (DataAccessException e) {
			return false;
		}
		return true;	
	}
}
