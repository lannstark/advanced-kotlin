package dsl

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main() {
  val yml = dockerCompose {
    version { 3 }
    service(name = "db") {
      image { "mysql" }
      env("USER" - "myuser")
      env("PASSWORD" - "mypassword")
      port(host = 9999, container = 3306)
    }
  }

  val yml2 = dockerCompose {
    service("") {
//      service("") { // @DslMarker 효과
//
//      }
    }
  }

  println(yml.render("  "))
}

fun dockerCompose(init: DockerCompose.() -> Unit): DockerCompose {
  val dockerCompose = DockerCompose()
  dockerCompose.init()
  return dockerCompose
}

@YamlDsl
class DockerCompose {
  private var version: Int by onceNotNull()
  private val services = mutableListOf<Service>()

  fun version(init: () -> Int) {
    version = init()
  }

  fun service(name: String, init: Service.() -> Unit) {
    val service = Service(name)
    service.init()
    services.add(service)
  }

  fun render(indent: String): String {
    val builder = StringBuilder()
    builder.appendNew("version: '$version'")
    builder.appendNew("services:")
    builder.appendNew(services.joinToString("\n") { it.render(indent) }.addIndent(indent, 1))
    return builder.toString()
  }
}

@YamlDsl
class Service(private val name: String) {
  private var image: String by onceNotNull()
  private val environments = mutableListOf<Environment>()
  private val portRules = mutableListOf<PortRule>()

  fun image(init: () -> String) {
    image = init()
  }

  fun env(environment: Environment) {
    this.environments.add(environment)
  }

  fun port(host: Int, container: Int) {
    this.portRules.add(PortRule(host = host, container = container))
  }

  fun render(indent: String): String {
    val builder = StringBuilder()
    builder.appendNew("$name:")
    builder.appendNew("image: $image", indent, 1)
    builder.appendNew("environment:")
    environments.joinToString("\n") { "- ${it.key}: ${it.value}" }
      .addIndent(indent, 1)
      .also { builder.appendNew(it) }
    builder.appendNew("port:")
    portRules.joinToString("\n") { "- \"${it.host}:${it.container}\"" }
      .addIndent(indent, 1)
      .also { builder.appendNew(it) }
    return builder.toString()
  }
}

data class Environment(
  val key: String,
  val value: String,
)

operator fun String.minus(other: String): Environment {
  return Environment(
    key = this,
    value = other,
  )
}

data class PortRule(
  val host: Int,
  val container: Int
)

fun <T> onceNotNull() = object : ReadWriteProperty<Any? ,T> {
  private var value: T? = null
  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    if (this.value == null) {
      throw IllegalArgumentException("변수가 초기화되지 않았습니다")
    }
    return this.value!!
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    if (this.value != null) {
      throw IllegalArgumentException("이 변수는 한 번만 값을 초기화할 수 있습니다.")
    }
    this.value = value
  }
}

fun StringBuilder.appendNew(str: String, indent: String = "", times: Int = 0) {
  (1..times).forEach { _ -> this.append(indent) }
  this.append(str)
  this.append("\n")
}

fun String.addIndent(indent: String, times: Int = 0): String {
  val allIndent = (1..times).joinToString("") { indent }
  return this.split("\n")
    .joinToString("\n") { "$allIndent$it" }
}

@DslMarker
annotation class YamlDsl