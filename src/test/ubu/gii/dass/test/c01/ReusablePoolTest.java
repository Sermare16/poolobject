/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Sergio Martín Reizábal
 * @author Javier Pampliega Garcia
 *
 */
public class ReusablePoolTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private ReusablePool pool;
	private ReusablePool pool2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
		pool2 = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool = ReusablePool.getInstance();

		assertNotNull(pool);

		assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */

	// ...

	@Test
	public void testAcquireReusable() {
		ReusablePool pool = ReusablePool.getInstance();
		try {
			Reusable reusable = pool.acquireReusable();
			assertNotNull(reusable);
		} catch(NotFreeInstanceException e) {
			fail("No debería lanzar una excepción");
		}
	}
	
	@Test
    public void testAcquireReusableWhenNoInstancesAvailableThrowsException() throws NotFreeInstanceException {
        // Configuramos la regla para esperar una excepción específica
        thrown.expect(NotFreeInstanceException.class);
        thrown.expectMessage("No hay más instancias reutilizables disponibles. Reintentalo más tarde");

        // Intentamos adquirir dos instancias
        Reusable reusable1 = pool.acquireReusable();
        Reusable reusable2 = pool.acquireReusable();

        assertNotNull(reusable1);
        assertNotNull(reusable2);
        
        // Intentamos adquirir una tercera instancia (debería lanzar una excepción)
        Reusable reusable3 = pool.acquireReusable();
    }

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() throws DuplicatedInstanceException{
		
		try {
			Reusable reusablePool2 = pool2.acquireReusable();
			pool.releaseReusable(reusablePool2);
			assertTrue(pool.getVector().contains(reusablePool2));
		} catch (NotFreeInstanceException ex) {
			fail("No deberia de lanzarse esta excepcion");
		}	
	}
}