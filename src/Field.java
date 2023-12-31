public enum Field {
    PROFILE("profile", 0),
    TEMPLATE("template", 1);
    Field(String str, int line) {
        field = str;
        this.line = line;
    }
    String field;
    int line;
    public String getField() {
        return this.field;
    }
    public int getLine() {
        return this.line;
    }
}
