package cse.study.csp

import cse.study.csp.problem.TimeRange
import spock.lang.Specification

import java.time.LocalTime

class TimeRangeSpec extends Specification {

    def "time range conflict check with cross ranges should overlap"() {
        given:
        def range1 = new TimeRange(LocalTime.of(8, 0), LocalTime.of(9, 0))
        def range2 = new TimeRange(LocalTime.of(8, 30), LocalTime.of(9, 30))

        expect:
        range1.hasConflict(range2)
    }

    def "time range conflict check with non-cross ranges should not overlap"() {
        given:
        def range1 = new TimeRange(LocalTime.of(8, 0), LocalTime.of(9, 0))
        def range2 = new TimeRange(LocalTime.of(10, 30), LocalTime.of(11, 30))

        expect:
        !range1.hasConflict(range2)
    }

    def "time range conflict check with equal ranges should overlap"() {
        given:
        def range1 = new TimeRange(LocalTime.of(8, 0), LocalTime.of(9, 0))
        def range2 = new TimeRange(LocalTime.of(8, 0), LocalTime.of(9, 0))

        expect:
        range1.hasConflict(range2)
    }

}
