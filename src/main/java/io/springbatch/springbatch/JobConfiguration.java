package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job Job(){
        return jobBuilderFactory.get("Job")
                .start(step1_tasklet()) //필수
                .next(step2())
                .next(step3())
                .build();
    }

    @Bean
    public Step step3() {
        //무한 반복
        return stepBuilderFactory.get("step1")
                .tasklet(new CustomTasklet()).build();

    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")  //chunk 또는 tasklet 둘중 하나만 api 호출 가능합니다.
                .tasklet(new Tasklet() {    //무한 반복
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        JobParameters parameters = stepContribution.getStepExecution().getJobParameters();
                        String name = parameters.getString("name");
                        Double age = parameters.getDouble("age");
                        System.out.println("============================");
                        System.out.println(" >> step2 excute" + name  + "age" +age);
                        System.out.println("============================");
                        return RepeatStatus.FINISHED;    //continue -> 계속 반복
                    }
                }).build();
    }

    @Bean
    public Step step1_tasklet() {
        //무한 반복
        return stepBuilderFactory.get("step1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("============================");
                    System.out.println(" >>  step1 excute");
                    System.out.println("============================");
                    return RepeatStatus.FINISHED;    //한번 실행 후 종료
                }).build();

    }
}
