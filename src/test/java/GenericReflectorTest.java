import com.company.util.GenericReflector;
import org.junit.Assert;
import org.junit.Test;

public class GenericReflectorTest {

    @Test
    public void testGetSuperclassParameterType() {
        Assert.assertEquals(Param.class, GenericReflector.getSuperclassParameterType(GenericChild.class));
    }

    private class Param {
    }

    private class Generic<T> {
    }

    private class GenericChild extends Generic<Param> {
    }
}
