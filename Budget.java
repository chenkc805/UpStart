/**
  * Methods that allow a user to determine how to best spend their money
  *  
  * @author 	Kevin L. Chen
  * @date 		11/16/2015
  * @version	1.0
  */

import java.util.Arrays;

public class Budget {

	/** 
	 * Number of years for budgeting 
	 */
	private int N;

	/** 
	 * Array for yearly income amounts. Each (index + 1) repesents the year.
	 * Example: income[0] is the income for the first year.
	 */
	public int[] income;

	/** 
	 * Array for yearly financial goal amounts. Each (index + 1) repesents the year.
	 * Example: expenses[3] is the expenses for the fourth year.
	 */
	public int[] expenses;

	/** 
	 * Array for the net assets accumulated up to each year. Each (index + 1) repesents the year.
	 * Example: netAssets[4] is the net asset from years up to the fifth year.
	 */
	public int[] netAssets;

	/** 
	 * Array for yearly discretionary spending amounts. Each (index + 1) repesents the year.
	 * Example: discretionary[1] is the income for the second year.
	 */
	public int[] discretionary;

	/** 
	 * The final amount after all income and financial goals have been accounted for. 
	 * finalAmount = sum(income) - sum(expenses);
	 */
	public int finalAmount;

	/**  
	  *	Constructor for the Budget class. 
	  *
	  *  @param INCOME: An array of the user's income they expect to earn each year for the next N years. 
	  * 		Income can be as low as $0, and can vary year to year.
	  *  @param EXPENSES:  An array of all the user's financial goals they have for the next N years. 
	  * 		An example of a financial goal is: “I’d like to buy a house in 10 years costing $100,000.” 
	  *	   		Some years can have no financial goals.
	  */
	public Budget(int[] inputIncome, int[] inputExpenses) {
		income = inputIncome;
		expenses = inputExpenses;
		N = income.length;
	}

	/**  
	  *	Determines how a user can best spend her money given income and expenses over the next N years.
	  *	This is based off the assumption that the user's happiness is a function of their netAssets spending. 
	  *	The more they spend, the happier they are!
	  * The user experiences declining marginal utility with respect to spending in a particular year. 
	  * In other words, the first $100 spent in a year gives them more happiness than the second $100, 
	  * which gives them more happiness than the third $100, and so on. 
	  * This means the user would prefer to spend their netAssets dollars as evenly as possible across years. 
	  *
	  *  @param INCOME: An array of the user's income they expect to earn each year for the next N years. 
	  * 		Income can be as low as $0, and can vary year to year.
	  *  @param EXPENSES:  An array of all the user's financial goals they have for the next N years. 
	  * 		An example of a financial goal is: “I’d like to buy a house in 10 years costing $100,000.” 
	  *	   		Some years can have no financial goals.
	  */
	public int[] budgetingAlgorithm() {

		//Check if the two inputs are of length > 0 and same length.
		if (N == 0) {
			System.out.println("No values given to income.");
			return null;
		}
		if (N != expenses.length) {
			System.out.println("Length of years in INCOME and EXPENSES not equal.");
			return null;
		}

		//Create a new array, NETASSETS, to store the netAssets spending values
		// Fill the savings array
		netAssets = new int[N];
		netAssets[0] = income[0] - expenses[0];
		discretionary = new int[N];
		for (int i = 1; i < N; i++) {
			netAssets[i] = netAssets[i - 1] + income[i] - expenses[i];

		// Basic check if the user cannot make his/her financial goals 
		// without discretionary spending accounted for.
			if (netAssets[i] <= 0) {
				System.out.println("Unable to make financial goal in year " + i);
				return null;
			}
		}

		System.out.println(Arrays.toString(netAssets));

		// Make a first round by blindly and evenly dividing out the remaining discretionary
		// amount from the last year among all the years.
		finalAmount = netAssets[N - 1];
		divideToDiscretionary(finalAmount, 0, N - 1);
		int[] checkMatch = new int[N];
		System.arraycopy(discretionary, 0, checkMatch, 0, netAssets.length);

		// Recaculate the discretionary spending based off of two constraints:
		// 		1) You cannot spend more than you make.
		//  	2) You have to meet all your financial goals with discretionary spending accounted for
		// This part will keep iterating through until discretionary spending cannot be further optimized,
		// at which point the loop terminates and the final discretionary spending plan is returned.
		while (true) {
			System.arraycopy(discretionary, 0, checkMatch, 0, netAssets.length);
			recalculateDiscretionary();
			recalculateSpending();
			if (Arrays.equals(checkMatch, discretionary)) {
				break;
			}
		}

		return discretionary;
	}

	/**  
	  *	A method that allocates discretionary spending for certain sections of the discretionary array. 
	  *
	  *  @param AMOUNT: The amount to be divided evenly
	  *  @param START: The beginnning of the section
	  *  @param END: The end of the section
	  */
	private void divideToDiscretionary(int amount, int start, int end) {
		if ((end - start + 1) == 0) {
			return;
		} else {
			int averageDiscretionary = amount/(end - start + 1);
			for (int i = start; i <= end; i++) {
				discretionary[i] = averageDiscretionary;
			}
		}
	}

	/**  
	  *	A method that recalculates discretionary spending if a certain year's discretionary spending is more than the year's income.
	  */
	private void recalculateDiscretionary() {
		int leftOver = finalAmount;
		for (int i = 0; i < N; i++) {
			if (discretionary[i] > netAssets[i]) {
				discretionary[i] = netAssets[i];
				leftOver -= discretionary[i];
				divideToDiscretionary(leftOver, i + 1, N - 1);
				// for (int j = i; j < N; j++) {
				// 	netAssets[j] = netAssets[j] - discretionary[i];
				// }
				System.out.println(Arrays.toString(netAssets));
			} else {
				leftOver -= discretionary[i];
			}
		}
	}

	/**  
	  *	A method that calculates how much money is used for discretionary spending up to a certain end year.
	  *
	  * @param END: The last year to include in the total discretionary spending.
	  * @return The total discretionary spending
	  */
	private int totalDiscretionarySoFar(int end) {
		int result = 0;
		for (int i = 0; i <= end; i++) {
			result += discretionary[i];
		}
		return result;
	}

	/**  
	  *	A method that calculates how much money the user has saved (income - expenses - discretionary) up to a certain end year.
	  *
	  * @param END: The last year to include in the total amount spent.
	  * @return The total amount spent
	  */
	private int totalSaved(int end) {
		int result = 0;
		for (int i = 0; i <= end; i++) {
			result = result + income[i] - discretionary[i];
		}
		return result;
	}

	/**  
	  *	A method that recalculates the discretionary spending to make sure the 
	  * budget doesn't allow for discretionary spending up to the point where the user
	  * can no longer afford his/her financial goals.
	  */
	private void recalculateSpending() {
		int totalDiscretionarySoFar = 0;
		int sumDiscretionary = 0;
		int totalSaved = 0;
		int start = 0;
		for (int i = 0; i < N; i++) {
			totalDiscretionarySoFar = this.totalDiscretionarySoFar(i);
			if (expenses[i] != 0) {
				totalSaved = this.totalSaved(i);
				if (totalSaved < expenses[i]) {
					divideToDiscretionary(totalDiscretionarySoFar - expenses[i], start, i);
					sumDiscretionary += this.totalDiscretionarySoFar(i);
					divideToDiscretionary(finalAmount - sumDiscretionary, i + 1, N - 1);
					break;
				} else {
					sumDiscretionary += this.totalDiscretionarySoFar(i);
				}
				start = i + 1;
			}
		}
	}
}



















