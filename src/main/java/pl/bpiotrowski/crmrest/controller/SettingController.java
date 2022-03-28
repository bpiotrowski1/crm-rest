package pl.bpiotrowski.crmrest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.bpiotrowski.crmrest.dto.SettingDto;
import pl.bpiotrowski.crmrest.service.SettingService;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/settings")
public class SettingController {
	
	private final SettingService settingService;

	@GetMapping
	public SettingDto findSetting() {
		return settingService.findLastOne() != null ? settingService.findLastOne() : new SettingDto();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public SettingDto addSetting(@Valid @RequestBody SettingDto setting) {
		return settingService.create(setting);
	}
	
	@PutMapping("/{id}")
	public SettingDto updateSetting(@Valid @RequestBody SettingDto setting, @PathVariable Long id) {
		setting.setId(id);
		return settingService.update(setting);
	}
	
}
