package pl.bpiotrowski.crmrest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "settings")
public class Setting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String firstFieldName;
	
	@Column
	private String secondFieldName;
	
	@Column
	private String thirdFieldName;
	
	@Column
	private String fourthFieldName;
	
	@Column
	private String textareaName;
			
}
