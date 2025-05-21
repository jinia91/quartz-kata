//package deepdive.quartz.quartz
//
//import org.quartz.JobExecutionContext
//import org.quartz.JobExecutionException
//import org.quartz.JobListener
//import org.springframework.jdbc.core.JdbcTemplate
//import java.time.LocalDateTime
//import org.springframework.stereotype.Component
//
//@Component
//class AuditJobListener(
//    private val jdbcTemplate: JdbcTemplate
//) : JobListener {
//    override fun getName(): String {
//        return "auditJobListener"
//    }
//
//    override fun jobToBeExecuted(context: JobExecutionContext?) {
//    }
//
//    override fun jobExecutionVetoed(context: JobExecutionContext?) {
//    }
//
//    override fun jobWasExecuted(context: JobExecutionContext, jobException: JobExecutionException?) {
//        val jobName: String = context.jobDetail.key.toString()
//        val isSuccess = jobException == null
//        val now = LocalDateTime.now()
//
//        jdbcTemplate.update(
//            "INSERT INTO job_audit_log (job_name, executed_at, success, error_message) VALUES (?, ?, ?, ?)",
//            jobName,
//            now,
//            isSuccess,
//            jobException?.message
//        )
//    }
//}