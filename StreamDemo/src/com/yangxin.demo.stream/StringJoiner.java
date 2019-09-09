

public final class StringJoiner {
    /**
     * prefix：表示字符串拼接的前缀
     * suffix：表示字符串拼接的结尾
     * delimiter： 表示分割符
     * */
    private final String prefix;
    private final String delimiter;
    private final String suffix;

    /*
     * StringBuilder的值。构造器从prefix开始添加元素，delimiter分割，但是没有
     * 结尾符suffix，那么我们每次会更容易的去拼接字符串
     */
    private StringBuilder value;

    /*
     * 默认情况，由prefix和suffix拼接的字符串，在返回值的时候使用toString转换。
     * 当没有元素添加的时候，那么这个为空，这很有可能被用户去覆盖一些其他值，包括空串
     */
    private String emptyValue;

    /**
     * 构造器只有delimiter分隔符，prefix和suffix将默认为空串，
     */
    public StringJoiner(CharSequence delimiter) {
        this(delimiter, "", "");
    }

    /**
     * 三参数的构造器
     */
    public StringJoiner(CharSequence delimiter,
                        CharSequence prefix,
                        CharSequence suffix) {
        Objects.requireNonNull(prefix, "The prefix must not be null");
        Objects.requireNonNull(delimiter, "The delimiter must not be null");
        Objects.requireNonNull(suffix, "The suffix must not be null");
        // make defensive copies of arguments
        this.prefix = prefix.toString();
        this.delimiter = delimiter.toString();
        this.suffix = suffix.toString();
        this.emptyValue = this.prefix + this.suffix;
    }

    /**
     * Sets the sequence of characters to be used when determining the string
     * representation of this {@code StringJoiner} and no elements have been
     * added yet, that is, when it is empty.  A copy of the {@code emptyValue}
     * parameter is made for this purpose. Note that once an add method has been
     * called, the {@code StringJoiner} is no longer considered empty, even if
     * the element(s) added correspond to the empty {@code String}.
     *
     * @param  emptyValue the characters to return as the value of an empty
     *         {@code StringJoiner}
     * @return this {@code StringJoiner} itself so the calls may be chained
     * @throws NullPointerException when the {@code emptyValue} parameter is
     *         {@code null}
     */
    public StringJoiner setEmptyValue(CharSequence emptyValue) {
        this.emptyValue = Objects.requireNonNull(emptyValue,
                "The empty value must not be null").toString();
        return this;
    }

    /**
     *
     * 重写的toString，字符串将是prefix开始，suffix结尾，除非没有添加任何元素，那
     * 么就返回空值
     */
    @Override
    public String toString() {
        if (value == null) {
            return emptyValue;
        } else {
            if (suffix.equals("")) {
                return value.toString();
            } else {
                int initialLength = value.length();
                String result = value.append(suffix).toString();
                // reset value to pre-append initialLength
                value.setLength(initialLength);
                return result;
            }
        }
    }

    /**
     * 添加一个拼接的串
     *
     * @param  newElement The element to add
     * @return a reference to this {@code StringJoiner}
     */
    public StringJoiner add(CharSequence newElement) {
        prepareBuilder().append(newElement);
        return this;
    }

    /**
     * 将拼接的字串合并
     */
    public StringJoiner merge(StringJoiner other) {
        Objects.requireNonNull(other);
        if (other.value != null) {
            final int length = other.value.length();
            // lock the length so that we can seize the data to be appended
            // before initiate copying to avoid interference, especially when
            // merge 'this'
            StringBuilder builder = prepareBuilder();
            builder.append(other.value, other.prefix.length(), length);
        }
        return this;
    }

    private StringBuilder prepareBuilder() {
        if (value != null) {
            value.append(delimiter);
        } else {
            value = new StringBuilder().append(prefix);
        }
        return value;
    }

    /**
     * 返回长度
     */
    public int length() {
        // Remember that we never actually append the suffix unless we return
        // the full (present) value or some sub-string or length of it, so that
        // we can add on more if we need to.
        return (value != null ? value.length() + suffix.length() :
                emptyValue.length());
    }
}