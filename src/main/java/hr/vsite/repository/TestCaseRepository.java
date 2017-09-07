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
			+ " and t.status LIKE 'Not run') or (t.isAssigned = true and t.owner LIKE :name)")
	List<TestCase> ovredueTests(@Param("name") String name);

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
			"trunc(SYSDATE-9) AND trunc(SYSDATE-1) AND t.status LIKE :caseStatus")
	Long countCases(@Param("caseStatus") String Status);

	@Override
	@Query("select COUNT(t.id) from TestCase t where TRUNC(t.createdDate) BETWEEN " +
			"trunc(SYSDATE-9) AND trunc(SYSDATE-1)")
	Long countTotal();

	@Override
	@Query("SELECT COUNT(t.id) FROM TestCase t " +
			"WHERE TRUNC(t.executedDate) < TRUNC(SYSDATE) AND t.status LIKE 'Not run' AND t.owner LIKE :user")
	Long countOvredueTests(@Param("user") String user);

	@Override
	@Query("select t from TestCase t")
	List<TestCase> findAllTestCases();
	
}
