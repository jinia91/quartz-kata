package deepdive.quartz.quartz.job

import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jobs")
class JobController(
    private val scheduler: Scheduler
) {
    data class UpdateIntervalRequest(
        val intervalSeconds: Int
    )

    @PostMapping("/update-interval")
    fun updateInterval(
      @RequestBody request: UpdateIntervalRequest,
    ): ResponseEntity<String> {
        val triggerKey = TriggerKey.triggerKey("logTrigger", "logGroup")

        val newTrigger = TriggerBuilder.newTrigger()
            .withIdentity("logTrigger", "logGroup")
            .withSchedule(
                SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(request.intervalSeconds)
                    .repeatForever()
            )
            .build()

        scheduler.rescheduleJob(triggerKey, newTrigger)

        return ResponseEntity.ok("Updated interval to ${request.intervalSeconds} seconds")
    }
}