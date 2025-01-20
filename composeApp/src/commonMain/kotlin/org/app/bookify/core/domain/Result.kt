package org.app.bookify.core.domain

/**
 * `Result` is a sealed interface representing the outcome of an operation,
 * which can either be a successful result with data (`Success`) or a failure with an error (`Error`).
 *
 * - `Success<D>` holds the result data of type `D`.
 * - `Error<E>` holds an error of type `E`, which must extend `org.app.bookify.core.domain.Error`.
 *
 * This sealed interface allows for a restricted hierarchy where only `Success` and `Error`
 * are valid implementations, ensuring exhaustive handling in `when` expressions.
 */
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : org.app.bookify.core.domain.Error>(val error: E) :
        Result<Nothing, E>
}

/**
 * Transforms the data of a successful `Result` using the provided mapping function.
 * If the `Result` is an error, it returns the same error.
 *
 * @param map A function that takes the successful data of type `T` and returns transformed data of type `R`.
 * @return A new `Result` containing the transformed data of type `R` if successful, or the original error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

/**
 * Converts a `Result` to an `EmptyResult`, discarding any successful data and only retaining the error.
 *
 * @return An `EmptyResult` with `Unit` as the success type and the same error type.
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

/**
 * Executes the given action if the `Result` is successful, passing the successful data to the action.
 * Returns the original `Result`.
 *
 * @param action A function to be executed with the successful data if the `Result` is successful.
 * @return The original `Result`.
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Executes the given action if the `Result` is successful, passing the successful data to the action.
 * Returns the original `Result`.
 *
 * @param action A function to be executed with the successful data if the `Result` is successful.
 * @return The original `Result`.
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }
}

/** `EmptyResult<E>` is an alias for `Result<Unit, E>`.
 * It is used to represent a `Result` type where no meaningful data is returned on success (`Unit`),
 * but it can still carry an error of type `E` on failure.
 * This alias improves code readability by making it clear that the result is intentionally "empty" on success.
 **/
typealias EmptyResult<E> = Result<Unit, E>
