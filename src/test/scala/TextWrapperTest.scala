import org.specs2.mutable.Specification

/** testOnly TextWrapperTest */
class TextWrapperTest extends Specification {

  val textWrapper = new TextWrapper()
  import textWrapper._
  import StringUtils._

  val limit = 40

  val originalText: String = "In 1991, while studying computer science at University of Helsinki," +
    " Linus Torvalds began a project that later became the Linux kernel." +
    " He wrote the program specifically for the hardware he was using and" +
    " independent of an operating system because he wanted to use the functions" +
    " of his new PC with an 80386 processor. Development was done on MINIX using" +
    " the GNU C Compiler."

  val originalWrappedText: String = "In 1991, while studying computer science\n" +
    "at University of Helsinki, Linus\n" +
    "Torvalds began a project that later\n" +
    "became the Linux kernel. He wrote the\n" +
    "program specifically for the hardware he\n" +
    "was using and independent of an\n" +
    "operating system because he wanted to\n" +
    "use the functions of his new PC with an\n" +
    "80386 processor. Development was done on\n" +
    "MINIX using the GNU C Compiler."

  "wrapText" should {

    "handle empty string" in {
      wrapText("", limit) must beRight("")
    }

    "produce correct output for text made of chars" in {
      val input = "\n  \n    \n\n"
      val expected = ""
      val result = wrapText(input)
      result must beRight

      result.toOption.get must beEqualTo(expected)
    }

    "fail on a string that is too big" in {
      wrapText("01234567890123456789012345678901234567890", limit) must beLeft
    }

    "not break words" in {
      val input = originalText
      val result = wrapText(input, limit)
      result must beRight

      val inputWords = input.words
      val outputWords = result.toOption.get.words
      inputWords must beEqualTo(outputWords)
    }

    "break text into lines of at most the given character limit" in {
      val input = originalText
      val result = wrapText(input, limit)
      result must beRight

      val outputLines: List[String] = result.toOption.get.linesIterator.toList
      outputLines must forall { s: String =>
        s.length must be_<=(40)
      }
    }

    "produce correct output for example text" in {
      val input = originalText
      val expected = originalWrappedText
      val result = wrapText(input)
      result must beRight

      result.toOption.get must beEqualTo(expected)
    }
  }
}
