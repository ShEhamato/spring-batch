package test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import SpringBatch.Application;
import SpringBatch.Item;
import SpringBatch.SpringBatchProcess;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {SpringBatchProcess.class, Application.class, JobLauncherTestUtils.class})
public class SpringBatchTest {
	
	private static final Logger log = LoggerFactory.getLogger(SpringBatchTest.class);

	@Autowired
	private  JdbcTemplate jdbcTemplate;

	  @Autowired
	    private JobLauncherTestUtils jobLauncherTestUtils;


	@Test
	public void test() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();

		jdbcTemplate.query("SELECT item_name, item_code FROM item",
				(rs, row) -> new Item(
						rs.getString(1),
						rs.getString(2))
				).forEach(item -> log.info("Found <" + item + "> in the database."));
		
        Assert.assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
	}

}
