package assignment05;

public record Course(String courseRubric, String courseNumber, 
		String courseTitle, int credits, String section) {
	public String toString() {
		return courseRubric + courseNumber + "-" + section;
	}
}
