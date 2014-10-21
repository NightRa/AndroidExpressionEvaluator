package nightra.tests.test;

public class Test {
    public final String description;
    public final boolean success;

    public Test(boolean success, String description) {
        this.description = description;
        this.success = success;
    }

    public Tests lift() {
        return new Tests(this);
    }

    public static Test test(boolean success, String description) {
        return new Test(success, description);
    }

    public static <A> Test equals(A actual, A expected, String description) {
        if (actual.equals(expected))
            return new Test(true, description);
        else
            return new Test(false, description + "; expected: " + expected + ", actual: " + actual);
    }

}
