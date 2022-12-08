package assignment05;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class InstructorInvocationHandler implements InvocationHandler { 
	private SystemUser instructor;

	public InstructorInvocationHandler(SystemUser instructorIn) {
		instructor = instructorIn;
	}

	public Object invoke(Object proxy, Method method, Object[] args) 
			throws IllegalAccessException {

		try {
			if ((method.getName().equals("getClassList") 
							&& !DataResources.mapInstructortoCRNs.get(instructor.getBnumber()).contains(args[0])
							)
				|| (method.getName().equals("getStudentLastNames") && !DataResources.getInstructorClassListsCombined(instructor.getBnumber()).contains(args[0]))
				|| (method.getName().equals("getStudentFirstNames") && !DataResources.getInstructorClassListsCombined(instructor.getBnumber()).contains(args[0]))
				|| (method.getName().equals("setGrade") 
						&& !(DataResources.mapInstructortoCRNs.get(instructor.getBnumber()).contains(args[1]) && DataResources.getInstructorClassListsCombined(instructor.getBnumber()).contains(args[0]))
						)) {
				throw new IllegalAccessException("Cannot access this method with this argument");
				
			} else if(method.getName().equals("getClassList")
					||method.getName().equals("getStudentLastNames")
					||method.getName().equals("getStudentFirstNames")
					||method.getName().equals("setGrade")) { // checked constraints prior
				return method.invoke(instructor, args);
			}
				
			
			if (method.getName().equals("getBnumber")
					|| method.getName().equals("getLastNames")
					|| method.getName().equals("getFirstNames")
					|| method.getName().equals("getStudentLastNames")
					|| method.getName().equals("getStudentFirstNames")) {
				return method.invoke(instructor, args);
			} else 
				throw new IllegalAccessException("Cannot access this method");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
