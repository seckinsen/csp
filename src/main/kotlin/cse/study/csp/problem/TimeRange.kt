package cse.study.csp.problem

import java.time.LocalTime

class TimeRange(private val from: LocalTime, private val to: LocalTime) {

    companion object {
        val NOW = TimeRange(LocalTime.now(), LocalTime.now())
    }

    fun hasConflict(timeRange: TimeRange): Boolean =
            from.inRange(timeRange) || to.inRange(timeRange) || (from.equal(timeRange.from) && to.equal(timeRange.to))

    override fun toString(): String = "Time Range: FROM: $from TO: $to"

    private fun LocalTime.inRange(timeRange: TimeRange): Boolean = isAfter(timeRange.from) && isBefore(timeRange.to)

    private fun LocalTime.equal(time: LocalTime): Boolean = compareTo(time) == 0

}
