package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import unb.mdsgpp.qualcurso.QualCurso;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import libraries.DataBase;

public class GenericBeanDAO{

	private SQLiteDatabase database;
	
	public GenericBeanDAO() throws SQLException, ClassNotFoundException {
		super();
		database = new DataBase(QualCurso.getContext()).getReadableDatabase();
		SQLiteStatement test = database.compileStatement("test ?");
		test.bindString(1, "test");
		test.e
	}
	
	public ArrayList<Bean> selectBeanRelationship(Bean bean, String table)
			throws SQLException {
		this.openConnection();
		ArrayList<Bean> beans = new ArrayList<Bean>();
		String sql = "SELECT c.* FROM " + table + " as c, " + bean.relationship
				+ " as ci " + "WHERE ci.id_" + bean.identifier + "= ? "
				+ "AND ci.id_" + table + " = c.id";
		this.pst = this.conn.prepareStatement(sql);
		this.pst.setString(1, bean.get(bean.fieldsList().get(0)));
		ResultSet rs = this.pst.executeQuery();
		while (rs.next()) {
			Bean object = init(table);
			for (String s : object.fieldsList()) {
				object.set(s, rs.getString(s));
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
		this.pst = this.conn.prepareStatement(sql);
		for (String s : notPrimaryFields) {
			this.pst.setString(i, bean.get(s));
			i++;
		}
		int result = this.pst.executeUpdate();
		this.closeConnection();
		return (result == 1) ? true : false;
	}

	public boolean addBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();
		String sql = "INSERT INTO " + parentBean.relationship + "(id_"
				+ parentBean.identifier + ",id_" + childBean.identifier
				+ ") VALUES(?,?)";
		this.pst = this.conn.prepareStatement(sql);
		this.pst.setString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.pst.setString(2, childBean.get(childBean.fieldsList().get(0)));
		int result = this.pst.executeUpdate();
		this.closeConnection();
		return (result == 1) ? true : false;
	}
	
	public boolean deleteBeanRelationship(Bean parentBean, Bean childBean)
			throws SQLException {
		this.openConnection();
		String sql = "DELETE FROM " + parentBean.relationship + "  WHERE id_"
				+ parentBean.identifier + " = ? AND id_" + childBean.identifier
				+ " = ?";
		this.pst = this.conn.prepareStatement(sql);
		this.pst.setString(1, parentBean.get(parentBean.fieldsList().get(0)));
		this.pst.setString(2, childBean.get(childBean.fieldsList().get(0)));
		int result = this.pst.executeUpdate();
		this.closeConnection();
		return (result == 1) ? true : false;
	}

	public Bean selectBean(Bean bean) throws SQLException {
		this.openConnection();
		Bean result = null;
		String sql = "SELECT * FROM " + bean.identifier + " WHERE "
				+ bean.fieldsList().get(0) + " = ?";
		this.pst = this.conn.prepareStatement(sql);
		this.pst.setString(1, bean.get(bean.fieldsList().get(0)));
		ResultSet rs = this.pst.executeQuery();
		if (rs.next()) {
			result = init(bean.identifier);
			for (String s : bean.fieldsList()) {
				result.set(s, rs.getString(s));
			}
		}
		this.closeConnection();
		return result;
	}

	public ArrayList<Bean> selectAllBeans(Bean type) throws SQLException {
		this.openConnection();
		ArrayList<Bean> beans = new ArrayList<Bean>();
		String sql = "SELECT * FROM " + type.identifier;
		this.pst = this.conn.prepareStatement(sql);

		ResultSet rs = this.pst.executeQuery();
		while (rs.next()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, rs.getString(s));
			}
			beans.add(bean);
		}
		this.closeConnection();
		return beans;
	}

	public Integer countBean(Bean type) throws SQLException {
		Integer count = 0;
		String sql = "SELECT COUNT(*) FROM " + type.identifier;

		this.openConnection();
		this.pst = this.conn.prepareStatement(sql);

		ResultSet rs = this.pst.executeQuery();
		if (rs.next())
			count = rs.getInt(1);

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
		this.pst = this.conn.prepareStatement(sql);
		ResultSet rs = this.pst.executeQuery();

		if (rs.next()) {
			bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, rs.getString(s));
			}
		}
		this.closeConnection();

		return bean;
	}

	public ArrayList<Bean> selectBeanWhere(Bean type, String field,
			String value, boolean use_like) throws SQLException {
		ArrayList<Bean> beans = new ArrayList<Bean>();
		String sql = "SELECT * FROM " + type.identifier + " WHERE ";

		if (!use_like)
			sql += field+" =?";
		else
			sql += field+" LIKE ?";

		this.openConnection();
		this.pst = this.conn.prepareStatement(sql);
		if (use_like)
			this.pst.setString(1, "%" + value + "%");
		else
			this.pst.setString(1, value);

		ResultSet rs = this.pst.executeQuery();
		while (rs.next()) {
			Bean bean = init(type.identifier);
			for (String s : type.fieldsList()) {
				bean.set(s, rs.getString(s));
			}
			beans.add(bean);
		}
		this.closeConnection();

		return beans;
	}
	
	public boolean deleteBean(Bean bean) throws SQLException {
		this.openConnection();
		String sql = "DELETE FROM "+bean.identifier+ " WHERE "+bean.fieldsList().get(0)+" = ?";
		this.pst = this.conn.prepareStatement(sql);
		this.pst.setString(1, bean.get(bean.fieldsList().get(0)));
		int result = this.pst.executeUpdate();
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
