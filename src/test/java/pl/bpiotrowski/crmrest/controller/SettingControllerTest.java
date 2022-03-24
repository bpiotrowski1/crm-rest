package pl.bpiotrowski.crmrest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import pl.bpiotrowski.crmrest.entity.Setting;
import pl.bpiotrowski.crmrest.repository.SettingRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SettingControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private SettingRepository settingRepository;

    @AfterEach
    public void tearDown() {
        settingRepository.deleteAll();
    }
    
    @Test
    public void getLastOne() throws Exception {
    	String firstSettingFirstFieldName = "first setting first field";
    	String secondSettingFirstFieldName = "second setting first field";
    	Setting first = create(firstSettingFirstFieldName);
    	Setting second = create(secondSettingFirstFieldName);
    	settingRepository.save(first);
    	settingRepository.save(second);
    	
    	mockMvc.perform(get("/api/settings").contentType(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.firstFieldName", is(secondSettingFirstFieldName)));
    }
    
    @Test
    public void createTest() throws Exception {
    	//given
    	String settingFirstFieldName = "first field name";
    	String settingJson = "{\"firstFieldName\":\"{firstFieldName}\"}".replace("{firstFieldName}", settingFirstFieldName);
    	
    	//when
    	mockMvc.perform(post("/api/settings").contentType(MediaType.APPLICATION_JSON).content(settingJson))
    		.andExpect(status().isCreated());
    	
    	//then
    	List<Setting> settings = settingRepository.findAll();
    	assertThat(settings).hasSize(1);
    	
    	Setting result = settings.get(0);
    	assertThat(result.getFirstFieldName()).isEqualTo(settingFirstFieldName);
    }
    
    @Test
    public void createNotValidTest() throws Exception {
    	//given
    	String settingFirstFieldName = "";
    	String settingJson = "{\"firstFieldName\":\"{firstFieldName}\"".replace("{firstFieldName}", settingFirstFieldName);
    	
    	//when
    	mockMvc.perform(post("/api/settings").contentType(MediaType.APPLICATION_JSON).content(settingJson))
			.andExpect(status().isBadRequest());
    	
    	//then
    	List<Setting> settings = settingRepository.findAll();
    	assertThat(settings).isEmpty();
    }
    
    @Test
    public void updateTest() throws Exception {
    	//given
    	Setting setting = new Setting();
    	setting.setFirstFieldName("first field name");
    	
    	Long id = settingRepository.save(setting).getId();
    	
    	String newFirstFieldName = "new first field name";
    	String settingJson = "{\"id\":{id},\"firstFieldName\":\"{firstFieldName}\"}"
                .replace("{id}", String.valueOf(id))
                .replace("{firstFieldName}", newFirstFieldName);
    	
    	//when
    	mockMvc.perform(put("/api/settings/{id}", id).contentType(MediaType.APPLICATION_JSON).content(settingJson))
    		.andExpect(status().isOk());
    	
    	//then
    	Setting result = settingRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    	assertThat(result.getFirstFieldName()).isEqualTo(newFirstFieldName);
    }
    
    @Test
    public void updateNotValidTest() throws Exception {
    	//given
    	Long id = 1L;
    	String firstFieldName = "";
    	String settingJson = "{\"id\":{id},\"firstFieldName\":\"{firstFieldName}\"}"
                .replace("{id}", String.valueOf(id))
                .replace("{name}", firstFieldName);
    	
    	//when
    	mockMvc.perform(put("/api/settings/{id}", id).contentType(MediaType.APPLICATION_JSON).content(settingJson))
    		.andExpect(status().isBadRequest());
    	
    	//then
    	List<Setting> settings = settingRepository.findAll();
    	assertThat(settings).isEmpty();
    }

    private Setting create(String firstFieldName) {
    	Setting setting = new Setting();
    	setting.setFirstFieldName(firstFieldName);
    	return setting;
    }
    
}
