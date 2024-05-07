package loan.tracker.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import loan.tracker.controller.model.LoanData;
import loan.tracker.controller.model.LocationData;
import loan.tracker.controller.model.ObjectsData;
import loan.tracker.entity.Loan;
import loan.tracker.entity.Location;
import loan.tracker.entity.AnObject;

public class TrackerControllerTestSupport {

	private static final String LOAN_TABLE = "loan";

	private static final String LOCATION_TABLE = "location";

	//formatter:off
	private LocationData insertAddress1 = new LocationData(
			1L, 
			"Milwaukee Art Museum",
			"Jane O'Meara",
			"(414) 224-3200",
			"jomeara@mam.org",
			"700 N. Art Museum Drive",
			"Milwaukee",
			"Wisconsin",
			"53202",
			"P.O. Box 2235",
			"Milwaukee",
			"Wisconsin",
			"53202"		
	);
	
	private LocationData insertAddress2 = new LocationData(
			2L, 
			"Madison Museum of Contemporary Art",
			"Marilyn L.M. Sohi",
			"(608) 257-0158",
			"mlmsohi@mmoca.org",
			"227 State Street",
			"Madison",
			"Wisconsin",
			"53703",
			"P.O. Box 3735",
			"Madison",
			"Wisconsin",
			"53703"	
	);
	
	private LoanData insertLoanInfo1 = new LoanData(
			1L,
			"Current", 
			"Oct. 15, 2023",
			"Oct. 15, 2025",
			"For exhibit"
	);
	
	private LoanData insertLoanInfo2 = new LoanData(
			2L,
			"Returned", 
			"April 12, 2023",
			"April 12, 2024",
			"For research"
	);
	
	private ObjectsData insertObjectInfo1 = new ObjectsData(
			44L,
			"0000-0001-0001",
			"Test Object",
			"Digital"
			);
	
	private ObjectsData insertObjectInfo2 = new ObjectsData(
			45L,
			"0000-0001-0002",
			"Object Test",
			"Missing"
			);
	//formatter:on
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TrackerController trackerController;

	
	
	protected LocationData buildInsertLocation(int which) {
		return which == 1 ? insertAddress1 : insertAddress2;
	}
	
	protected int rowsInLocationTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, LOCATION_TABLE);
	}

	protected LocationData insertLocation(LocationData locationData) {
		Location location = locationData.toLocation();
		LocationData clone = new LocationData(location);
		
		clone.setLocationId(null);
		
		return trackerController.createLocation(clone);
	}
	
	protected int rowsInLoanTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, LOAN_TABLE);
	}

	protected LoanData insertLoan(LoanData loanData) {
		Loan loan = loanData.toLoan();
		LoanData clone = new LoanData(loan);
		
		//inserting into table an example location 
		//bc location cannot be null for loan
		LocationData tempLocation = trackerController.createLocation(insertAddress1);
		tempLocation.setLocationId(1L);
		
		clone.setLoanId(null);
		
		return trackerController.createLoan(1L, clone);
	}
	
	
	protected List<LoanData> insertTwoLoans() {
		Loan loan1 = insertLoanInfo1.toLoan();
		LoanData clone1 = new LoanData(loan1);
		
		Loan loan2 = insertLoanInfo2.toLoan();
		LoanData clone2 = new LoanData(loan2);;
		
		LocationData tempLocation1 = trackerController.createLocation(insertAddress1);
		tempLocation1.setLocationId(1L);
		LocationData tempLocation2 = trackerController.createLocation(insertAddress2);
		tempLocation2.setLocationId(2L);
		
		clone1.setLoanId(null);
		clone2.setLoanId(null);
		
		LoanData fullLoan1 = trackerController.createLoan(1L, clone1);
		LoanData fullLoan2 = trackerController.createLoan(2L, clone2);
		
		List<LoanData> twoLoans = new LinkedList<>();
		twoLoans.add(fullLoan1);
		twoLoans.add(fullLoan2);
		
		return twoLoans;
		
	}
	
	protected LoanData buildInsertLoan(int which) {
		return which == 1 ? insertLoanInfo1 : insertLoanInfo2;
	}
		
	protected List<LoanData> retrieveAllLoans() {
		return trackerController.retrieveAllLoans();
	}
	

	protected ObjectsData insertObject(ObjectsData objectsData) {
		AnObject object = objectsData.toObject();
		ObjectsData clone = new ObjectsData(object);
		
		clone.setObjectId(null);
		
		return trackerController.createObject(clone);
	}

	protected ObjectsData buildInsertObject(int which) {
		return which == 1 ? insertObjectInfo1 : insertObjectInfo2;
	}
	
	protected LoanData updateLoan(long locationId, long loanId) {
		return trackerController.updateLoan(locationId, loanId, insertLoanInfo2);
	}
	
	protected Map<String, String> deleteLoan(Long loanId) {
		return trackerController.deleteLoan(loanId);
	}

}
