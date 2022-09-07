package shopmallsaga.common;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import shopmallsaga.ShowviewApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = { ShowviewApplication.class })
public class CucumberSpingConfiguration {}
