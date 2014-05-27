package models;

import android.database.SQLException;
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
	
	public boolean save() throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.insertBean(this);
		this.setId(Course.last().getId());
		return result;
	}
	
	public boolean addInstitution(Institution institution) throws  SQLException {
		boolean result = false;
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = gDB.addBeanRelationship(this, institution);
		return result;
	}


	public static Course get(int id) throws 
			SQLException {
		Course result = new Course(id);
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.selectBean(result);
		return result;
	}

	public static ArrayList<Course> getAll()
			throws  SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectAllBeans(type)) {
			result.add((Course) b);
		}
		return result;
	}

	public static int count() throws  SQLException {
		Course type = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		return gDB.countBean(type);
	}

	public static Course first() throws 
			SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, false);
		return result;
	}

	public static Course last() throws 
			SQLException {
		Course result = new Course();
		GenericBeanDAO gDB = new GenericBeanDAO();
		result = (Course) gDB.firstOrLastBean(result, true);
		return result;
	}

	public ArrayList<Institution> getInstitutions() throws 
			SQLException {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanRelationship(this, "institution")) {
			institutions.add((Institution) b);
		}
		return institutions;
	}

	public static ArrayList<Course> getWhere(String field, String value,
			boolean like) throws  SQLException {
		Course type = new Course();
		ArrayList<Course> result = new ArrayList<Course>();
		GenericBeanDAO gDB = new GenericBeanDAO();
		for (Bean b : gDB.selectBeanWhere(type, field, value, like)) {
			result.add((Course) b);
		}
		return result;
	}

	public static ArrayList<Course> getCoursesByEvaluationFilter(String filterField, String year, String minInterval, String maxInterval) throws  SQLException {
		ArrayList<Course> result = new ArrayList<Course>();
		String sql = "SELECT DISTINCT id_course from evaluation"+
					" WHERE year="+year+
					" AND "+filterField;
		
		if(maxInterval == "MAX" || maxInterval == "max")
			sql+=" >= "+minInterval;
		else
			sql+=" BETWEEN "+minInterval+" AND "+maxInterval;

		GenericBeanDAO gDB = new GenericBeanDAO();

		for (String sqlResponse[] : gDB.runSql(sql))
			result.add(Course.get(Integer.parseInt(sqlResponse[0])));

		return result;
	}

	public static ArrayList<Institution> getInstitutionsByEvaluationFilter(String id_course, String filterField, String year, String minInterval, String maxInterval) throws  SQLException {
		ArrayList<Institution> result = new ArrayList<Institution>();
		String sql = "SELECT id_institution from evaluation"+
					" WHERE id_course="+id_course+
					" AND year="+year+
					" AND "+filterField;
		
		if(maxInterval == "MAX" || maxInterval == "max")
			sql+=" >= "+minInterval;
		else
			sql+=" BETWEEN "+minInterval+" AND "+maxInterval;

		GenericBeanDAO gDB = new GenericBeanDAO();

		for (String sqlResponse[] : gDB.runSql(sql))
			result.add(Institution.get(Integer.parseInt(sqlResponse[0])));

		return result;
	}

	public boolean delete() throws  SQLException {
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
		if(field.equals("_id")){
			return Integer.toString(this.getId());
		}else if(field.equals("name")){
			return this.getName();
		}else {
		return "";
		}
	}

	@Override
	public void set(String field, String data) {
		if(field.equals("_id")){
			this.setId(Integer.parseInt(data));
		}else if(field.equals("name")){
			this.setName(data);
		}else {
		
		}
		
	}

	@Override
	public ArrayList<String> fieldsList() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("_id");
		fields.add("name");
		return fields;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	
}
