package cse.study.csp.domain

import cse.study.csp.entity.Variable

abstract class Constraint<in T : Assignment> {

    lateinit var variable: Variable

    fun addVariable(element: Variable) {
        variable = element
    }

    abstract fun satisfied(): Boolean

    // TODO --> Think about its best practices ???
    abstract fun consistent(assignment: T): Pair<Variable?, Boolean>

}
