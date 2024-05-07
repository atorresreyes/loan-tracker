package loan.tracker.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import loan.tracker.LoanTrackerApplication;
import loan.tracker.controller.model.LoanData;
import loan.tracker.controller.model.LocationData;
import loan.tracker.controller.model.ObjectsData;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = LoanTrackerApplication.class)
@ActiveProfiles("test") 
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@SqlConfig(encoding = "utf-8")
class TrackerControllerTest extends TrackerControllerTestSupport {

	@Test
	void testInsertLocation() {
		//given: a location request
		LocationData request = buildInsertLocation(1);
		LocationData expected = buildInsertLocation(1);
		
		//when: the location is added to the location table
		LocationData actual = insertLocation(request);
		
		//then: the location returned is equal to expected
		assertThat(actual).isEqualTo(expected);
		
		//and: there is one row in the location table
		assertThat(rowsInLocationTable()).isOne();
	}
	
	@Test
	void testInsertLoan() {
		//given: a loan request with a location id
		LoanData request = buildInsertLoan(1);
		LoanData expected = buildInsertLoan(1);
		
		//when: the loan is added to the loan table
		LoanData actual = insertLoan(request);
		
		//then: the loan returned is equal to expected
		assertThat(actual).isEqualTo(expected);
		
		//and: there is one row in the loan table
		assertThat(rowsInLoanTable()).isOne();
	}
	
	@Test
	void testInsertObject() {
		//given: an object request 
		ObjectsData request = buildInsertObject(1);
		ObjectsData expected = buildInsertObject(1);
		
		//when: the object is added to the objects table
		ObjectsData actual = insertObject(request);

		//then: the object returned is equal to the expected
		assertThat(actual).isEqualTo(expected);
		
	}

	@Test 
	void testRetrieveAllLoans() {
		//given: two loans in loan table
		List<LoanData> expected = insertTwoLoans();
		
		//when: all loans are retrieved
		List<LoanData> actual = retrieveAllLoans();
		
		//then: the retrieved loans are the same as expected
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testUpdateLoanById() {
		//given: a loan in the table,
				//two locations in the table,
				//and an update loan request with a location id and loan id
		LoanData request = buildInsertLoan(1);
		insertLoan(request);
		LocationData location1 = buildInsertLocation(1);
		LocationData location2 = buildInsertLocation(2);
		insertLocation(location1);
		insertLocation(location2);
		
		LoanData expected = buildInsertLoan(2);
		
		//when: loan is updated 
		LoanData actual = updateLoan(2L, 1L);
		
		//then: the retrieved loan is the same as expected
		assertThat(actual).isEqualTo(expected);
		
		//and: there is one row in the loan table
		assertThat(rowsInLoanTable()).isOne();
	}

	@Test 
	void testDeleteLoanById() {
		//given: a loan in the table
				//and a delete loan request with loan id
		insertLoan(buildInsertLoan(1));
		Map<String, String> expected = new HashMap<>(); 
		expected.put("message", "Loan with ID=1 was deleted successfully.");
		
		//when: a loan is deleted
		Map<String, String> actual = deleteLoan(1L);
		
		//then: the deleted message is the same as expected
		assertThat(actual).isEqualTo(expected);
		
	}





}
