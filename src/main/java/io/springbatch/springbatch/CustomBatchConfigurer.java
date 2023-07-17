package io.springbatch.springbatch;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.reactive.TransactionContextManager;

import javax.sql.DataSource;
@Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

    private DataSource source;

    protected CustomBatchConfigurer(BatchProperties properties, DataSource source, TransactionManagerCustomizers transactionContextManager) {
        super(properties, source,transactionContextManager);
        this.source = source;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(source);
        jobRepositoryFactoryBean.setTransactionManager(getTransactionManager());
        jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMITTED");
        return jobRepositoryFactoryBean.getObject();
    }
}
