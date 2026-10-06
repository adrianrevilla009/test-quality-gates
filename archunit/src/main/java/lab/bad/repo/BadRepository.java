package lab.bad.repo;

import lab.bad.web.BadController;

/** Deliberately wrong sample: the repository reaches up into the web layer, forming a cycle. */
public class BadRepository {
    public String render() {
        return new BadController().name();
    }
}
