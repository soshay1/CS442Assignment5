package assignment05;

import java.util.List;

public class SystemUserImpl implements SystemUser {
	private String bnum; 
	// can be student bnum, instructor bnum, or advisor bnum
	
	public SystemUserImpl(String bnumIn) {
		bnum = bnumIn;
	}
	
	public String getBnumber() {
		return bnum;
	}
	
	@Override
	public String getLastNames() {
		Student stu = DataResources.mapBNUMtoStudent.get(bnum);
		Instructor instr = DataResources.mapBNUMtoInstructor.get(bnum);
		Advisor adv = DataResources.mapBNUMtoAdvisor.get(bnum);
		if(stu != null) return stu.lastNames();
		if(instr != null) return instr.lastNames();
		return adv.lastNames();
	}

	@Override
	public String getFirstNames() {
		Student stu = DataResources.mapBNUMtoStudent.get(bnum);
		Instructor instr = DataResources.mapBNUMtoInstructor.get(bnum);
		Advisor adv = DataResources.mapBNUMtoAdvisor.get(bnum);
		if(stu != null) return stu.firstNames();
		if(instr != null) return instr.firstNames();
		return adv.firstNames();
	}

	@Override
	public List<String> getEnrollment() {
		Student stu = DataResources.mapBNUMtoStudent.get(bnum);
		// if stu is null, this is an instructor's bnum
		if(stu != null)
			return DataResources.getEnrolledClasses(bnum);
		return null;
	}

	@Override
	public double getGpa() {
		return DataResources.getGpa(bnum);
	}

	@Override
	public boolean requestRegistration(String crn) {
		return DataResources.enroll(bnum, crn);
	}

	@Override
	public boolean dropRegistration(String crn) {
		return DataResources.unEnroll(bnum, crn);
	}

	@Override
	public void setGrade(String studetBnum, String crn, String grade) {
		DataResources.assignGrade(studetBnum, crn, grade);
	}

	@Override
	public List<String> getClassList(String crn) {
		return DataResources.getClassList(crn);
	}

	@Override
	public String getGrade(String crn) {
		return DataResources.getGrade(bnum, crn);
	}

	@Override
	public String getStudentLastNames(String studnetBnum) {
		return DataResources.mapBNUMtoStudent.get(studnetBnum).lastNames();
	}

	@Override
	public String getStudentFirstNames(String studnetBnum) {
		return DataResources.mapBNUMtoStudent.get(studnetBnum).firstNames();
	}

	@Override
	public List<String> getStudentEnrollment(String studnetBnum) {
		return DataResources.getEnrolledClasses(studnetBnum);
	}

	@Override
	public double getStudentGpa(String studnetBnum) {
		return DataResources.getGpa(studnetBnum);
	}

	@Override
	public String getStudentGrade(String studnetBnum, String crn) {
		return DataResources.getGrade(studnetBnum, crn);
	}
	
}
