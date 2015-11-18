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

    private int[] workingIncome0 = new int[] {600, 100, 100, 100, 0};
    private int[] workingExpenses0 = new int[] {0, 100, 0, 0, 300};    
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

    private int[] negativeIncome = new int[] {100, 100, -1, 300, 300, 300, 300, 400, 500, 600};
    private int[] negativeExpenses = new int[] {0, 0, 200, 0, 200, 0, 500, 300, 600, 10};    
    private Budget negativeArray = new Budget(negativeIncome, negativeExpenses);

    @Test
    public void testWorkingBudget0() {
        String result = Arrays.toString(workingBudget0.budgetingAlgorithm());  
        assertEquals("[100, 200, 100, 100, 400]", result); 
    }

    @Test
    public void testWorkingBudget1() {
        String result = Arrays.toString(workingBudget1.budgetingAlgorithm());  
        assertEquals("[33, 33, 233, 83, 283, 83, 583, 383, 683, 600]", result); 
    }

    @Test
    public void testWorkingBudget2() {
        String result = Arrays.toString(workingBudget2.budgetingAlgorithm());  
        assertEquals("[100, 100, 100, 250, 150, 150, 650]", result); 
    }

    @Test
    public void testWorkingBudget3() {
        String result = Arrays.toString(workingBudget3.budgetingAlgorithm());  
        assertEquals("[100]", result); 
    }

    @Test
    public void testNoExpenses() {
        String result = Arrays.toString(noExpensesBudget.budgetingAlgorithm());  
        assertEquals("[100, 100, 100, 300, 300, 300, 300]", result); 
    }

    @Test
    public void testInvalidBudget1() {
        System.out.println("Unable to make financial goal in year 7.");
        String result = Arrays.toString(invalidBudget1.budgetingAlgorithm());  
        assertEquals("null", result); 
        System.out.println("-----------------------");
    }

    @Test
    public void testDifferingLengths() {
        System.out.println("Length of years in INCOME and EXPENSES not equal.");
        String result = Arrays.toString(differingLengths.budgetingAlgorithm());  
        assertEquals("null", result);
        System.out.println("-----------------------");
    }

    @Test
    public void noLength() {  
        System.out.println("No values given to INCOME.");
        String result = Arrays.toString(noLength.budgetingAlgorithm());  
        assertEquals("null", result); 
        System.out.println("-----------------------");
    }

    @Test
    public void negativeArray() {  
        System.out.println("Income or financial goal projections cannot contain negative values.");
        String result = Arrays.toString(negativeArray.budgetingAlgorithm());  
        assertEquals("null", result); 
        System.out.println("-----------------------");
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BudgetTest.class);
    }                 
} 
