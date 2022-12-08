package assignment05;

import java.util.Iterator;
import java.util.Random;

public class RandomDataSetup {

	public static void setup() {
		String[] gradeLetters = {"F", "D", "C-", "C", "C+", "B-", "B", "B+", "A-", "A"};
		Random rand = new Random(1);
		// System.out.println("DEBUG " + DataResources.mapCapacities);
		int numCourses = DataResources.mapCRNtoCourse.keySet().size();
		String[] crnsArray = new String[numCourses];
		Iterator<String> iter = DataResources.mapCRNtoCourse.keySet().iterator();
		for(int i = 0; i < numCourses; i++) 
			crnsArray[i] = iter.next();
		boolean testEnroll = false;
		int crn = 0;
 		for(String bnum : DataResources.mapBNUMtoStudent.keySet()) {
 			do {
 				crn = rand.nextInt(numCourses);
 				testEnroll = DataResources.enroll(bnum, crnsArray[crn]);
 			} while(!testEnroll);
 			DataResources.assignGrade(bnum, crnsArray[crn], gradeLetters[rand.nextInt(10)]);
		}
 		for(String bnum : DataResources.mapBNUMtoStudent.keySet()) {
 			do {
 				crn = rand.nextInt(numCourses);
 				testEnroll = DataResources.enroll(bnum, crnsArray[crn]);
 			} while(!testEnroll);
 			DataResources.assignGrade(bnum, crnsArray[crn], gradeLetters[rand.nextInt(10)]);
		}
 		for(String bnum : DataResources.mapBNUMtoStudent.keySet()) {
 			do {
 				crn = rand.nextInt(numCourses);
 				testEnroll = DataResources.enroll(bnum, crnsArray[crn]);
 			} while(!testEnroll);
 			DataResources.assignGrade(bnum, crnsArray[crn], gradeLetters[rand.nextInt(10)]);
		}
 		for(String bnum : DataResources.mapBNUMtoStudent.keySet()) {
 			do {
 				crn = rand.nextInt(numCourses);
 				testEnroll = DataResources.enroll(bnum, crnsArray[crn]);
 			} while(!testEnroll);
 			DataResources.assignGrade(bnum, crnsArray[crn], gradeLetters[rand.nextInt(10)]);
// 			System.out.println(DataResources.mapBNUMtoStudent.get(bnum) 
// 					+ " " +
// 					DataResources.getEnrolledClasses(bnum));
		}		
	}
}
