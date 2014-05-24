import com.cra.figaro.language.{Flip, Select} //#A 
import com.cra.figaro.library.compound.If //#A 
import com.cra.figaro.algorithm.factored.VariableElimination //#A

object HelloWorld {
  val sunnyToday = Flip(0.2) //#B 
  val greetingToday = If(sunnyToday, //#C
    Select(0.6 -> "Hello world!", 0.4 -> "Howdy, universe!"), //#C
    Select(0.2 -> "Hello world!", 0.8 -> "Oh no, not again")) //#C 
  val sunnyTomorrow = If(sunnyToday, Flip(0.8), Flip(0.05)) //#D 
  val greetingTomorrow = If(sunnyTomorrow, //#E
    Select(0.6 -> "Hello world!", 0.4 -> "Howdy, universe!"), //#E 
    Select(0.2 -> "Hello world!", 0.8 -> "Oh no, not again")) //#E

  def predict() {
    val algorithm = VariableElimination(greetingToday) //#F 
    algorithm.start() //#G 
    val result =
      algorithm.probability(greetingToday, "Hello world!") //#H 
    println("Tomorrow's greeting is \"Hello world!\" " +
            "with probability " + result + ".") //#I } algorithm.kill() //#J

  def infer() {
    greetingToday.observe("Hello world!") //#K 
    val algorithm = VariableElimination(sunnyToday) //#F 
    algorithm.start() //#G 
    val result = algorithm.probability(sunnyToday, true) //#H 
    println("If today's greeting is \"Hello world!\", today’s " +
            "weather is sunny with probability " + result + ".") //#I 
    algorithm.kill() //#I
    greetingToday.unobserve() //#L
  }

  def learnAndPredict() {
    greetingToday.observe("Hello world!") //#K 
    val algorithm = VariableElimination(greetingTomorrow) //#F 
    algorithm.start() //#G 
    val result =
      algorithm.probability(greetingTomorrow, "Hello world!") //#H 
    println("If today's greeting is \"Hello world!\", " +
            "tomorrow's greeting will be \"Hello world!\" " +
            "with probability " + result + ".") //#I 
    algorithm.kill() //#J 
    greetingToday.unobserve() //#L
  }

  def main(args: Array[String]) { //#M 
    predict() //#M 
    infer() //#M
    learnAndPredict() //#M
  }
}
