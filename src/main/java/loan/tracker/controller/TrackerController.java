package loan.tracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/{locationId}/loan")
	@ResponseStatus(code = HttpStatus.CREATED)
	public LoanData createLoan(@PathVariable Long locationId, @RequestBody LoanData loanData) {
		log.info("Creating loan for location {}", locationId);
		return trackerService.saveLoan(locationId, loanData);
	}
	
	@PostMapping("/object")
	@ResponseStatus(code = HttpStatus.CREATED)
	//FUTURE UPDATE: Handle error if cat number is null or duplicate
	public ObjectsData createObject(@RequestBody ObjectsData objectData) {
		log.info("Creating object {}", objectData);
		return trackerService.saveObject(objectData);
	}

	
	@GetMapping("/loan")
	public List<LoanData> retrieveAllLoans() {
		log.info("Retrieving all loans");
		return trackerService.retrieveAllLoans();
	}

	
}
