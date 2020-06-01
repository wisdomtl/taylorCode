package test.taylor.com.taylorcode.util

inline val String?.value: String
    get() {
        return this ?: ""
    }

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
