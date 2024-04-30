package loan.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import loan.tracker.controller.model.LocationData;
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
}
