package loan.tracker.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import loan.tracker.controller.model.LoanData;
import loan.tracker.controller.model.LocationData;
import loan.tracker.controller.model.ObjectsData;
import loan.tracker.dao.LoanDao;
import loan.tracker.dao.LocationDao;
import loan.tracker.dao.ObjectDao;
import loan.tracker.entity.AnObject;
import loan.tracker.entity.Loan;
import loan.tracker.entity.Location;

@Service
public class TrackerService {
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private LoanDao loanDao;
	
	@Autowired
	private ObjectDao objectDao;
	
	@Transactional(readOnly = false)
	public LocationData saveLocation(LocationData locationData) {
		//FUTURE UPDATE: Handle error if place name or contact name are null
		Location location = locationData.toLocation();
		Location dbLocation = locationDao.save(location);
		
		return new LocationData(dbLocation);
	}
	

	@Transactional(readOnly = false)
	public LoanData saveLoan(Long locationId, LoanData loanData) {
		Location location = findLocationById(locationId);
		Long loanId = loanData.getLoanId();
		Loan loan = findOrCreateLoan(locationId, loanId);
		
		copyLoanFields(loan, loanData);
		
		//set location in loan
		loan.setLocation(location);
		
		//add loan to location's list of loans
		location.getLoans().add(loan);
		
		Loan dbLoan = loanDao.save(loan);
		
		return new LoanData(dbLoan);
	}
	
	private void copyLoanFields(Loan loan, LoanData loanData) {
		loan.setStatus(loanData.getStatus());
		loan.setStartDate(loanData.getStartDate());
		loan.setEndDate(loanData.getEndDate());
		loan.setPurpose(loanData.getPurpose());
	}

	private Loan findOrCreateLoan(Long locationId, Long loanId) {
		Loan loan;
		if(Objects.isNull(loanId)) {
			loan = new Loan();
		} else {
			loan = findLoanById(locationId, loanId);
		}
		return loan;
	}

	private Loan findLoanById(Long locationId, Long loanId) {
		return loanDao.findById(loanId)
				.orElseThrow(() -> new NoSuchElementException(
						"Loan with ID=" + loanId + " does not exist."));
	}

	private Location findLocationById(Long locationId) {
		return locationDao.findById(locationId)
				.orElseThrow(() -> new NoSuchElementException(
						"Location with ID=" + locationId + " does not exist"));
	}
	@Transactional(readOnly = true)
	public List<LoanData> retrieveAllLoans() {
		List<Loan> loanEntities = loanDao.findAll();
		List<LoanData> allLoans = new LinkedList<>();

		for(Loan loan : loanEntities) {
			LoanData loanData = new LoanData(loan);
			allLoans.add(loanData);
		}
		
		return allLoans;
		
	}

	@Transactional(readOnly = false)
	public ObjectsData saveObject(ObjectsData objectData) {
		Long objectId = objectData.getObjectId();
		AnObject object = findOrCreateObject(objectId);
		
		copyObjectFields(object, objectData);
		
		AnObject dbObject = objectDao.save(object);
		
		return new ObjectsData(dbObject);
	}

	private void copyObjectFields(AnObject object, ObjectsData objectData) {
		object.setCatalogNumber(objectData.getCatalogNumber());
		object.setCommonName(objectData.getCommonName());
		object.setMedium(objectData.getMedium());
	}


	private AnObject findOrCreateObject(Long objectId) {
		AnObject object;
		if(Objects.isNull(objectId)) {
			object = new AnObject();
		} else {
			object = findObjectById(objectId);
		}
		return object;
	}


	private AnObject findObjectById(Long objectId) {
		return objectDao.findById(objectId)
				.orElseThrow(() -> new NoSuchElementException(
						"Object with ID=" + objectId + " does not exist."));
	}
	


}
