package kurswork.hospital.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import kurswork.hospital.entity.Card;
public interface CardRepository extends JpaRepository<Card,Long>{
    Card findByNumber(String number);
}
