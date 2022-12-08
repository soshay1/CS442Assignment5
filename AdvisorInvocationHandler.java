package assignment05;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AdvisorInvocationHandler implements InvocationHandler { 
	private SystemUser advisor;

	public AdvisorInvocationHandler(SystemUser advisorIn) {
		advisor = advisorIn;
	}

	public Object invoke(Object proxy, Method method, Object[] args) 
			throws IllegalAccessException {

		try {
			if (method.getName().equals("getBnumber")
					|| method.getName().equals("getLastNames")
					|| method.getName().equals("getFirstNames")
					|| method.getName().equals("getClassList")
					|| method.getName().equals("getStudentLastNames")
					|| method.getName().equals("getStudentFirstNames")
					|| method.getName().equals("getStudentEnrollment")
					|| method.getName().equals("getStudentGpa")
					|| method.getName().equals("getStudentGrade")) {
				return method.invoke(advisor, args);
			} else 
				throw new IllegalAccessException("Cannot access this method");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
