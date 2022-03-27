package pl.bpiotrowski.crmrest.dto;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SettingDto {
	
	private Long id;

	@Size(min = 3, max = 255, message = "Name must be greter than 3 and less than 255 chars")
	private String firstFieldName;
	
	@Size(min = 3, max = 255, message = "Name must be greter than 3 and less than 255 chars")
	private String secondFieldName;
	
	@Size(min = 3, max = 255, message = "Name must be greter than 3 and less than 255 chars")
	private String thirdFieldName;
	
	@Size(min = 3, max = 255, message = "Name must be greter than 3 and less than 255 chars")
	private String fourthFieldName;
	
	@Size(min = 3, max = 255, message = "Name must be greter than 3 and less than 255 chars")
	private String textAreaName;
	
}
