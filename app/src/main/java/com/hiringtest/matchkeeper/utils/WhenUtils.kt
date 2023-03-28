package com.hiringtest.matchkeeper.utils

/**
 * Calling this property tells the compiler to check whether all cases covered inside a "when" block,
 * even if the "when" block doesn't return anything.
 *
 * Example:
 *   enum class ResultType { LOADING, COMPLETE, ERROR }
 *
 *   when (result.type) {              // no compilation error
 *      LOADING  -> doSomething()
 *      COMPLETE -> doSomethingElse()
 *   }
 *
 *   when (result.type) {              // ERROR: 'when' expression must be exhaustive,
 *      LOADING  -> doSomething()      // add necessary 'ERROR' branch or 'else' branch instead
 *      COMPLETE -> doSomethingElse()
 *   }.exhaustive
 */
val <T> T.exhaustive: T get() = this