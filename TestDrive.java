
package assignment05;

import java.lang.reflect.Proxy;
import java.util.List;

public class TestDrive {

	public static void main(String[] args) {
		RandomDataSetup.setup();
		SystemUser studentProxy = getStudentProxy(new SystemUserImpl("B00119628"));
		System.out.println("Check student can call for their b number");
		System.out.println("\t" + studentProxy.getBnumber());
		System.out.println("Check student can call for their first name");
		System.out.println("\t" + studentProxy.getFirstNames());
		System.out.println("Check student can call for their last name");
		System.out.println("\t" + studentProxy.getLastNames());
		System.out.println("Check student can call for their enrollment list of crns");
		List<String> lst = studentProxy.getEnrollment();
		System.out.println("\t" + studentProxy.getEnrollment());
		System.out.println("Check student can see their course grades (also print courses");
		for(String crn : lst) {
			Course crs = DataResources.mapCRNtoCourse.get(crn);
			System.out.println("\t" + crs + " " + crs.courseTitle() + " " + studentProxy.getGrade(crn));
		}
		System.out.println("Check student can call for their gpa");
		System.out.println("\t" + studentProxy.getGpa());
		System.out.println("Check student can call to drop a registration");
		System.out.println("\t" + studentProxy.dropRegistration(lst.get(0)));
		lst = studentProxy.getEnrollment();
		System.out.println("Show new enrollment crns");
		System.out.println("\t" + studentProxy.getEnrollment());
		System.out.println("Show new gpa eliminating dropped course");
		System.out.println("\t" + studentProxy.getGpa());
		System.out.println("Check student can call to request a new registration");
		System.out.println("\t" + studentProxy.requestRegistration("12345"));
		lst = studentProxy.getEnrollment();
		System.out.println("Show new enrollment crns");
		System.out.println("\t" + studentProxy.getEnrollment());
		System.out.println("Check call to request same course is false");
		System.out.println("\t" + studentProxy.requestRegistration("12345"));
		System.out.println("Show the course grades (also print courses");
		for(String crn : lst) {
			Course crs = DataResources.mapCRNtoCourse.get(crn);
			System.out.println("\t" + crs + " " + crs.courseTitle() + " " + studentProxy.getGrade(crn));
		}
		System.out.println("Show new gpa counting \"no-grade\" as an F");
		System.out.println("\t" + studentProxy.getGpa());
		System.out.println("Show the student cannot set a grade");
		try {
			studentProxy.setGrade("B00119628", lst.get(0), "A");
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot set their grades");
		}
		System.out.println("Show the student cannot get their class list");
		try {
			studentProxy.getClassList(lst.get(0));
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot get the list of students in their class");
		}
		System.out.println("Show the student cannot get last name of some other b-number");
		try {
			studentProxy.getStudentLastNames("B00119628");
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot use advisor methods (last name)");
		}
		System.out.println("Show the student cannot get first name of some other b-number");
		try {
			studentProxy.getStudentFirstNames("B00119628");
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot use advisor methods (first name)");
		}
		System.out.println("Show the student cannot get enrolled courses of some other b-number");
		try {
			studentProxy.getStudentEnrollment("B00119628");
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot use advisor methods (courses registered)");
		}
		System.out.println("Show the student cannot get gpa of some other b-number");
		try {
			studentProxy.getStudentGpa("B00119628");
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot use advisor methods (gpa)");
		}
		System.out.println("Show the student cannot get a grade of some other b-number");
		try {
			studentProxy.getStudentGrade("B00119628", lst.get(0));
		} catch (Exception e) {
			System.out.println("\t" + "Students cannot use advisor methods (course grade)");
		}
		
		SystemUser instructorProxy = getInstructorProxy(new SystemUserImpl("B00017176"));
		System.out.println("Check instructor can call for their b number");
		System.out.println("\t" + instructorProxy.getBnumber());
		System.out.println("Check instructor can call for their first name");
		System.out.println("\t" + instructorProxy.getFirstNames());
		System.out.println("Check instructor can call for their last name");
		System.out.println("\t" + instructorProxy.getLastNames());
		System.out.println("Check instructor cannot call a student's enrollment list");
		try {
			lst = instructorProxy.getEnrollment();
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot use student methods (get enrollment)");
		}
		System.out.println("Check instructor cannot call a student's gpa");
		try {
			System.out.println("\t" + instructorProxy.getGpa());
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot use student methods (get gpa)");
		}
		System.out.println("Check instructor cannot drop a student's registration");
		try {
			System.out.println("\t" + instructorProxy.dropRegistration(lst.get(0)));
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot use student methods (drop registration)");
		}
		System.out.println("Check instructor cannot enroll a student");
		try {
			System.out.println("\t" + instructorProxy.requestRegistration("12345"));
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot use student methods (request registration)");
		}
		// get crn of class taught by instructor:
		System.out.println("\t" + "Taught by this instructor\n\t" + DataResources.mapInstructortoCRNs.get(instructorProxy.getBnumber()));
		String crn = DataResources.mapInstructortoCRNs.get(instructorProxy.getBnumber()).iterator().next();
		// get bnum for student in class
		String studentBnum = DataResources.getClassList(crn).get(0);
		EnrollmentKey enrl = new EnrollmentKey(studentBnum, crn);
		System.out.println("Old grade: " + DataResources.mapEnrollmentToGrade.get(enrl));
		System.out.println("Calling assign A- to " + studentBnum +" in " + crn);
		System.out.println("Check instructor can set a grade for a student in their class");		
		instructorProxy.setGrade(studentBnum, crn, "A-");
		System.out.println("New grade: " + DataResources.mapEnrollmentToGrade.get(enrl));
		String notInClassStudentBnum = "B00101356"; // not in the class
		System.out.println("Check instructor cannot set a grade for a student NOT in their class");		
		try {
			System.out.println("\t" + "Calling assign A- to " + notInClassStudentBnum +" in " + crn);
			instructorProxy.setGrade(notInClassStudentBnum, crn, "A-");
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot change the grade of someone not in the class");
		}
		String aDifferentCrn = "12362"; // different class, not taught by this instructor
		String studentBnumIn_12362 = "B00194450"; //student in that class
		System.out.println("Check instructor cannot set a grade for a student in a different class");		
		try {
			System.out.println("\t" + "Calling assign A- to " + studentBnumIn_12362 +" in " + aDifferentCrn);
			instructorProxy.setGrade(studentBnumIn_12362, aDifferentCrn, "A-");
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot change the grade of someone in a different class");
		}
		System.out.println("Check instructor can get their class list");		
		System.out.println("\t" + instructorProxy.getClassList(crn));
		System.out.println("Check instructor cannot get the class list for a course that is not theirs");		
		try {
			instructorProxy.getClassList(aDifferentCrn);
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot get class list of a different class");
		}
		System.out.println("Check instructor can get the last names for someone in their class");		
		System.out.println("\t" + instructorProxy.getStudentLastNames(studentBnum));
		System.out.println("Check instructor can get the first names for someone in their class");		
		System.out.println("\t" + instructorProxy.getStudentFirstNames(studentBnum));
		System.out.println("Check instructor cannot get the last names for someone NOT in their class");		
		try {
			instructorProxy.getStudentLastNames(notInClassStudentBnum);
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot get last names of student not in class");
		}
		System.out.println("Check instructor cannot get the first names for someone NOT in their class");		
		try {
			instructorProxy.getStudentFirstNames(notInClassStudentBnum);
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot get first names of student not in class");
		}
		System.out.println("Check instructor cannot get enrollment of a student using their b-number");		
		try {
			instructorProxy.getStudentEnrollment(studentBnum);
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot get the enrollments of students in class");
		}
		System.out.println("Check instructor cannot get the gpa of a student using their b-number");		
		try {
			instructorProxy.getStudentGpa(studentBnum);
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot get gpa of students in class");
		}
		System.out.println("Check instructor cannot get the grade of a student using their b-number and course crn");		
		try {
			instructorProxy.getStudentGrade(studentBnum, crn);
		} catch (Exception e) {
			System.out.println("\t" + "Instructors cannot set grade but cannot read grades from the database, even for their class");
		}

		SystemUser advisorProxy = getAdvisorProxy(new SystemUserImpl("B00027649"));
		System.out.println("Check advisor can call for their b number");
		System.out.println("\t" + advisorProxy.getBnumber());
		System.out.println("Check advisor can call for their own first name");
		System.out.println("\t" + advisorProxy.getFirstNames());
		System.out.println("Check advisor can call for their own last name");
		System.out.println("\t" + advisorProxy.getLastNames());
		System.out.println("Check advisor cannot call student's method to get enrollment");
		try {
			lst = advisorProxy.getEnrollment();
		} catch (Exception e) {
			System.out.println("\t" + "Advisors cannot use student methods (get enrollment)");
		}
		System.out.println("Check advisor cannot call student's method to get gpa");
		try {
			System.out.println("\t" + advisorProxy.getGpa());
		} catch (Exception e) {
			System.out.println("\t" + "Advisors cannot use student methods (get gpa)");
		}
		System.out.println("Check advisor cannot call student's method to drop registration");
		try {
			System.out.println("\t" + advisorProxy.dropRegistration(lst.get(0)));
		} catch (Exception e) {
			System.out.println("\t" + "Advisors cannot use student methods (drop registration)");
		}
		System.out.println("Check advisor cannot call student's method to add a registration");
		try {
			System.out.println("\t" + advisorProxy.requestRegistration("12345"));
		} catch (Exception e) {
			System.out.println("\t" + "Advisors cannot use student methods (request registration)");
		}
		System.out.println("Check advisor cannot call set a student's grade");		
		try {
			advisorProxy.setGrade(studentBnum, crn, "A-");
		} catch (Exception e) {
			System.out.println("\t" + "Advisors cannot change grades");
		}
		System.out.println("Check advisor can see class list of any valid crn");
		System.out.println("\t" + advisorProxy.getClassList(crn));
		System.out.println("Check advisor cannot call class list for a non-valid crn");		
		try {
			advisorProxy.getClassList("11111");
		} catch (Exception e) {
			System.out.println("\t" + "Advisors get a class list for a non-valid crn");
		}
		System.out.println("Check advisor can see last names of any valid student");
		System.out.println("\t" + advisorProxy.getStudentLastNames(studentBnum));
		System.out.println("Check advisor cannot get last name of a non-valid student");		
		try {
			advisorProxy.getStudentLastNames("B00999999");
		} catch (Exception e) {
			System.out.println("\t" + "Advisors get a last name for a non-valid bnum");
		}
		System.out.println("Check advisor can see first names of any valid student");
		System.out.println("\t" + advisorProxy.getStudentFirstNames(studentBnum));
		System.out.println("Check advisor can see enrollments of any valid student");
		System.out.println("\t" + advisorProxy.getStudentEnrollment(studentBnum));
		System.out.println("Check advisor can see gpa of any valid student");
		System.out.println("\t" + advisorProxy.getStudentGpa(studentBnum));
		System.out.println("Check advisor can see grade of any valid student in any valid course");
		System.out.println("\t" + advisorProxy.getStudentGrade(studentBnum, crn));
	}

	static SystemUser getStudentProxy(SystemUser student) {	
        return (SystemUser) Proxy.newProxyInstance( 
        		student.getClass().getClassLoader(),
            	student.getClass().getInterfaces(),
                new StudentInvocationHandler(student));
	}

	static SystemUser getAdvisorProxy(SystemUser advisor) {	
        return (SystemUser) Proxy.newProxyInstance( 
        		advisor.getClass().getClassLoader(),
        		advisor.getClass().getInterfaces(),
                new AdvisorInvocationHandler(advisor));
	}
	
	static SystemUser getInstructorProxy(SystemUser instructor) {	
        return (SystemUser) Proxy.newProxyInstance( 
        		instructor.getClass().getClassLoader(),
            	instructor.getClass().getInterfaces(),
                new InstructorInvocationHandler(instructor));
	}
}
