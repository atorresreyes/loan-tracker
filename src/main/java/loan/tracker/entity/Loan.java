package loan.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	@EqualsAndHashCode.Exclude
	private String status;
	
	@EqualsAndHashCode.Exclude
	private String startDate;
	
	@EqualsAndHashCode.Exclude
	private String endDate;
	
	@EqualsAndHashCode.Exclude
	private String purpose;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "location_id", nullable = false)
	private Location location;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST) //don't delete object if loan deleted
	@JoinTable(
			name = "loan_an_object",
			joinColumns = @JoinColumn(name = "loan_id"),
			inverseJoinColumns = @JoinColumn(name = "object_id")
	)
	private Set<AnObject> anObject = new HashSet<>();
	
}
