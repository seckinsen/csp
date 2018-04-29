package cse.study.csp.problem

import cse.study.csp.domain.Constraint
import cse.study.csp.domain.Problem
import cse.study.csp.entity.Domain
import cse.study.csp.entity.Variable
import java.time.LocalTime

class CSCScheduleProblem : Problem<CSCScheduleAssignment>() {

    init {
        Domain(Classroom.A01.toString()).apply { domains.add(this) }
        Domain(Classroom.B01.toString()).apply { domains.add(this) }
        Domain(Classroom.C01.toString()).apply { domains.add(this) }

        prepareVariables()
        prepareConstraints()
    }

    override fun prepareVariables(): Set<Variable> {
        Variable(Course.C1.toString())
                .apply { addDomains(subDomains(setOf(Classroom.C01))) }
                .also { variables.add(it) }
        Variable(Course.C2.toString())
                .apply { addDomains(subDomains(setOf(Classroom.B01, Classroom.C01))) }
                .also { variables.add(it) }
        // TODO --> `addDomains(domains)` does not work properly ????
        Variable(Course.C3.toString())
                .apply { addDomains(subDomains(setOf(Classroom.A01, Classroom.B01, Classroom.C01))) }
                .also { variables.add(it) }
        Variable(Course.C4.toString())
                .apply { addDomains(subDomains(setOf(Classroom.A01, Classroom.B01, Classroom.C01))) }
                .also { variables.add(it) }
        Variable(Course.C5.toString())
                .apply { addDomains(subDomains(setOf(Classroom.B01, Classroom.C01))) }
                .also { variables.add(it) }

        return variables
    }

    override fun prepareConstraints(): Set<Constraint<CSCScheduleAssignment>> {
        DifferentTimeRange(TimeRange(LocalTime.of(8, 0), LocalTime.of(9, 0)))
                .apply { addVariable(getVariable(Course.C1)) }
                .also { constraints.add(it) }
        DifferentTimeRange(TimeRange(LocalTime.of(8, 30), LocalTime.of(9, 30)))
                .apply { addVariable(getVariable(Course.C2)) }
                .also { constraints.add(it) }
        DifferentTimeRange(TimeRange(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .apply { addVariable(getVariable(Course.C3)) }
                .also { constraints.add(it) }
        DifferentTimeRange(TimeRange(LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .apply { addVariable(getVariable(Course.C4)) }
                .also { constraints.add(it) }
        DifferentTimeRange(TimeRange(LocalTime.of(9, 30), LocalTime.of(10, 30)))
                .apply { addVariable(getVariable(Course.C5)) }
                .also { constraints.add(it) }

        return constraints
    }

    override fun isSatisfied(assignment: CSCScheduleAssignment): Boolean = variables.size
            .takeIf { it > assignment.assignments.size }
            ?.let { false }
            ?: true

    // TODO --> Refactor!!!
    override fun isConsistent(assignment: CSCScheduleAssignment): Variable? {
        constraints.forEach {
            val pair = it.consistent(assignment)
            if (!pair.second) return pair.first!!
        }
        return null
    }

    private fun subDomains(definitions: Set<Classroom>) =
            domains.filter { definitions.toString().contains(it.definition) }.toSet()

    private fun getVariable(definitions: Course) =
            variables.first { definitions.toString().contains(it.definition) }

}
