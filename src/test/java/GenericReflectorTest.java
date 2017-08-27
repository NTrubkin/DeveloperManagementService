import com.company.util.GenericReflector;
import org.junit.Assert;
import org.junit.Test;

public class GenericReflectorTest {

    private class Param {}

    private class Generic<T>{}

    private class GenericChild extends Generic<Param> {}

    @Test
    public void testGetSuperclassParameterType() {
        Assert.assertEquals(Param.class, GenericReflector.getSuperclassParameterType(GenericChild.class));
    }
}
