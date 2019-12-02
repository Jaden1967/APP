package testing;
import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import entities.Continent;
import entities.Country;
import entities.GamePlay;
import entities.Player;
import ui.labels.CountryObsLabel;

public class CountryTest {
	Country country = new Country();
	Player pla = new Player();
	private String countryName;
	private Player owner = pla;
	private int countryId;
	private int armyNum = 0;
	private Continent belong_to_continent;
	private int x;
	private int y;
//	private CountryObsLabel label;
	private Border border;
	Vector <Country> neighbor_countries = new Vector<Country>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		owner = pla;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		countryId = 1;
		countryName = "Korean";
		x = 20;
		y = 20;
		this.owner = new Player();
		neighbor_countries.add(null);
		
//		label = new CountryObsLabel(String.valueOf(armyNum),countryName);
////		x = (int)((float)plotX/imageX*x);
////		y = (int)((float)plotY/imageY*y);
//		label.setBounds(x-15, y-15, 30, 30);
//		label.setFont(new Font("SimSun", Font.BOLD, 15));
//		label.setHorizontalAlignment(SwingConstants.CENTER);
//		this.addObserver(label);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	public void testCountry() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOwner() {
		assertEquals(owner.getID(),country.getOwner().getID());
	}

	@Ignore
	public void testSetOwner() {
		
		GamePlay play = new GamePlay();
		Color co = new Color(2);
		Player plann = new Player("newone",co,play);
//		System.out.print(plann.getID());
//		System.out.print(country.getOwner().getColor());
		country.setOwner(plann);
		assertEquals(plann.getID(),country.getOwner().getID());
	}

	@Test
	public void testAddNeighbour() {
		Country neighbour1 = new Country();
		country.addNeighbour(neighbour1);
		assertEquals(neighbour1.getID(),country.getNeighbors().firstElement().getID());
	}

	@Test
	public void testHasNeighbour() {
		assertFalse(country.hasNeighbour("Korean"));
	}

	@Test
	public void testHasEnemyNeighbour() {
		assertFalse(country.hasEnemyNeighbour());
	}

	@Ignore
	public void testGetOneEnemyNeighbor() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testHasPathTo() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetLinkCountries() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testAddArmy() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testRemoveArmy() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testMoveArmy() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetID() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetArmyNum() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testSetArmy() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetContinent() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testSetContinent() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetNeighbors() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetLabel() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPrintLinkedCountries() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testPrintLinkedCountriesNum() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testGetXY() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testAlertObservers() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testObservable() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testAddObserver() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testDeleteObserver() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testNotifyObservers() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testNotifyObserversObject() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testDeleteObservers() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testSetChanged() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testClearChanged() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testHasChanged() {
		fail("Not yet implemented");
	}

	@Ignore
	public void testCountObservers() {
		fail("Not yet implemented");
	}

}
