package ru.hse.db.hw7.orm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.db.hw7.orm.model.Olympic;

@Repository
public interface OlympicRepository extends JpaRepository<Olympic, String> {
}
