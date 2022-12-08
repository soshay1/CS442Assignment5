package assignment05;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StudentInvocationHandler implements InvocationHandler { 
	private SystemUser student;

	public StudentInvocationHandler(SystemUser studentIn) {
		student = studentIn;
	}

	public Object invoke(Object proxy, Method method, Object[] args) 
			throws IllegalAccessException {

		try {
			if (method.getName().equals("getBnumber")
					|| method.getName().equals("getLastNames")
					|| method.getName().equals("getFirstNames")
					|| method.getName().equals("getEnrollment")
					|| method.getName().equals("getGpa")
					|| method.getName().equals("getGrade")
					|| method.getName().equals("requestRegistration")
					|| method.getName().equals("dropRegistration")) {
				return method.invoke(student, args);
			} else 
				throw new IllegalAccessException("Cannot access this method");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
