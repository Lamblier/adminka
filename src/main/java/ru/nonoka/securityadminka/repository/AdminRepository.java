package ru.nonoka.securityadminka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nonoka.securityadminka.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
}
