package py.edu.uc.lp32025.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.edu.uc.lp32025.domain.EmpleadoTiempoCompleto;

@Repository
public interface EmpleadoTiempoCompletoRepository extends JpaRepository<EmpleadoTiempoCompleto, Long> {
}
