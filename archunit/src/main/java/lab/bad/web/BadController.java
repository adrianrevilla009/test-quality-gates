package lab.bad.web;

import lab.bad.repo.BadRepository;

/** Deliberately wrong sample, see BadRepository. */
public class BadController {
    public String name() {
        return "bad";
    }

    public String viaRepo() {
        return new BadRepository().render();
    }
}
