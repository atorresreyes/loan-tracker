package loan.tracker.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
		Long locationId = locationData.getLocationId();
		String placeName = locationData.getPlaceName();
		String contactName = locationData.getContactName();
		Location location = findOrCreateLocation(locationId, placeName, contactName);
		Set<Loan> loans = location.getLoans();
		
		copyLocationFields(location, locationData);
		
		// set loans
		for(Loan loan : loans) {
			location.getLoans().add(loan);
		}
		
		Location dbLocation = locationDao.save(location);
		
		return new LocationData(dbLocation);
	}

	private void copyLocationFields(Location location, LocationData locationData) {
		location.setPlaceName(locationData.getPlaceName());
		location.setContactName(locationData.getContactName());
		location.setPhone(locationData.getPhone());
		location.setEmail(locationData.getEmail());
		location.setStreetAddress(locationData.getStreetAddress());
		location.setCity(locationData.getCity());
		location.setState(locationData.getState());
		location.setZip(locationData.getZip());
		location.setMailingStreet(locationData.getMailingStreet());
		location.setMailingCity(locationData.getMailingCity());
		location.setMailingState(locationData.getMailingState());
		location.setMailingZip(locationData.getMailingZip());
	}	

	private Location findOrCreateLocation(Long locationId, String placeName, String contactName) {
		if(Objects.isNull(placeName)) {
			throw new IllegalStateException("Place name cannot be null.");
		}
		if(Objects.isNull(contactName)) {
			throw new IllegalStateException("Contact name cannot be null.");
		}
		Location location;
		if(Objects.isNull(locationId)) {
			location = new Location();
		} else { 
			location = findLocationById(locationId);
		}
		
		return location;
	}

	@Transactional(readOnly = false)
	public LoanData saveLoan(Long locationId, LoanData loanData) {
		Location location = findLocationById(locationId);
		Long loanId = loanData.getLoanId();
		Loan loan = findOrCreateLoan(locationId, loanId);
		Set<AnObject> objects = loan.getAnObject();
		
		
		copyLoanFields(loan, loanData);
		
		//set location in loan
		loan.setLocation(location);
		
		//add loan to location's list of loans
		location.getLoans().add(loan);
		
		//set objects
			for(AnObject object : objects) {
				loan.getAnObject().add(object);
			}
		
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
		String catNum = objectData.getCatalogNumber();
		AnObject object = findOrCreateObject(objectId, catNum);
		
		copyObjectFields(object, objectData);
		
		AnObject dbObject = objectDao.save(object);
		
		return new ObjectsData(dbObject);
	}

	private void copyObjectFields(AnObject object, ObjectsData objectData) {
		object.setCatalogNumber(objectData.getCatalogNumber());
		object.setCommonName(objectData.getCommonName());
		object.setMedium(objectData.getMedium());
	}

	private AnObject findOrCreateObject(Long objectId, String catNum) {
		if(Objects.isNull(catNum)) {
			throw new IllegalStateException("Catalog number cannot be null.");
		}
		AnObject object;
		if(Objects.isNull(objectId)) {
			//preventing duplicate catalog numbers
			Optional<AnObject> opObject = objectDao.findByCatalogNumber(catNum);
			if (opObject.isPresent()) {
				throw new DuplicateKeyException("Object with ID=" + catNum + " already exists.");
			}
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

	@Transactional(readOnly = true)
	public LoanData retrieveLoanById(Long loanId) {
		Loan loan = findLoanById(loanId);
		return new LoanData(loan);
	}
	
	private Loan findLoanById(Long loanId) {
		return loanDao.findById(loanId)
				.orElseThrow(() -> new NoSuchElementException(
						"Loan with ID=" + loanId + " was not found."));
	}

	@Transactional(readOnly = false)
	public LoanData addObjectToLoan(Long loanId, Long objectId) {
		AnObject anObject = findObjectById(objectId);
		Loan loan = findLoanById(loanId);
		
		loan.getAnObject().add(anObject);
		
		Loan dbLoan = loanDao.save(loan);
		
		return new LoanData(dbLoan);
	}

	@Transactional(readOnly = true)
	public List<ObjectsData> retrieveAllObjectsInCatNumOrder() {
		// @formatter: off
		return objectDao.findAll()
				.stream()
				.sorted((object1, object2) -> 
					object1.getCatalogNumber().compareTo(object2.getCatalogNumber()))
				.map(object -> new ObjectsData(object))
				.toList();
		// @formatter: on
	}
	
	public List<LocationData> retrieveAllLocationsAlphabeticallyByPlaceName() {
		// @formatter:off 
		return locationDao.findAll()
				.stream()
				.sorted((location1, location2) ->
						location1.getPlaceName().compareTo(location2.getPlaceName()))
				.map(location -> new LocationData(location))
				.toList();
				// @formatter: on
	}
	
	public LocationData retrieveLocationById(Long locationId) {
		Location location = findLocationById(locationId);
		return new LocationData(location);
	}

	@Transactional(readOnly = false)
	public void deleteObject(Long objectId) {
		AnObject anObject = findObjectById(objectId);
		objectDao.delete(anObject);
	}

	public void deleteLocation(Long locationId) {
		Location location = findLocationById(locationId);
		locationDao.delete(location);
	}

	@Transactional(readOnly = false)
	public void deleteLoan(Long loanId) {
		Loan loan = findLoanById(loanId);
		loanDao.delete(loan);
	}
}
