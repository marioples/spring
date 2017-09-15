package hr.vsite.repository;

import java.util.List;

import hr.vsite.model.TestCase;

public interface CustomTestCaseRepository {

	List<TestCase> findDailyTests();
	
	List<TestCase> findAllTestCases();
	
	List<TestCase> ovredueOrAssignedTests(String name);
	
	List<TestCase> findAllCasesWithParametar(String SuitName);
	
	Long countTestcases(String date, String status);
	
	Long countCases(String Status, Integer start, Integer end);
	
	Long countOvredueTests(String user);
	
	Long countTotalStartEndDate(Integer start, Integer end);
	
	List<TestCase> findByCaseNameAuthorOwner(String name, String author, String owner);
}
