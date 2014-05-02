package models;

import java.sql.SQLException;
import java.util.ArrayList;


public class Course extends Bean{
	private int id;
	private String name;

	public Course() {
		this.id = 0;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	public Course(int id){
		this.id = id;
		this.identifier= "course";
		this.relationship = "courses_institutions";
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean save() throws ClassNotFoundException, SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Course.last().getId());
		return result;
	}
	
	public boolean addInstitution(Institution institution) throws ClassNotFoundException, SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.addBeanRelationship(this, institution);
		return result;
	}


	public static Course get(int id) throws ClassNotFoundException,
			SQLException {
		Course result = new Course(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Course> getAll()
			throws ClassNotFoundException, SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type)) {
			result.add((Course) b);
		}
		return result;
	}

	public static int count() throws ClassNotFoundException, SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Course first() throws ClassNotFoundException,
			SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Course last() throws ClassNotFoundException,
			SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, true);
		return result;
	}

	public ArrayList<Institution> getInstitutions() throws ClassNotFoundException,
			SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "institution")) {
			institutions.add((Institution) b);
		}
		return institutions;
	}

	public static ArrayList<Course> getWhere(String field, String value,
			boolean like) throws ClassNotFoundException, SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like)) {
			result.add((Course) b);
		}
		return result;
	}

	public boolean delete() throws ClassNotFoundException, SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		for(Institution i : this.getInstitutions()){
			gDB.deleteBeanRelationship(this,i);
		}
		result = gDB.deleteBean(this);
		return result;
	}
	
	@Override
	public String get(String field) {
		if(field.equals("id")){
			return Integer.toString(this.getId());
		}else if(field.equals("name")){
			return this.getName();
		}else {
		return "";
		}
	}

	@Override
	public void set(String field, String data) {
		if(field.equals("id")){
			this.setId(Integer.parseInt(data));
		}else if(field.equals("name")){
			this.setName(data);
		}else {
		
		}
		
	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("id");
		fields.add("name");
		return fields;
	}
}
