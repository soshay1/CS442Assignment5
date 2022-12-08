package assignment05;

public record Student(String lastNames, String firstNames, String bNumber) {
	public String toString() {
		return firstNames + " " + lastNames + " (" + bNumber + ")";
	}
}
