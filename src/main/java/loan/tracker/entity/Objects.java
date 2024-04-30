package loan.tracker.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Objects {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long objectId;
	
	@EqualsAndHashCode.Exclude
	private Long catalogNumber;
	
	@EqualsAndHashCode.Exclude
	private String commonName;
	
	@EqualsAndHashCode.Exclude
	private String materialType;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "objects")
	private Set<Loan> loans = new HashSet<>();
}
