package assignment05;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataResources {
	public static final Map<Double, String> LETTER_GRADE = 
			Map.ofEntries(entry(4.0, "A"), entry(3.7, "A-"), entry(3.3, "B+"),
					entry(3.0, "B"), entry(2.7, "B-"), entry(2.3, "C+"), 
					entry(2.0, "C"), entry(1.7, "C-"), entry(1.0, "D"), entry(0.0, "F"));
	public static final Map<String, Double> GRADE_POINTS = 
			Map.ofEntries(entry("A", 4.0), entry("A-", 3.7), entry("B+", 3.3),
					entry("B", 3.0), entry("B-", 2.7), entry("C+", 2.3), 
					entry("C", 2.0), entry("C-", 1.7), entry("D", 1.0), entry("F", 0.0), entry("-", 0.0));
	public static Map<String, Integer> mapCapacities = new TreeMap<>(); //key CRN
	public static Map<String, Integer> mapNumEnrolled = new TreeMap<>(); //key CRN
	public static Map<String, Course> mapCRNtoCourse = new TreeMap<>(); //key CRN
	public static Map<String, Student> mapBNUMtoStudent = new TreeMap<>(); // bnum -> student with that bnum
	public static Map<String, Instructor> mapBNUMtoInstructor = new TreeMap<>(); //key Bnum (to CRN)
	public static Map<String, String> mapCRNtoInstructor = new TreeMap<>(); //key CRN (to Bnum)
	public static Map<String, Set<String>> mapInstructortoCRNs = new TreeMap<>(); //key Bnum (to set of CRN)
	public static Map<EnrollmentKey, String> mapEnrollmentToGrade = new TreeMap<>(); //key CRN (to Bnum)
	public static Map<String, Advisor> mapBNUMtoAdvisor = new TreeMap<>(); //key Bnum (to CRN)

	static {
		int crn = 12345;
		int count = 0; 
		for(Course crs : Data.courses) {
			mapCRNtoCourse.put("" + crn, crs);
			mapCapacities.put("" + crn, (int)Math.round(10*(1 + Math.random()))); // from 10 to 19
			mapNumEnrolled.put("" + crn, 0);
			Instructor instr = Data.instructors[count++];
			mapCRNtoInstructor.put("" + crn, instr.bNumber());
			Set<String> set = mapInstructortoCRNs.get(instr.bNumber());
			if(set == null) set = new TreeSet<>();
			set.add("" + crn);
			mapInstructortoCRNs.put(instr.bNumber(), set);
			mapBNUMtoInstructor.put(instr.bNumber(), instr);
			crn++;
		}
//		System.out.println("DEBUG " + mapCRNtoCourse);
		for(Student std : Data.students) {
			mapBNUMtoStudent.put(std.bNumber(), std);
		}
		for(Advisor adv : Data.advisors) {
			mapBNUMtoAdvisor.put(adv.bNumber(), adv);
		}
//		System.out.println("DEBUG " + mapBNUMtoStudent);
//		System.out.println("DEBUG " + mapBNUMtoInstructor);
//		System.out.println("DEBUG " + mapBNUMtoAdvisor);
//		System.out.println("DEBUG " + mapInstructortoCRNs);
	}
	
	public static boolean enroll(String bnum, String crn) {
		var key = new EnrollmentKey(bnum, crn);
		if(mapEnrollmentToGrade.containsKey(key))
			return false; // already enrolled
		mapEnrollmentToGrade.put(key, "-");
		return true;
	}

	public static boolean unEnroll(String bnum, String crn) {
		var key = new EnrollmentKey(bnum, crn);
		if(!mapEnrollmentToGrade.containsKey(key))
			return false; // no such enrollment
		mapEnrollmentToGrade.remove(key);
		return true;
	}
	
	public static boolean assignGrade(String bnum, String crn, String grade) {
		var key = new EnrollmentKey(bnum, crn);
		if(!mapEnrollmentToGrade.containsKey(key)) {
			System.out.println("Not enrolled in this class");
			return false;
		}
		mapEnrollmentToGrade.put(key, grade);
		return true;
	}

	public static List<String> getClassList(String crn) {
		List<String> list = new ArrayList<>();
		for(var key : mapEnrollmentToGrade.keySet()) {
			if(key.crn().equals(crn))
				list.add(key.bnum());
		}
		return list;
	}

	public static List<String> getInstructorClassListsCombined(String bnum) {
		Set<String> crns = mapInstructortoCRNs.get(bnum);
		List<String> list = new ArrayList<>();
		for(var crn : crns) {
			list.addAll(getClassList(crn));
		}
		return list;
	}

	public static List<String> getEnrolledClasses(String bnum) {
		List<String> list = new ArrayList<>();
		for(var key : mapEnrollmentToGrade.keySet()) {
			if(key.bnum().equals(bnum))
				list.add(key.crn());
		}
		return list;
	}

	public static String getGrade(String bnum, String crn) {
		var key = new EnrollmentKey(bnum, crn);
		return mapEnrollmentToGrade.get(key);
	}

	public static double getGpa(String bnum) {
		int credits = 0;
		double qualPts = 0.0;
		List<String> enrolledClasses = getEnrolledClasses(bnum);
		for(String crn : enrolledClasses) {
			Course course = mapCRNtoCourse.get(crn);
			//System.out.println("DEBUG getGpa " + course);
			int temp = course.credits();
			String grd = getGrade(bnum, crn);
			//System.out.println("DEBUG getGpa " + temp + " -> " + grd);
			credits += temp;
			qualPts += temp*GRADE_POINTS.get(grd);			
			//System.out.println("DEBUG getGpa " + credits + "/" + grd + "/" + qualPts);
		}
		double gpa = 0.0;
		if(credits > 0) gpa = qualPts/credits; 
		gpa = Math.round(gpa*1000.0)/1000.0;
		//System.out.println("DEBUG " + gpa);
		return gpa;
	}
}
