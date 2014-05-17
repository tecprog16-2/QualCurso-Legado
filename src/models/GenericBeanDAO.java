package models;


import android.database.SQLException;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import libraries.DataBase;

public class GenericBeanDAO extends DataBase{

	private SQLiteStatement pst;
	
	public GenericBeanDAO() throws SQLException {
		super();
		
	}
	
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table)
			throws SQLException {
		this.openConnection();
		ArrayList<Bean> beans = new ArrayList<Bean>();
		String sql = "SELECT c.* FROM " + table + " as c, " + bean.relationship
				+ " as ci " + "WHERE ci.id_" + bean.identifier + "= ? "
				+ "AND ci.id_" + table + " = c._id";
		Cursor cs = this.database.rawQuery(sql, new String[]{bean.get(bean.fieldsList().get(0))});
		while (cs.moveToNext()) {
			Bean object = init(table);
			for (String s : object.fieldsList()) {
				object.set(s, cs.getString(cs.getColumnIndex(s)));
			}
			beans.add(object);
		}
		this.closeConnection();
		return beans;
	}
	
	public ArrayList<Bean> selectFromFields(Bean bean, ArrayList<String> fields)
			throws SQLException {
		this.openConnection();
		ArrayList<Bean> beans = new ArrayList<Bean>();
		ArrayList<String> values = new ArrayList<String>();
		//String sql = "SELECT * FROM " + bean.identifier + " WHERE ";
		String sql ="";
		for(String s : fields){
			sql+=" "+s+" = ? AND";
			values.add(bean.get(s));
		}
		sql = sql.substring(0, sql.length() - 3);
		String[] strings = new String[values.size()];
		strings = values.toArray(strings);
		Cursor cs;//= this.database.rawQuery(sql,strings);
		
		cs =this.database.query(bean.identifier, null, 
				sql,
				strings,
				null, null, null);
		while (cs.moveToNext()) {
			Bean object = init(bean.identifier);
			for (String s : object.fieldsList()) {
				object.set(s, cs.getString(cs.getColumnIndex(s)));
			}
			beans.add(object);
		}
		this.closeConnection();
		return beans;
	}

	public boolean insertBean(Bean bean) throws SQLException {
		this.openConnection();
		String replace = "";
		int i = 1;
		ArrayList<String> notPrimaryFields = bean.fieldsList();
		notPrimaryFields.remove(0);
		String sql = "INSERT INTO " + bean.identifier + "(";
		for (String s : notPrimaryFields) {
			sql += s + ",";
			replace += "?,";
		}
		sql = sql.substring(0, sql.length() - 1);
		replace = replace.substring(0, replace.length() - 1);
		sql += ") VALUES(" + replace + ")";
		this.pst = this.database.compileStatement(sql);
		for (String s : notPrimaryFields) {
			this.pst.bindString(i, bean.get(s));
			i++;
		}
		long result = this.pst.executeInsert();
		this.pst.clearBindings();
		this.closeConnection();
		return (result != -1) ? true : false;
	}

	public boolean addBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();
		String sql = "INSERT INTO " + parentBean.relationship + "(id_"
				+ parentBean.identifier + ",id_" + childBean.identifier
				+ ") VALUES(?,?)";
		this.pst = this.database.compileStatement(sql);
		this.pst.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.pst.bindString(2, childBean.get(childBean.fieldsList().get(0)));
		long result = this.pst.executeInsert();
		this.pst.clearBindings();
		this.closeConnection();
		return (result != -1) ? true : false;
	}
	
	public boolean deleteBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();
		String sql = "DELETE FROM " + parentBean.relationship + "  WHERE id_"
				+ parentBean.identifier + " = ? AND id_" + childBean.identifier
				+ " = ?";
		this.pst = this.database.compileStatement(sql);
		this.pst.bindString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.pst.bindString(2, childBean.get(childBean.fieldsList().get(0)));
		int result = this.pst.executeUpdateDelete();
		this.pst.clearBindings();
		this.closeConnection();
		return (result == 1) ? true : false;
	}

	public Bean selectBean(Bean bean) throws SQLException {
		this.openConnection();
		Bean result = null;
		String sql = "SELECT * FROM " + bean.identifier + " WHERE "
				+ bean.fieldsList().get(0) + " = ?";
		Cursor cs = this.database.rawQuery(sql, new String[]{bean.get(bean.fieldsList().get(0))});
		if (cs.moveToFirst()) {
			result = init(bean.identifier);
			for (String s : bean.fieldsList()) {
				result.set(s, cs.getString(cs.getColumnIndex(s)));
			}
		}
		this.closeConnection();
		return result;
	}

	public ArrayList<Bean> selectAllBeans(Bean type) throws SQLException {
		this.openConnection();
		ArrayList<Bean> beans = new ArrayList<Bean>();
		Cursor cs = this.database.query(type.identifier, null, null, null, null, null, null);
		while (cs.moveToNext()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}
			beans.add(bean);
		}
		this.closeConnection();
		return beans;
	}

	public Integer countBean(Bean type) throws SQLException {
		this.openConnection();
		Integer count = 0;
		String sql = "SELECT * FROM " + type.identifier;
		Cursor cs = this.database.rawQuery(sql, null);
		if (cs.moveToFirst())
			count = cs.getCount();
		this.closeConnection();
		return count;
	}

	public Bean firstOrLastBean(Bean type, boolean last) throws SQLException {
		Bean bean = null;
		String sql = "SELECT * FROM " + type.identifier + " ORDER BY "
				+ type.fieldsList().get(0);

		if (!last)
			sql += " LIMIT 1";
		else
			sql += " DESC LIMIT 1";

		this.openConnection();
		Cursor cs = this.database.rawQuery(sql,null);

		if (cs.moveToFirst()) {
			bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}
		}
		this.closeConnection();

		return bean;
	}

	public ArrayList<Bean> selectBeanWhere(Bean type, String field,
			String value, boolean use_like) throws SQLException {
		this.openConnection();
		ArrayList<Bean> beans = new ArrayList<Bean>();
		String sql = "SELECT * FROM " + type.identifier + " WHERE ";
		Cursor cs;
		if (!use_like)
			cs =this.database.query(type.identifier, null, 
					field+" = ?",
					new String[]{value},
					null, null, null);
		else
			cs =this.database.query(type.identifier, null, 
					field+" LIKE ?",
					new String[]{"%"+value+"%"},
					null, null, null);

		while (cs.moveToNext()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, cs.getString(cs.getColumnIndex(s)));
			}
			beans.add(bean);
		}
		this.closeConnection();

		return beans;
	}
	
	public boolean deleteBean(Bean bean) throws SQLException {
		this.openConnection();
		String sql = "DELETE FROM "+bean.identifier+ " WHERE "+bean.fieldsList().get(0)+" = ?";
		this.pst = this.database.compileStatement(sql);
		this.pst.bindString(1, bean.get(bean.fieldsList().get(0)));
		int result = this.pst.executeUpdateDelete();
		this.pst.clearBindings();
		this.closeConnection();
		return (result == 1) ? true : false;
	}

	public Bean init(String beanIdentifier) {
		Bean object = null;
		if (beanIdentifier.equals("institution")) {
			object = new Institution();
		} 
		
		else if (beanIdentifier.equals("course")) {
			object = new Course();
		}
		
		else if (beanIdentifier.equals("books")) {
			object = new Book();
		}
		
		else if (beanIdentifier.equals("articles")) {
			object = new Article();
		}
		
		else if (beanIdentifier.equals("evaluation")) {
			object = new Evaluation();
		}
		
		return object;
	}

}