import cats.implicits._
import StringUtils._
class TextWrapper(val defaultLimit: Int = 40) {
  /**
   * Formats text to ensure that lines do not exceed the specified character limit.
   * Words are not broken across lines. If a single word exceeds the character limit, an error is returned.
   *
   * @param text  The input text to be wrapped.
   * @param limit The maximum number of characters allowed per line.
   * @return Either an error message (Left) if a word exceeds the limit or the wrapped text (Right).
   */
  def wrapText(text: String, limit: Int = defaultLimit): Either[String, String] = {

    // 1. Split the text into words
    val words: List[String] = text.words

    // 2. Create lines
    val emptyLine: List[String] = List.empty
    val emptyText: List[List[String]] = List.empty
    val emptyLineWordCount: Int = 0 // the size of the empty line

    val result = words.foldM((
      emptyLine,
      emptyText,
      emptyLineWordCount
    )) {
      case ((currentLine, allLines, currentLength), word) =>
        // note: currentLength = currentLine.reverse.mkString(" ").size
        // i.e. the length of the string (line) represented by currentLine

        if (word.length > limit) {
          // current word itself exceeds the character limit [ERROR]
          s"Word '$word' exceeds the limit.".asLeft
        } else {
          // current word is smaller than the limit, no error
          // construct the string we will attempt to add to the current line
          val str = if(currentLine.isEmpty) word else s" $word"

          if (currentLength + str.length <= limit) {
            // the word fits the line
            // - introduce the word in the current line (list of words) as the head
            //   * the line is being built with the words in reverse order
            // - list of constructed lines is unchanged
            // - current size is updated to the size of the line represented by str :: currentLine
            (str :: currentLine, allLines, currentLength + str.length).asRight
          }
          else {
            // the word does not fit the line
            // - create a new line with the new word
            // - introduce the current line in the list of lines
            //   * the line (list of words) was created in reverse so we re-reverse it
            //   * the line is inserted as the head, so the list of lines is bing built in reverse
            (List(word), currentLine.reverse :: allLines, word.length).asRight
          }
        }
    }

    // 3. Convert to single string
    result.map { case (lastLine, allLines, _) =>
      // - the last line needs to be added to the list
      // - the lines are in reverse order, need to re-reverse
      // - make each line and make the final string with all the lines
      (lastLine.reverse :: allLines).reverse.map(_.mkString("")).mkString("\n")
    }
  }
}
