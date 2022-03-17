package club.hosppy.hospital.repo;

import club.hosppy.hospital.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, String> {

}
