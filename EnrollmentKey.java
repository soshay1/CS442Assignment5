package assignment05;

public record EnrollmentKey(String bnum, String crn) 
	implements Comparable<EnrollmentKey> {
	@Override
	public int compareTo(EnrollmentKey obj) {
		return (bnum + crn).compareTo(obj.bnum + obj.crn);
	}

	@Override
	public int hashCode() {
		return (bnum + crn).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrollmentKey other = (EnrollmentKey) obj;
		return (bnum + crn).equals(other.bnum + other.crn);
	}

	@Override
	public String toString() {
		return bnum + "-" + crn;
	}
}
