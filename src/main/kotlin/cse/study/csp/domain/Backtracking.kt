package cse.study.csp.domain

import cse.study.csp.entity.Variable

abstract class Backtracking<T : Assignment> {

    abstract fun recursiveSolve(assignment: T): Assignment

    abstract fun findMostConstrainingValue(): Variable

    abstract fun findLeastConstrainingValue(): Variable

}
