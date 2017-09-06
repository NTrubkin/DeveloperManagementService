import com.company.domain.ProjectDomain;
import com.company.domain.SecureAccountDomain;
import com.company.entity.Account;
import com.company.entity.Project;
import org.junit.Assert;
import org.junit.Test;

public class DomainTest {
    @Test
    public void testConversionNullAccountToSecureAccountDomain() {
        Account entity = null;
        SecureAccountDomain domain = new SecureAccountDomain(entity);
        Assert.assertTrue(domain.getId() == 0);
    }

    @Test
    public void testConversionNullProjectToProjectDomain() {
        Project entity = null;
        ProjectDomain domain = new ProjectDomain(entity);
        Assert.assertTrue(domain.getId() == 0);
    }
}
