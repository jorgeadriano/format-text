import org.scalacheck.Prop.forAll
import org.scalacheck.{Gen, Properties}
import StringUtils._

object TextWrapperPropertyTest extends Properties("WrapText") {

  val textWrapper = new TextWrapper()
  import textWrapper._

  // Generator for words of size <= limit
  def wordGen(limit: Int): Gen[String] = Gen.resize(limit, Gen.alphaStr)
  // Generator for text with words of size <= limit
  def textGen(limit: Int): Gen[String] = Gen.listOf(wordGen(limit)).map(_.mkString(" "))
  // Generator for positive numbers
  val limitGen: Gen[Int] = Gen.choose(1, 40)


  property("same words as original text") = forAll(limitGen) { limit: Int =>
    forAll(textGen(limit)) { text: String =>
      wrapText(text, limit) match {
        case Right(wrappedText) =>
          wrappedText.words == text.words
        case Left(_) => false
      }
    }
  }

  property("line length less than limit") = forAll(limitGen) { limit: Int =>
    forAll(textGen(limit)) { text: String =>
      wrapText(text, limit) match {
        case Right(wrappedText) =>
          wrappedText.split("\n").forall(_.length <= limit)
        case Left(_) => false
      }
    }
  }
}
