package loan.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Loan> loans = new HashSet<>();
}
