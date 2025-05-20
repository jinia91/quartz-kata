package deepdive.quartz.quartz

import jakarta.annotation.PostConstruct
import org.quartz.*
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class QuartzJobConfig(
    private val scheduler: Scheduler,
    private val auditJobListener: AuditJobListener
) {
    @PostConstruct
    fun scheduleLogJob() {
        val jobDetail = JobBuilder.newJob(LogJob::class.java)
            .withIdentity("logJob", "logGroup")
            .storeDurably()
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity("logTrigger", "logGroup")
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(10)
                    .repeatForever()
            ).build()

        scheduler.scheduleJob(jobDetail, trigger)
        scheduler.listenerManager.addJobListener(auditJobListener)
    }
}

@Component
class LogJob : Job {
    override fun execute(context: JobExecutionContext) {
        println("쿼츠 동작 ${System.currentTimeMillis()}")
    }
}