import org.junit.Test;

import play.mvc.Http.Response;
import play.test.FunctionalTest;
import javax.persistence.TransactionRequiredException;

public class TransactionalJPATest extends FunctionalTest {
    
    @Test(expected = TransactionRequiredException.class)
    public void testImport() {
        Response response = GET("/Transactional/readOnlyTest");
    }
    
    @Test
    public void testDisableTransaction() {
	
        //make sure isInsideTransaction returns true when actually inside a transaction
        Response response = GET("/Transactional/verifyIsInsideTransaction");
        assertIsOk(response);
        assertEquals("isInsideTransaction: true", getContent(response));
	
        //verify that we can make the controller run without any transaction at all
        response = GET("/Transactional/disabledTransactionTest");
        assertIsOk(response);
        assertEquals("isInsideTransaction: false", getContent(response));
        
        //verify that a method does not use a tranaction if the controller-class
        //is annotated with @NoTransaction
        response = GET("/Transactional2/disabledTransactionTest");
        assertIsOk(response);
        assertEquals("isInsideTransaction: false", getContent(response));
        
    }

}

