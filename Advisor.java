package assignment05;

public record Advisor(String lastNames, String firstNames, String bNumber) {
	public String toString() {
		return firstNames + " " + lastNames + " (" + bNumber + ")";
	}
}
