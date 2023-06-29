package reflection

import kotlin.reflect.KClass

@Repeatable
@Target(AnnotationTarget.CLASS)
annotation class Shape(
  val texts: Array<String>
)

@Shape(["C"])
@Shape(["A", "B"])
class Annotation {
}

fun main() {
  val clazz: KClass<Annotation> = Annotation::class
}