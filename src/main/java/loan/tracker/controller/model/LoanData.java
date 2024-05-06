package loan.tracker.controller.model;

import java.util.HashSet;
import java.util.Set;

import loan.tracker.entity.Loan;
import loan.tracker.entity.AnObject;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanData {
		private Long loanId;
		private String status;
		private String startDate;
		private String endDate;
		private String purpose;
		private Set<ObjectsData> objectSet = new HashSet<>();
		
		public LoanData(Loan loan) {
			this.loanId = loan.getLoanId();
			this.status = loan.getStatus();
			this.startDate = loan.getStartDate();
			this.endDate = loan.getEndDate();
			this.purpose = loan.getPurpose();
			
			for(AnObject objects : loan.getAnObject()) {
				this.objectSet.add(new ObjectsData(objects));
			}
		}
		
		public LoanData(Long loanId, String status, String startDate, String endDate, String purpose) {
			this.loanId = loanId;
			this.status = status;
			this.startDate = startDate;
			this.endDate = endDate;
			this.purpose = purpose;
		}
		
		public Loan toLoan() {
			Loan loan = new Loan();
			
			loan.setLoanId(loanId);
			loan.setStatus(status);
			loan.setStartDate(startDate);
			loan.setEndDate(endDate);
			loan.setPurpose(purpose);
			
			for(ObjectsData objectsData : objectSet) {
				loan.getAnObject().add(objectsData.toObject());
			}
			
			return loan;
		}
	}

