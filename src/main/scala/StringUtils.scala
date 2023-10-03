object StringUtils {
  implicit class StringOps(val s: String) {
    /**
     * breakdown a string to an array with its words
     * NOTE:
     *   the string is trimmed because leading whitespaces result in an empty string. E.g.,
     *   " a  b  ".split("\\s+").toList = List("", "a", "b")
     *   " a  b  ".trim.split("\\s+").toList = List("a", "b")
     * @return the string split at whitespace
     */
    def words: List[String] = s.trim.split("\\s+").toList
  }
}
