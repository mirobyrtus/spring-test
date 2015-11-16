package main;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuidelineRepository extends JpaRepository<Guideline, Long> {

	List<Guideline> findByDescriptionStartsWithIgnoreCase(String description);
	
}
