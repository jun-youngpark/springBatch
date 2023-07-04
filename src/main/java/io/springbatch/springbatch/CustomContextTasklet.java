package io.springbatch.springbatch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomContextTasklet implements Tasklet {


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("CustomContextTasklet was excueted");

        //job 내 공유되는 context
        ExecutionContext jobExecutionContext = stepContribution.getStepExecution().getJobExecution().getExecutionContext();
        //step 내 공유 되는 ccontext
        ExecutionContext stepExecutionContext = stepContribution.getStepExecution().getExecutionContext();
        System.out.println("chucnk" + chunkContext.getStepContext().getStepExecution().getStepName());
        stepExecutionContext.put("stepName","1");
        System.out.println("jobName" + jobExecutionContext.get("jobName"));
        System.out.println("stepName" + stepExecutionContext.get("stepName"));


        return RepeatStatus.FINISHED;
    }
}
