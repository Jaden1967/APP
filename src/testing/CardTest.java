package testing;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import entities.Card;
import ui.labels.CardView;

public class CardTest {
	Card card = new Card(type);
	static String type;
	private ImageIcon icon;
	private CardView cv = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 type = "Infantry";

		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		type = "Infantry";
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testLoadImage() {
		assertTrue(card.loadImage(type));
	}

	@Test
	public void testGetType() {
		assertEquals("Infantry",card.getType());
	}

	@Test
	public void testGetImage() {
		card.loadImage(type);
		ImageIcon ico = new ImageIcon("source\\Infantry.jpg"); 
		assertEquals(ico.getIconHeight(),card.getImage().getIconHeight());	
	}

	@Test
	public void testGetCardView() {
//		card.getCardView();
		assertEquals(0,card.getCardView().getCount());
		
	}

	@Ignore
	public void testChangeView() {
		fail("Not yet implemented");
	}

}
