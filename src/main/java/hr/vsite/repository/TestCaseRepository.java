package hr.vsite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hr.vsite.model.TestCase;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long>, CustomTestCaseRepository{

	@Query("select u from TestCase u where u.id = :id")
	TestCase findbyId(@Param("id") Integer id);

	@Override
	@Query("select t from TestCase t where trunc(t.createdDate) = trunc(SYSDATE)")
	List<TestCase> findDailyTests();

	@Override
	@Query("select t from TestCase t where (trunc(t.executedDate) < trunc(SYSDATE)"
			+ " and t.status LIKE 'Not run' and t.owner LIKE :name) or (t.isAssigned LIKE true and t.owner LIKE :name)")
	List<TestCase> ovredueOrAssignedTests(@Param("name") String name);

	@Override
	@Query("select t from TestCase t inner join t.suit s"
			+ " WHERE s.testSuitName LIKE :custSuit")
	List<TestCase> findAllCasesWithParametar(@Param("custSuit") String SuitName);

	@Override
	@Query("select count(t.id) from TestCase t " +
			"where SUBSTR(t.createdDate,1,8) LIKE :cDate AND t.status LIKE :caseStatus")
	Long countTestcases(@Param("cDate") String date, @Param("caseStatus") String status);

	@Override
	@Query("select count(t.id) from TestCase t where trunc(t.createdDate) BETWEEN " +
			"trunc(SYSDATE-:start) AND trunc(SYSDATE-:end) AND t.status LIKE :caseStatus")
	Long countCases(@Param("caseStatus") String Status, @Param("start") Integer start, @Param("end") Integer end);

	@Override
	@Query("SELECT COUNT(t.id) FROM TestCase t " +
			"WHERE (trunc(t.executedDate) < trunc(SYSDATE)"
			+ " and t.status LIKE 'Not run' and t.owner LIKE :user) or (t.isAssigned LIKE true and t.owner LIKE :user)")
	Long countOvredueTests(@Param("user") String user);

	@Override
	@Query("select t from TestCase t")
	List<TestCase> findAllTestCases();

	@Query("SELECT COUNT(t.id) FROM TestCase t WHERE TRUNC(t.createdDate) BETWEEN " + 
			"trunc(SYSDATE-:start) AND trunc(SYSDATE-:end)")
	Long countTotalStartEndDate(@Param("start") Integer start, @Param("end") Integer end);

	@Override
	@Query("select t from TestCase t where t.caseName LIKE :name or t.author LIKE :author or t.owner LIKE :owner")
	List<TestCase> findByCaseNameAuthorOwner(@Param("name") String name, @Param("author") String author, @Param("owner") String owner);	
}
