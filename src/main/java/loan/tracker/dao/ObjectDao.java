package loan.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import loan.tracker.entity.AnObject;

public interface ObjectDao extends JpaRepository<AnObject, Long> {

}
