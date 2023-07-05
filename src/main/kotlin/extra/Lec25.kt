package extra

import kotlin.reflect.KClass

class Lec25 {
}

tailrec fun factorialV2(n: Int, curr: Int = 1): Int {
  return if (n <= 1) {
    curr
  } else {
    factorialV2(n - 1, n * curr)
  }
}


@JvmInline
value class Key(val key: String)

class User(
  val id: Id<User>,
  val name: String,
)

class Book(
  val id: Id<Book>,
  val author: String,
)

fun handle(userId: Long, bookId: Long) {

}

fun handleV2(userId: Id<User>, bookId: Id<Book>) {

}

@JvmInline
value class Id<T>(val id: Long)

@JvmInline
value class Number(val num: Long) {
  init {
    require(num in 1..10)
  }
}


fun logic(n: Int) {
  when {
    n > 0 -> throw AException()
    n == 0 -> throw BException()
  }
  throw CException()
}

class AException : RuntimeException()
class BException : RuntimeException()
class CException : RuntimeException()

fun main() {
  try {
    logic(10)
  } catch(e: Exception) {
    when (e) {
      is AException,
      is BException -> TODO()
      is CException -> TODO()
    }
    throw e
  }

  runCatching { logic(10) }
    .onError(AException::class, BException::class) {
      println("A 또는 B 예외가 발생했습니다.")
    }
    .onError(AException::class) {

    }


//  val key = Key("비밀 번호")
//  println(key)
//
//  val userId = 1L
//  val bookId = 2L
//  handle(
//    bookId = bookId,
//    userId = userId,
//  )
//
//  val inlineUserId = Id<User>(1L)
//  val inlineBookId = Id<Book>(2L)
//  handleV2(inlineUserId, inlineBookId)
}

class ResultWrapper<T>(
  private val result: Result<T>,
  private val knownExceptions: MutableList<KClass<out Throwable>>,
) {
  fun toResult(): Result<T> {
    return this.result
  }

  fun onError(vararg exceptions: KClass<out Throwable>, action: (Throwable) -> Unit): ResultWrapper<T> {
    this.result.exceptionOrNull()?.let {
      if (it::class in exceptions && it::class !in this.knownExceptions) {
        action(it)
      }
    }
    return this
  }

}

fun <T> Result<T>.onError(vararg exceptions: KClass<out Throwable>, action: (Throwable) -> Unit): ResultWrapper<T> {
  exceptionOrNull()?.let {
    if (it::class in exceptions) {
      action(it)
    }
  }
  return ResultWrapper(this, exceptions.toMutableList())
}