package cse.study.csp.problem

import cse.study.csp.domain.Assignment
import cse.study.csp.entity.Variable

class CSCScheduleAssignment(val assignments: MutableMap<Variable, TimeRange>) : Assignment() {

    fun addAssignment(variable: Variable, timeRange: TimeRange) {
        assignments[variable] = timeRange
    }

    fun updateAssignment(deprecatedVariable: Variable, updatedVariable: Variable, timeRange: TimeRange) {
        assignments.remove(deprecatedVariable)
        addAssignment(updatedVariable, timeRange)
    }

    companion object {
        val BLANK = CSCScheduleAssignment(mutableMapOf())
    }

    override fun toString(): String {
        return "Assignments[$assignments]"
    }

}
