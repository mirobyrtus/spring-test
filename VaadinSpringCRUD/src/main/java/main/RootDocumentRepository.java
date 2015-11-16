package main;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootDocumentRepository extends JpaRepository<RootDocument, Long> {

	List<RootDocument> findByDescriptionStartsWithIgnoreCase(String description);
	
}
