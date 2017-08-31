package hr.vsite.repository;

import java.util.List;

import hr.vsite.model.TestCase;

public interface CustomTestCaseRepository {

	List<TestCase> findDailyTests();
	
	List<TestCase> findAllTestCases();
	
	List<TestCase> ovredueTests(String name);
	
	List<TestCase> findAllCasesWithParametar(String SuitName);
	
	List<TestCase> testHasBeenAssigned(String username);
	
	Long countTestcases(String date, String status);
	
	Long countCases(String Status);
	
	Long countTotal();
	
	Long countOvredueTests(String user);
}
