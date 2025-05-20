package deepdive.quartz.quartz

import deepdive.quartz.quartz.job.LogJob
import jakarta.annotation.PostConstruct
import org.quartz.*
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzConfig(
    private val scheduler: Scheduler,
    private val auditJobListener: AuditJobListener
) {
    @PostConstruct
    fun initSchedule() {
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

