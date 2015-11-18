/**
  * //////////////
  * //          //
  * //  Budget  // 
  * //          //
  * //////////////
  * 
  * Description: A class that allows a user to determine how to best spend their money
  * Summary of how this code functions:
  *  1) Check if financial goals can be met with no discretionary spending.
  *  2) Begin allocating income to discretionary spending without going over the user's net assets 
  *  3) Reallocate income to discretionary spending to make sure that financial goals are still being met 
  *     with the discretionary budget set in step 2.
  *  4) Smooth out discretionary spending based off of the observation that, say, 
  *     for a couple of years the user has a lot of discretionary spending and then the following
  *     couple of years the user has less discretionary spending, then the discretionary spending from the 
  *     earlier years can be allocated to the future years to maximize happiness without compromising spending goals.
  *  5) Iterate through step 2-4 until the array cannot be changed any futher. The resulting array is the optimized 
  *     discretionary spending array.
  *  6) For each year, add the discretionary spending and expenses values together to get the final 
  *     array of spending projects for each year.  
  *  
  * @author     Kevin L. Chen
  * @date       11/16/2015
  * @version    1.0
  */

import java.util.Arrays;
import java.util.LinkedList;

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
     * A LinkedList of the points where discretionary spending changes in the discretionary array.
     */
    private LinkedList<Integer> transitions;

    /** 
     * The yearly spending with discretionary spending and financial goals accounted for.
     */
    private int[] finalSpending;

    /**  
      * Constructor for the Budget class. 
      *
      *  @param INCOME: An array of the user's income they expect to earn each year for the next N years. 
      *         Income can be as low as $0, and can vary year to year.
      *  @param EXPENSES:  An array of all the user's financial goals they have for the next N years. 
      *         An example of a financial goal is: “I’d like to buy a house in 10 years costing $100,000.” 
      *         Some years can have no financial goals.
      */
    public Budget(int[] inputIncome, int[] inputExpenses) {
        income = inputIncome;
        expenses = inputExpenses;
        N = income.length;
    }

    /**  
      * Determines how a user can best spend her money given income and expenses over the next N years.
      * This is based off the assumption that the user's happiness is a function of their netAssets spending. 
      * The more they spend, the happier they are!
      * The user experiences declining marginal utility with respect to spending in a particular year. 
      * In other words, the first $100 spent in a year gives them more happiness than the second $100, 
      * which gives them more happiness than the third $100, and so on. 
      * This means the user would prefer to spend their netAssets dollars as evenly as possible across years. 
      *
      *  @param INCOME: An array of the user's income they expect to earn each year for the next N years. 
      *         Income can be as low as $0, and can vary year to year.
      *  @param EXPENSES:  An array of all the user's financial goals they have for the next N years. 
      *         An example of a financial goal is: “I’d like to buy a house in 10 years costing $100,000.” 
      *         Some years can have no financial goals.
      */
    public int[] budgetingAlgorithm() {

        //Check if the two inputs are of length > 0 and same length.
        if (N == 0) {
            System.out.println("No values given to INCOME.");
            return null;
        }
        if (N != expenses.length) {
            System.out.println("Length of years in INCOME and EXPENSES not equal.");
            return null;
        }

        //Create a new array, NETASSETS, to store the netAssets spending values
        // Fill the savings array
        netAssets = new int[N];
        discretionary = new int[N];
        for (int i = 0; i < N; i++) {
            if (i == 0) {
                netAssets[0] = income[0] - expenses[0];
            } else {
                netAssets[i] = netAssets[i - 1] + income[i] - expenses[i];
            }

        // Basic check if the user cannot make his/her financial goals 
        // without discretionary spending accounted for.
            if (netAssets[i] < 0) {
                System.out.println("Unable to make financial goal in year " + (i + 1) + ".");
                return null;
            }
        }

        // Make a first round by blindly and evenly dividing out the remaining discretionary
        // amount from the last year among all the years.
        finalAmount = netAssets[N - 1];
        divideToDiscretionary(finalAmount, 0, N - 1);
        int[] checkMatch = new int[N];
        System.arraycopy(discretionary, 0, checkMatch, 0, netAssets.length);

        // Recalculate the discretionary spending based off of two constraints:
        //      1) You cannot spend more than you make.
        //      2) You have to meet all your financial goals with discretionary spending accounted for
        // This part will keep iterating through until discretionary spending cannot be further optimized,
        // at which point the loop terminates and the final discretionary spending plan is returned.
        while (true) {
            System.arraycopy(discretionary, 0, checkMatch, 0, netAssets.length);
            recalculateSpending();
            recalculateDiscretionary();
            transitions = new LinkedList<Integer>();
            smoothDiscretionary(transitions, true);
            if (Arrays.equals(checkMatch, discretionary)) {
                break;
            }
        }

        // Add the financial goals and discretionary spending together 
        // to get the final spending budget for all years.
        finalSpending = new int[N];
        for (int i = 0; i < N; i++) {
            finalSpending[i] = discretionary[i] + expenses[i];
        }
        return finalSpending;
    }

    /**  
      * A method that allocates discretionary spending for certain sections of the discretionary array. 
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
      * A method that recalculates discretionary spending if a certain year's discretionary spending is more than the year's income.
      */
    private void recalculateDiscretionary() {
        int lastExpense = 0;
        int leftOver = finalAmount;
        for (int i = 0; i < N; i++) {
            if (this.totalDiscretionarySoFar(i) > netAssets[i]) {
                divideToDiscretionary(netAssets[i]-this.totalDiscretionarySoFar(lastExpense - 1), lastExpense, i);
                lastExpense = i + 1;
                leftOver = finalAmount - netAssets[i];
                divideToDiscretionary(leftOver, lastExpense, N - 1);
            }
        }
    }

    /**  
      * A method that calculates how much money is used for discretionary spending up to a certain end year.
      *
      * @param END: The last year to include in the total discretionary spending.
      * @return The total discretionary spending
      */
    private int totalDiscretionarySoFar(int end) {
        int result = 0;
        if (end == -1) {
            return 0;
        } else {
            for (int i = 0; i <= end; i++) {
                result += discretionary[i];
            }
        }
        return result;
    }

    /**  
      * A method that calculates how much money the user has saved (income - expenses - discretionary) up to a certain end year.
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
      * A method that recalculates the discretionary spending to make sure the 
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

    /**  
      * A method that searches through the discretionary array and
      * adds the indices where discretionary spending changes. 
      * @param TRANSITIONS: A LinkedList to hold the indices for 
      *                     where discretionary spending changes. 
      */
    private void findTransitions(LinkedList<Integer> transitions) {
        transitions.add(0);
        for (int i = 1; i < N; i++) {
            if (discretionary[i] != discretionary[i - 1]) {
                transitions.add(i);
            }
        }
    }

    /**  
      * A method that smooths out discretionary spending based off of the observation that 
      * if for a couple of years the user has a lot of discretionary spending and then the following
      * couple of years have less discretionary spending, then the discretionary spending from the 
      * earlier years can be allocated to the future years to maximize happiness.
      * @param: TRANSITIONS: A LinkedList to hold the indices for 
      *                     where discretionary spending changes. 
      * @param: FIRSTCALL: A boolean that indicates whether this call is the first call or another call
      *                    from within this function. 
      */
    private void smoothDiscretionary(LinkedList<Integer> transitions, boolean firstCall) {
        if (transitions.size() == 0 && firstCall == false) {
            return;
        }
        if (transitions.size() == 0 && firstCall == true) {
            this.findTransitions(transitions);
        }
        int firstTransition;
        int secondTransition;
        int thirdTransition;
        if (transitions.size() == 1) {
            return;
        }
        if (transitions.size() == 2) {
            firstTransition = transitions.get(0);
            secondTransition = transitions.get(1);
            if (discretionary[firstTransition] > discretionary[secondTransition]) {
                divideToDiscretionary(this.totalDiscretionarySoFar(N - 1), firstTransition, N - 1);
            }
        } else {
            while (transitions.size() > 2) {
                firstTransition = transitions.get(0);
                secondTransition = transitions.get(1);
                thirdTransition = transitions.get(2);
                if (discretionary[firstTransition] > discretionary[secondTransition]) {
                    int amount = this.totalDiscretionarySoFar(thirdTransition) - this.totalDiscretionarySoFar(firstTransition) - discretionary[thirdTransition] + discretionary[firstTransition];
                    divideToDiscretionary(amount, firstTransition, thirdTransition - 1);
                }
                transitions.pop();
            }
            smoothDiscretionary(transitions, false);
            return;
        }
    }
}



















