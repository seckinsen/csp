package cse.study.csp.problem

import cse.study.csp.domain.Constraint
import cse.study.csp.entity.Variable
import java.util.*

class DifferentTimeRange(val timeRange: TimeRange) : Constraint<CSCScheduleAssignment>() {

    //     There is non assign value into constraint variables that why it is not satisfied
    override fun satisfied(): Boolean = variable
            .takeIf { Objects.isNull(it.assignment) }
            ?.let { false }
            ?: true

    // TODO --> Refactor!!!
    override fun consistent(assignment: CSCScheduleAssignment): Pair<Variable?, Boolean> {
        val checkFlag: MutableList<Pair<Variable?, Boolean>> = mutableListOf()

        assignment.assignments
                .filter { checkConflict(it.key, it.value) }
                .forEach { variable, _ ->
                    checkFlag.add(checkArcConsistency(variable))
                }

        return checkFlag.firstOrNull { !it.second } ?: Pair(null, true)
    }

    private fun checkConflict(variable: Variable, time: TimeRange): Boolean =
            timeRange.hasConflict(time) && Objects.nonNull(variable.assignment) && checkVariableMatchup(variable)

    private fun checkArcConsistency(variable: Variable): Pair<Variable?, Boolean> = variable
            .takeIf { it.assignment == this.variable.assignment }
            ?.let { Pair(it, false) }
            ?: Pair(null, true)

    private fun checkVariableMatchup(variable: Variable) = variable
            .takeIf { it == this.variable }
            ?.let { false }
            ?: true

}
