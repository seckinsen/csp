package cse.study.csp

import cse.study.csp.problem.CSCScheduleAssignment
import cse.study.csp.problem.CSCScheduleBacktracking
import cse.study.csp.problem.CSCScheduleProblem
import cse.study.csp.problem.Course
import spock.lang.Specification

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString

class CSCScheduleProblemSpec extends Specification {

    def "resolve csp problem should succeed"() {
        given:
        def cscSchedule = new CSCScheduleProblem()
        def search = new CSCScheduleBacktracking(cscSchedule, CSCScheduleAssignment.BLANK)

        cscSchedule.variables.forEach {
            println("Variable: ${reflectionToString(it)}")
        }

        cscSchedule.constraints.forEach {
            println("Constraint: ${reflectionToString(it)}")
        }

        when:
        def finalAssignment = search.solve()

        then:
        (finalAssignment as CSCScheduleAssignment).assignments
                .forEach { variable, timeRange ->
            println("Variable: ${variable.definition} [${Course.valueOf(variable.definition).information}] --> Assignment: ${variable.assignment} [$timeRange]")
        }

    }

    def "find most constraint value should succeed"() {
        given:
        def cscSchedule = new CSCScheduleProblem()
        def search = new CSCScheduleBacktracking(cscSchedule, CSCScheduleAssignment.BLANK)

        expect:
        search.findMostConstrainingValue()
    }

    def "find least constraint value should succeed"() {
        given:
        def cscSchedule = new CSCScheduleProblem()
        def search = new CSCScheduleBacktracking(cscSchedule, CSCScheduleAssignment.BLANK)

        expect:
        search.findLeastConstrainingValue()
    }

}
