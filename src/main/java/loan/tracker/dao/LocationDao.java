package loan.tracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import loan.tracker.entity.Location;

public interface LocationDao extends JpaRepository<Location, Long> {

}
