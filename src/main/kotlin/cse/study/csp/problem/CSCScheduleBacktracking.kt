package cse.study.csp.problem

import cse.study.csp.domain.Assignment
import cse.study.csp.domain.Backtracking
import cse.study.csp.domain.Search
import cse.study.csp.entity.Variable
import org.slf4j.LoggerFactory
import java.util.*

class CSCScheduleBacktracking(private val problem: CSCScheduleProblem,
                              private val inference: CSCScheduleAssignment) : Backtracking<CSCScheduleAssignment>(), Search {

    private val log = LoggerFactory.getLogger(javaClass)

    private val constraint = problem.constraints.map { it as DifferentTimeRange }

    override fun solve(): Assignment = recursiveSolve(inference)

    override fun recursiveSolve(assignment: CSCScheduleAssignment): Assignment {
        problem.takeUnless { it.isSatisfied(assignment) }?.run { prepareAssignmentSpace(assignment) }

        problem.isConsistent(assignment)?.apply {
            val domain = this.getUnassignedDomain()
            val timeRange = retrieveTimeRange(this)
            val deprecatedVariable = this
            domain?.let { this.assignDomain(it) }

            assignment.updateAssignment(deprecatedVariable, this, timeRange)
            recursiveSolve(assignment)
        } ?: return assignment

//        The problem does not have solution
        return CSCScheduleAssignment.BLANK
    }

    // TODO --> Refactor!!!
    override fun findMostConstrainingValue(): Variable {
        val (timeTable, timeRanges) = prepareSortedConstraint()

        val sortedMCV = timeTable.toList().sortedByDescending { it.second }

        sortedMCV.forEach { log.info("Sorted MCV: $it") }

        val mcv = timeRanges.filter { Objects.isNull(it.second.assignment) }
                .firstOrNull { it.first == sortedMCV.map { it.first }.first() }

        log.info("MCV: $mcv")

        return mcv!!.second
    }

    // TODO --> Refactor!!!
    override fun findLeastConstrainingValue(): Variable {
        val (timeTable, timeRanges) = prepareSortedConstraint()

        val sortedLCV = timeTable.toList().sortedBy { it.second }

        sortedLCV.forEach { log.info("Sorted LCV: $it") }

        val lcv = timeRanges.filter { Objects.isNull(it.second.assignment) }
                .firstOrNull { it.first == sortedLCV.map { it.first }.first() }

        log.info("LCV: $lcv")

        return lcv!!.second
    }

    // TODO --> Refactor!!!
    private fun prepareSortedConstraint(): Pair<MutableMap<TimeRange, Int>, List<Pair<TimeRange, Variable>>> {
        val timeTable: MutableMap<TimeRange, Int> = mutableMapOf()

        constraint.map { it.timeRange }.forEach { timeTable[it] = 0 }

        val timeRanges = constraint.map { it.timeRange to it.variable }

        constraint.forEach { range ->
            timeRanges.forEach {
                if (it.first.hasConflict(range.timeRange) && it.second != range.variable) {
                    timeTable[range.timeRange] = timeTable[range.timeRange]!!.inc()
                }
            }
        }

        timeTable.forEach { key, value -> log.info("Key: $key - Value: $value") }

        return (timeTable to timeRanges)
    }

    private fun prepareAssignmentSpace(assignment: CSCScheduleAssignment) {
        val variable = retrieveUnassignedOne()
        val timeRange = retrieveTimeRange(variable)

        variable.domains.first().apply {
            variable.assignDomain(this)
            assignment.addAssignment(variable, timeRange)
            log.info("Assignment: $timeRange | $variable --> $assignment")
            recursiveSolve(assignment)
        }
    }

    private fun retrieveUnassignedOne(): Variable =
            problem.variables.first { Objects.isNull(it.assignment) }

    private fun retrieveTimeRange(variable: Variable): TimeRange =
            constraint.first { it.variable == variable }.timeRange


}
