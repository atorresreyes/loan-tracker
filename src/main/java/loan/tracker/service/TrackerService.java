package loan.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import loan.tracker.controller.model.LocationData;
import loan.tracker.dao.LocationDao;
import loan.tracker.entity.Location;

@Service
public class TrackerService {
	@Autowired
	private LocationDao locationDao;
	
	@Transactional(readOnly = false)
	public LocationData saveLocation(LocationData locationData) {
		//FUTURE UPDATE: Handle error if place name or contact name are null
		Location location = locationData.toLocation();
		Location dbLocation = locationDao.save(location);
		
		return new LocationData(dbLocation);
	}

}
