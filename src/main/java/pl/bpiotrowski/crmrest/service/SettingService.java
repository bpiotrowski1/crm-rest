package pl.bpiotrowski.crmrest.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.bpiotrowski.crmrest.dto.SettingDto;
import pl.bpiotrowski.crmrest.entity.Setting;
import pl.bpiotrowski.crmrest.exception.SettingNotFoundException;
import pl.bpiotrowski.crmrest.mapper.SettingMapper;
import pl.bpiotrowski.crmrest.repository.SettingRepository;

@Service
@RequiredArgsConstructor
public class SettingService {
	
	private final SettingMapper settingMapper;
	private final SettingRepository settingRepository;
	
	public SettingDto findLastOne() {
		return settingMapper.map(settingRepository.findLastSetting());
	}
	
	public SettingDto create(SettingDto dto) {
        Setting entity = settingMapper.map(dto);
        Setting savedEntity = settingRepository.save(entity);
        return settingMapper.map(savedEntity);
    }
	
	public SettingDto update(SettingDto dto) {
        Setting updatedSetting = settingRepository.findById(dto.getId())
                .orElseThrow(() -> new SettingNotFoundException(dto.getId()));
        updatedSetting.setFirstFieldName(dto.getFirstFieldName());
        updatedSetting.setSecondFieldName(dto.getSecondFieldName());
        updatedSetting.setThirdFieldName(dto.getThirdFieldName());
        updatedSetting.setFourthFieldName(dto.getFourthFieldName());
        updatedSetting.setTextAreaName(dto.getTextAreaName());
        settingRepository.save(updatedSetting);
        return settingMapper.map(updatedSetting);
    }

}
