package pl.bpiotrowski.crmrest.mapper;

import org.mapstruct.Mapper;

import pl.bpiotrowski.crmrest.dto.SettingDto;
import pl.bpiotrowski.crmrest.entity.Setting;

@Mapper(componentModel = "spring")
public interface SettingMapper {

	Setting map(SettingDto dto);
	
	SettingDto map(Setting entity);
	
}
