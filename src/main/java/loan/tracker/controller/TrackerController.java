package loan.tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import loan.tracker.controller.model.LoanData;
import loan.tracker.controller.model.LocationData;
import loan.tracker.controller.model.ObjectsData;
import loan.tracker.service.TrackerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/loan_tracker")
@Slf4j
public class TrackerController {
	@Autowired
	private TrackerService trackerService;
	
	@PostMapping("/location")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LocationData createLocation(@RequestBody LocationData locationData) {
		log.info("Creating location {}", locationData);
		return trackerService.saveLocation(locationData);
	}
	
	@PostMapping("/location/{locationId}/loan")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LoanData createLoan(@PathVariable Long locationId, @RequestBody LoanData loanData) {
		log.info("Creating loan for location {}", locationId);
		return trackerService.saveLoan(locationId, loanData);
	}
	
	@PostMapping("/object")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ObjectsData createObject(@RequestBody ObjectsData objectData) {
		log.info("Creating object {}", objectData);
		return trackerService.saveObject(objectData);
	}

	@GetMapping("/loan")
	public List<LoanData> retrieveAllLoans() {
		log.info("Retrieving all loans");
		return trackerService.retrieveAllLoans();
	}

	@GetMapping("/loan/{loanId}")
	public LoanData retrieveLoanById(@PathVariable Long loanId) {
		log.info("Retrieving loan with Id={}", loanId);
		return trackerService.retrieveLoanById(loanId);
	}
	
	@GetMapping("/object")
	public List<ObjectsData> retrieveAllObjectsInCatNumOrder() {
		log.info("Retrieving all objects in numerical order by catalog number.");
		return trackerService.retrieveAllObjectsInCatNumOrder();
	}
	
	@GetMapping("/location")
	public List<LocationData> retrieveAllLocationsAlphabeticallyByPlaceName() {
		log.info("Retrieving all locations in alphabetical order by place name.");
		return trackerService.retrieveAllLocationsAlphabeticallyByPlaceName();
	}
	
	@GetMapping("/location/{locationId}")
	public LocationData retrieveLocationById(@PathVariable Long locationId) {
		log.info("Retrieving location with ID={}", locationId);
		return trackerService.retrieveLocationById(locationId);
		}
	
	@PutMapping("/loan/{loanId}/object/{objectId}")
	public LoanData addObjectToLoan(@PathVariable Long loanId, @PathVariable Long objectId) {
		log.info("Adding object with ID={} to loan with ID={}", objectId, loanId);
		return trackerService.addObjectToLoan(loanId, objectId); 
	}
	
	@PutMapping("/location/{locationId}/loan/{loanId}")
	public LoanData updateLoan(@PathVariable Long locationId, @PathVariable Long loanId, @RequestBody LoanData loanData) {
		log.info("Updating loan with ID={}", loanId);
		loanData.setLoanId(loanId);
		return trackerService.saveLoan(locationId, loanData);
	}
	
	@PutMapping("/object/{objectId}")
	public ObjectsData updateObject(@PathVariable Long objectId, @RequestBody ObjectsData objectsData) {
		log.info("Updating object with ID={}", objectId);
		objectsData.setObjectId(objectId);
		return trackerService.saveObject(objectsData);
	}
	
	@PutMapping("/location/{locationId}")
	public LocationData updateLocation(@PathVariable Long locationId, @RequestBody LocationData locationData) {
		log.info("Updating location with ID={}", locationId);
		locationData.setLocationId(locationId);
		return trackerService.saveLocation(locationData);
	}
	
	//prevent all loans from being deleted
	@DeleteMapping("/loan")
	public void deleteAllLoans() {
		log.info("Attempting to delete all loans.");
		throw new UnsupportedOperationException("Deleting all loans is not allowed.");
	}

	@DeleteMapping("/loan/{loanId}")
	public Map<String, String> deleteLoan(@PathVariable Long loanId) {
		log.info("Deleting loan with ID={}", loanId);
		trackerService.deleteLoan(loanId);
		return Map.of("message", "Loan with ID=" + loanId + " was deleted successfully.");
	}
	
	@DeleteMapping("/object")
	public void deleteAllObjects() {
		log.info("Attempting to delete all objects.");
		throw new UnsupportedOperationException("Deleting all objects is not allowed.");
	}
	
	@DeleteMapping("/object/{objectId}")
	public Map<String, String> deleteObject(@PathVariable Long objectId) {
		log.info("Deleting object with ID={}", objectId);
		trackerService.deleteObject(objectId);
		return Map.of("message", "Object with ID=" + objectId + " was deleted successfully.");
	}
	
	@DeleteMapping("/location")
	public void deleteAllLocations() {
		log.info("Attempting to delete all locations.");
		throw new UnsupportedOperationException("Deleting all locations is not allowed.");
	}
	
	@DeleteMapping("/location/{locationId}")
	public Map<String, String> deleteLocation(@PathVariable Long locationId) {
		log.info("Deleting location with ID={}", locationId);
		trackerService.deleteLocation(locationId);
		return Map.of("message", "Location with ID=" + locationId + " was deleted successfully.");
	}

	
}
