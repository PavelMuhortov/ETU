package lab1;

import java.util.Arrays;

/**
 * @author Pavel Muhortov, Pavel Gavrilov
 * @version 0.1.1
 *
 */
public class SortingArray {
	
	/**
	 * @param args not processed
	 * @return no return values
	 */
	public static void main(String[] args) {
		int[] myArray = {9,5,1,6,2,3,0,4,8,0,7};
		System.out.print("\nНесортированный массив: ");
		for(int i=0; i<myArray.length; i++) {System.out.print(myArray[i]);}
		
		int[] sortControlArray = myArray;
		System.out.print("\nКонтрольная сортировка: ");
		Arrays.parallelSort(sortControlArray);
		for(int i=0; i<sortControlArray.length; i++) {System.out.print(sortControlArray[i]);}
		
		int[] sortArray = myArray;
		boolean needIteration = true;
		while (needIteration) {
			needIteration = false;
			for (int i=1; i < sortArray.length; i++) {
				if (sortArray[i] < sortArray[i-1]) {
					int tempElement = sortArray[i-1];
					sortArray[i-1] = sortArray[i];
					sortArray[i] = tempElement;
					needIteration = true;
				}
			}
		}
		System.out.print("\nОтсортированный массив: ");
		for(int i=0; i<sortArray.length; i++) {System.out.print(sortArray[i]);}
	}
}
