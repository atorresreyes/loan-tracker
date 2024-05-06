package loan.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import loan.tracker.entity.Loan;

public interface LoanDao extends JpaRepository<Loan, Long> {

}
