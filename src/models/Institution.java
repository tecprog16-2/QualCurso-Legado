package models;

import java.sql.SQLException;
import java.util.ArrayList;

public class Institution extends Bean {
	private int id;
	private String acronym;

	public Institution() {
		this.id = 0;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	public Institution(int id) {
		this.id = id;
		this.identifier = "institution";
		this.relationship = "courses_institutions";
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public boolean save() throws ClassNotFoundException, SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Institution.last().getId());
		return result;
	}
	
	public boolean addCourse(Course course) throws ClassNotFoundException, SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.addBeanRelationship(this, course);
		return result;
	}

	public static Institution get(int id) throws ClassNotFoundException,
			SQLException {
		Institution result = new Institution(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Institution) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Institution> getAll()
			throws ClassNotFoundException, SQLException {
		Institution type = new Institution();
		ArrayList<Institution> result = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type)) {
			result.add((Institution) b);
		}
		return result;
	}

	public static int count() throws ClassNotFoundException, SQLException {
		Institution type = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Institution first() throws ClassNotFoundException,
			SQLException {
		Institution result = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Institution) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Institution last() throws ClassNotFoundException,
			SQLException {
		Institution result = new Institution();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Institution) gDB.firstOrLastBean(result, true);
		return result;
	}

	public ArrayList<Course> getCourses() throws ClassNotFoundException,
			SQLException {
		ArrayList<Course> courses = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "course")) {
			courses.add((Course) b);
		}
		return courses;
	}
	
	

	public static ArrayList<Institution> getWhere(String field, String value,
			boolean like) throws ClassNotFoundException, SQLException {
		Institution type = new Institution();
		ArrayList<Institution> result = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like)) {
			result.add((Institution) b);
		}
		return result;
	}
	
	public boolean delete() throws ClassNotFoundException, SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		for(Course c : this.getCourses()){
			gDB.deleteBeanRelationship(this,c);
		}
		result = gDB.deleteBean(this);
		return result;
	}

	@Override
	public String get(String field) {
		if (field.equals("id")) {
			return Integer.toString(this.getId());
		} else if (field.equals("acronym")) {
			return this.getAcronym();
		} else {
			return "";
		}
	}

	@Override
	public void set(String field, String data) {
		if (field.equals("id")) {
			this.setId(Integer.parseInt(data));
		} else if (field.equals("acronym")) {
			this.setAcronym(data);
		} else {

		}

	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("id");
		fields.add("acronym");
		return fields;
	}

}
