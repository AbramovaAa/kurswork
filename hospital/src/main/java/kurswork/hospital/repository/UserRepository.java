package kurswork.hospital.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import kurswork.hospital.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByEmail(String email);
}
