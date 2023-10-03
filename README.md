# Text Wrapper

A Scala utility designed to format text, ensuring each line does not exceed a specified character limit.

## Features

- **Adherence to Limit:** Formats text into lines that strictly adhere to a specified character limit.
- **Word Integrity:** Ensures words are not split across lines.
- **Error Handling:** Returns an error if a single word exceeds the character limit.
- **Functional Approach:** Built using functional programming principles.

## Tests

- **Unit Tests:** Validates individual units of the code.
- **Property Tests:** Ensures properties hold for a variety of cases.

## Method

The utility first breaks the text into individual words. These words are then grouped together to form lines, ensuring the size of each line does not surpass the given limit.

### Efficiency

A straightforward approach would be to process each word sequentially, appending it to the end of the current line being processed:

```scala
val newLine = line :+ word
```

However, this operation takes linear time. Repeating it for every word would result in quadratic complexity. While this might be acceptable for small texts, we aim for linear complexity. The adopted method involves adding words at the head of the list, resulting in a reversed list, which is then re-reversed at the end of the procedure.

**Note:** For a more performance-oriented implementation, one could consider using `StringBuilder`. This introduces mutability but can be a reasonable trade-off depending on performance requirements.

### Error Handling

The result is wrapped in an `Either` to facilitate error handling. The implementation specifically checks for words longer than the line limit, returning an error in such cases.

## Assumptions

The utility assumes that:
- Leading and trailing spaces can be trimmed.
- Multiple whitespace characters can be consolidated into a single space.
