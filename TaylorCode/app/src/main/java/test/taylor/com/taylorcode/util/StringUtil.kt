package test.taylor.com.taylorcode.util

inline val String?.value: String
    get() {
        return this ?: ""
    }

/**
 * find the first digit sub-string of this CharSequence
 */
inline val CharSequence?.subDigit: CharSequence
    get() {
        var start = 0
        var offset = 0
        this?.forEachIndexed { index, c ->
            if (c.isDigit()) {
                if (offset == 0) {
                    start = index
                }
                offset++
            }
        }
        return this?.substring(start, start + offset) ?: ""
    }

/**
 * split CharSequence into several part, the separator is the digit in it
 */
fun CharSequence?.splitByDigit(): List<CharSequence> {
    if (this.isNullOrEmpty()) return emptyList()
    var start = 0
    var preIsDigit = this.first().isDigit()
    var subCharSequences = mutableListOf<CharSequence>()
    forEachIndexed { index, c ->
        if (preIsDigit != c.isDigit()) {
            subCharSequences.add(substring(start, index))
            start = index
        } else if (index == length - 1) {
            subCharSequences.add(substring(start, length))
        }
        preIsDigit = c.isDigit()
    }
    return subCharSequences
}
