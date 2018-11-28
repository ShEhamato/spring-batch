package SpringBatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class SpringBatchProcess {
	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Bean
	FlatFileItemReader<Item> reader() {
		return new FlatFileItemReaderBuilder<Item>()
				.name("itemReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names(new String [] {"item_name", "item_code"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Item>() {{
					 setTargetType(Item.class);
				}})
				.build();
	}

	
	@Bean
	JdbcBatchItemWriter<Item> writer(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Item>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("Insert into Item (item_name, item_code) values (:item_name, :item_code)")
				.dataSource(dataSource)
				.build();
		
	}
	@Bean
	Job itemMigration(Step step) {

		return jobBuilderFactory
				.get("itemMigration")
				.flow(step).end()
				.build();

	}

	@Bean
	Step step(JdbcBatchItemWriter<Item> writer) {
		ItemProcessor<? super Item, ? extends Item> function = null;
		return stepBuilderFactory.get("step")
				.<Item, Item>chunk(5)
				.reader(reader())
				.processor(function)
				.writer(writer)
				.build();
	}

}
