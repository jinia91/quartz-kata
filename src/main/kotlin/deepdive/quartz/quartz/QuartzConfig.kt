package deepdive.quartz.quartz

//import deepdive.quartz.quartz.job.LogJob
import jakarta.annotation.PostConstruct
import java.util.concurrent.ThreadLocalRandom
import org.jobrunr.scheduling.JobScheduler
import org.jobrunr.scheduling.cron.Cron
import org.springframework.context.annotation.Configuration

//
//@Configuration
//class QuartzConfig(
//    private val scheduler: Scheduler,
////    private val auditJobListener: AuditJobListener,
//) {
//    @PostConstruct
//    fun initSchedule() {
//        val jobDetail = JobBuilder.newJob(LogJob::class.java)
//            .withIdentity("logJob", "logGroup")
//            .storeDurably()
//            .build()
//
//        val trigger = TriggerBuilder.newTrigger()
//            .forJob(jobDetail)
//            .withIdentity("logTrigger", "logGroup")
//            .withSchedule(
//                SimpleScheduleBuilder.simpleSchedule()
//                    .withIntervalInSeconds(10)
//                    .repeatForever()
//            ).build()
//
//        scheduler.scheduleJob(jobDetail, trigger)
////        scheduler.listenerManager.addJobListener(auditJobListener)
//    }
//}

@Configuration
class JobRunrJobConfig(
    private val jobScheduler: JobScheduler,
) {
    @PostConstruct
    fun scheduleJob() {
        jobScheduler.scheduleRecurrently(Cron.every15seconds()) {
            val currentTimeMillis = System.currentTimeMillis()
            val randomValue = ThreadLocalRandom.current().nextInt(0, 101)
            println("JobRunr job executed at$currentTimeMillis, random value: $randomValue")

        }
    }
}

@Configuration
class TestSchedule {
    @PostConstruct
    fun loop() {
        val printLambda = { println("Hello, World! ${System.currentTimeMillis()}") }

        val capture = printLambda.javaClass.name
        print(capture)

        Thread {
            while (true) {
                printLambda()
                Thread.sleep(15000)
            }
        }.start()

        println(capture)
    }
}