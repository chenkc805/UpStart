import static org.junit.Assert.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Arrays;

import org.junit.Test;

/** 
  * Class that demonstrates basic Budgeting functionality.
  *  @author Kevin Chen
  */

public class BudgetTest {

    private int[] workingIncome0 = new int[] {10000, 600, 100, 100, 100, 1000};
    private int[] workingExpenses0 = new int[] {0, 100, 100, 0, 0, 10000};    
    private Budget workingBudget0 = new Budget(workingIncome0, workingExpenses0);

    private int[] workingIncome1 = new int[] {100, 100, 100, 300, 300, 300, 300, 400, 500, 600};
    private int[] workingExpenses1 = new int[] {0, 0, 200, 0, 200, 0, 500, 300, 600, 10};    
    private Budget workingBudget1 = new Budget(workingIncome1, workingExpenses1);

    private int[] workingIncome2 = new int[] {100, 100, 100, 300, 300, 300, 300};
    private int[] workingExpenses2 = new int[] {0, 0, 0, 100, 0, 0, 500};    
    private Budget workingBudget2 = new Budget(workingIncome2, workingExpenses2);

    private int[] workingIncome3 = new int[] {100};
    private int[] workingExpenses3 = new int[] {25};    
    private Budget workingBudget3 = new Budget(workingIncome3, workingExpenses3);

    private int[] justIncome = new int[] {100, 100, 100, 300, 300, 300, 300};
    private int[] noExpenses = new int[] {0, 0, 0, 0, 0, 0, 0};    
    private Budget noExpensesBudget = new Budget(justIncome, noExpenses);

    private int[] invalidIncome1 = new int[] {100, 100, 100, 300, 300, 300, 300};
    private int[] invalidExpenses1 = new int[] {0, 0, 0, 0, 0, 0, 1600};    
    private Budget invalidBudget1 = new Budget(invalidIncome1, invalidExpenses1);

    private int[] invalidIncome2 = new int[] {100, 100, 100, 300, 300, 300, 300};
    private int[] invalidExpenses2 = new int[] {0, 0, 0, 0, 0, 0};    
    private Budget differingLengths = new Budget(invalidIncome2, invalidExpenses2);

    private int[] noLengthIncome = new int[] {};
    private int[] noLengthExpenses = new int[] {};    
    private Budget noLength = new Budget(noLengthIncome, noLengthExpenses);

    @Test
    public void testWorkingBudget0() {
        System.out.println("Working Budget 0: " + Arrays.toString(workingBudget0.budgetingAlgorithm()));  
        System.out.println("-------------------------------"); 
    }

    @Test
    public void testWorkingBudget1() {
        System.out.println("Working Budget 1: " + Arrays.toString(workingBudget1.budgetingAlgorithm()));  
        System.out.println("-------------------------------"); 
    }

    @Test
    public void testWorkingBudget2() {
        System.out.println("Working Budget 2: " + Arrays.toString(workingBudget2.budgetingAlgorithm()));   
        System.out.println("-------------------------------");
    }

    @Test
    public void testWorkingBudget3() {
        System.out.println("Working Budget 3: " + Arrays.toString(workingBudget3.budgetingAlgorithm()));   
        System.out.println("-------------------------------");
    }

    @Test
    public void testNoExpenses() {
        System.out.println("No Expenses Budget : " + Arrays.toString(noExpensesBudget.budgetingAlgorithm()));   
        System.out.println("-------------------------------");
    }

    @Test
    public void testInvalidBudget1() {
        System.out.println("Invalid Budget 1: " + Arrays.toString(invalidBudget1.budgetingAlgorithm()));   
        System.out.println("-------------------------------");
    }

    @Test
    public void testDifferingLengths() {
        System.out.println("Differing Lengths: " + Arrays.toString(differingLengths.budgetingAlgorithm()));   
        System.out.println("-------------------------------");
    }


    @Test
    public void noLength() {
        System.out.println("No Length: " + Arrays.toString(noLength.budgetingAlgorithm()));   
        System.out.println("-------------------------------");
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BudgetTest.class);
    }                 
} 
