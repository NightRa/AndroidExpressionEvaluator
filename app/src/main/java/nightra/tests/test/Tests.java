package nightra.tests.test;

import fj.data.List;

import static fj.Unit.unit;

public class Tests {
    public final List<Test> tests;

    public Tests(List<Test> tests) {
        this.tests = tests;
    }

    public Tests(final Test... ts) {
        tests = List.list(ts);
    }

    public Tests and(Test t) {
        return new Tests(tests.append(List.single(t)));
    }

    public Tests and(Tests other){
        return new Tests(tests.append(other.tests));
    }

    public void run() {
        List<Test> successes = tests.filter(t -> t.success);
        List<Test> failures = tests.filter(t -> !t.success);

        System.out.println("Failures: " + (failures.isEmpty() ? "None" : ""));
        failures.foreach(t -> {
            System.out.println("\t- " + t.description);
            return unit();
        });

        System.out.println("Successes: " + (successes.isEmpty() ? "None" : ""));
        successes.foreach(t -> {
            System.out.println("\t- " + t.description);
            return unit();
        });
    }
}
