package cse.study.csp

import cse.study.csp.problem.CSCScheduleAssignment
import cse.study.csp.problem.CSCScheduleProblem
import cse.study.csp.problem.Course
import cse.study.csp.problem.CSCScheduleBacktracking
import org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {

    val log = LoggerFactory.getLogger("CSP-Main")

    val cscSchedule = CSCScheduleProblem()

    cscSchedule.variables.forEach {
        log.info("Variable: ${reflectionToString(it)}")
    }

    cscSchedule.constraints.forEach {
        log.info("Constraint: ${reflectionToString(it)}")
    }

    val search = CSCScheduleBacktracking(cscSchedule, CSCScheduleAssignment.BLANK)
    val finalAssignment = search.solve()

    log.info("Final Assignment: $finalAssignment")

    (finalAssignment as CSCScheduleAssignment).assignments
            .forEach { variable, timeRange ->
                log.info("Variable: ${variable.definition} [${Course.valueOf(variable.definition).information}] --> Assignment: ${variable.assignment} [$timeRange]")
            }

}
