package assignment05;

import java.util.List;

public interface SystemUser {
	// 3 general methods (everyone can see their own name)
	String getBnumber();
	String getLastNames();
	String getFirstNames();
	// 5 student-only methods
	List<String> getEnrollment();
	double getGpa();
	String getGrade(String crn);
	boolean requestRegistration(String crn);
	boolean dropRegistration(String crn);

	// For Instructor:
	// InstructorInvocationHandler has 2 more fields:
	// private List<String> studentsInClass;
	// private Set<String> crns;
	// in the constructor, studentsInClass is given the value
	// DataResources.getInstructorClassListsCombined(instructor.getBnumber())
	// in the constructor crns is given the value
	// DataResources.mapInstructortoCRNs.get(instructor.getBnumber())
	// instructor-only method:
	// in this method, only invoke the method if studentsInClass contains args[0]
	// and crns contains args[1]
	// otherwise throw new IllegalAccessException("Cannot access this method with these arguments")
	void setGrade(String studentBnum, String crn, String grade);	
	// instructor method, advisor method
	// when an instructor method only invoke the method if 
	// crns contains args[0]
	// otherwise throw new IllegalAccessException("Cannot access this method with this argument")
	// no such restriction for advisor
	List<String> getClassList(String crn);
	// 2 instructor methods, advisor methods
	// when an instructor method only invoke the method if 
	// if studentsInClass contains args[0]
	// otherwise throw new IllegalAccessException("Cannot access this method with this argument")
	// no such restriction for advisor
	String getStudentLastNames(String bnum);
	String getStudentFirstNames(String bnum);
	// 3 advisor-only methods
	List<String> getStudentEnrollment(String bnum);
	double getStudentGpa(String bnum);
	String getStudentGrade(String bnum, String crn);
}
