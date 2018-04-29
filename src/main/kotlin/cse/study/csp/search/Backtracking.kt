package cse.study.csp.search

import cse.study.csp.domain.Assignment
import cse.study.csp.domain.Search
import cse.study.csp.entity.Variable
import cse.study.csp.problem.CSCScheduleAssignment
import cse.study.csp.problem.CSCScheduleProblem
import cse.study.csp.problem.DifferentTimeRange
import cse.study.csp.problem.TimeRange
import org.slf4j.LoggerFactory
import java.util.*

// TODO --> Apply generic approach !!!
class Backtracking(private val problem: CSCScheduleProblem,
                   private val inference: CSCScheduleAssignment) : Search {

    private val log = LoggerFactory.getLogger(javaClass)

    private val constraint = problem.constraints.map { it as DifferentTimeRange }

    override fun solve(): Assignment = recursiveSolve(inference)

    private fun recursiveSolve(assignment: CSCScheduleAssignment): Assignment {
        problem.takeUnless { it.isSatisfied(assignment) }?.run { prepareAssignmentSpace(assignment) }

        problem.isConsistent(assignment)?.apply {
            val domain = this.getUnassignedDomain()
            val timeRange = retrieveTimeRange(this)
            val deprecatedVariable = this
            domain?.let { this.assignDomain(it) }

            assignment.updateAssignment(deprecatedVariable, this, timeRange)
            recursiveSolve(assignment)
        } ?: return assignment

        // The problem does not have solution
        return CSCScheduleAssignment.BLANK
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
