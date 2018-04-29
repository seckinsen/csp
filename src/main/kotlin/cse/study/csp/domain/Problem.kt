package cse.study.csp.domain

import cse.study.csp.entity.Domain
import cse.study.csp.entity.Variable

abstract class Problem<T : Assignment> {

    protected val domains: MutableSet<Domain> = mutableSetOf()

    val variables: MutableSet<Variable> = mutableSetOf()

    val constraints: MutableSet<Constraint<T>> = mutableSetOf()

    abstract fun prepareVariables(): Set<Variable>

    abstract fun prepareConstraints(): Set<Constraint<T>>

    abstract fun isSatisfied(assignment: T): Boolean

    abstract fun isConsistent(assignment: T): Variable?

}
