package pl.bpiotrowski.crmrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.bpiotrowski.crmrest.entity.Setting;

public interface SettingRepository extends JpaRepository<Setting, Long>{
	
	@Query(value = "SELECT TOP 1 * FROM SETTINGS S ORDER BY ID DESC", nativeQuery = true)
	Setting findLastSetting();
	
}
