package lab;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

class ArchitectureTest {
    private static final JavaClasses ORDERS = new ClassFileImporter().importPackages("lab.orders");
    private static final JavaClasses BAD = new ClassFileImporter().importPackages("lab.bad");

    private static ArchRule layers(String root) {
        return Architectures.layeredArchitecture().consideringOnlyDependenciesInLayers()
                .withOptionalLayers(true)
                .layer("Web").definedBy(root + ".web..")
                .layer("Service").definedBy(root + ".service..")
                .layer("Repo").definedBy(root + ".repo..")
                .whereLayer("Web").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Web")
                .whereLayer("Repo").mayOnlyBeAccessedByLayers("Service");
    }

    private static ArchRule noCycles(String root) {
        return slices().matching(root + ".(*)..").should().beFreeOfCycles();
    }

    @Test
    void ordersRespectLayers() {
        layers("lab.orders").check(ORDERS);
    }

    @Test
    void ordersHaveNoCycles() {
        noCycles("lab.orders").check(ORDERS);
    }

    @Test
    void rulesFailOnTheBadSample() {
        assertThrows(AssertionError.class, () -> layers("lab.bad").check(BAD));
        assertThrows(AssertionError.class, () -> noCycles("lab.bad").check(BAD));
    }
}
