package loan.tracker.controller.model;

import java.util.HashSet;
import java.util.Set;

import loan.tracker.entity.Loan;
import loan.tracker.entity.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationData {
	private Long locationId;
	private String placeName;
	private String contactName;
	private String phone;
	private String email;
	private String streetAddress;
	private String city;
	private String state;
	private String zip;
	private String mailingStreet;
	private String mailingCity;
	private String mailingState;
	private String mailingZip;
	private Set<LoanData> loans = new HashSet<>();
	
	public LocationData(Location location) {
		this.locationId = location.getLocationId();
		this.placeName = location.getPlaceName();
		this.contactName = location.getContactName();
		this.phone = location.getPhone();
		this.email = location.getEmail();
		this.streetAddress = location.getStreetAddress();
		this.city = location.getCity();
		this.state = location.getState();
		this.zip = location.getZip();
		this.mailingStreet = location.getMailingStreet();
		this.mailingCity = location.getMailingCity();
		this.mailingState = location.getMailingState();
		this.mailingZip = location.getMailingZip();
		
		for (Loan loan : location.getLoans()) {
			this.loans.add(new LoanData(loan));
		}
	}
	
	public LocationData(Long locationId, String placeName, String contactName, String phone, String email, String streetAddress, String city, String state, String zip, String mailingStreet, String mailingCity, String mailingState, String mailingZip) {
		this.locationId = locationId;
		this.placeName = placeName;
		this.contactName = contactName;
		this.phone = phone;
		this.email = email;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.mailingStreet = mailingStreet;
		this.mailingCity = mailingCity;
		this.mailingState = mailingState;
		this.mailingZip = mailingZip;
	}
	
	public Location toLocation() {
		Location location = new Location();
		
		location.setLocationId(locationId);
		location.setPlaceName(placeName);
		location.setContactName(contactName);
		location.setPhone(phone);
		location.setEmail(email);
		location.setStreetAddress(streetAddress);
		location.setCity(city);
		location.setState(state);
		location.setZip(zip);
		location.setMailingStreet(mailingStreet);
		location.setMailingCity(mailingCity);
		location.setMailingState(mailingState);
		location.setMailingZip(mailingZip);
		
		for(LoanData loanData : loans) {
			location.getLoans().add(loanData.toLoan());
		}
		
		return location;
	}
}


