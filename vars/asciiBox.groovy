def call(String str) {
    def topBottomRow = String.format("+%1\$-" + (str.length() + 2) + "s+", "").replaceAll(" ", "-")
    def middleRow = String.format("| %s |", str)
    def emptyRow = String.format("|%1\$-" + (str.length() + 2) + "s|", "")

    print("""
    ${topBottomRow}
    ${emptyRow}
    ${middleRow}
    ${emptyRow}
    ${topBottomRow}
    """
}