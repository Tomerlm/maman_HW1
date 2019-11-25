package olympic;

import olympic.business.ReturnValue;
import olympic.business.Sport;
import org.junit.Test;

import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;

public class SportsBasicTest extends AbstractTest {
    @Test
    public void simpleTestCreateSport()
    {
        Sport a = new Sport();
        a.setId(1);
        a.setName("Baseball");
        a.setCity("Haifa");
        a.setAthletesCount(0);
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK, ret);
    }

    @Test
    public void testDeleteSportNotExist(){
        Sport a = new Sport();
        a.setId(1);
        a.setName("Baseball");
        a.setCity("Haifa");
        a.setAthletesCount(0);

        ReturnValue ret = Solution.deleteSport(a);
        assertEquals(NOT_EXISTS , ret);

    }

    @Test
    public void testDeleteSport(){
        Sport a = new Sport();
        a.setId(1);
        a.setName("Baseball");
        a.setCity("Haifa");
        a.setAthletesCount(0);
        ReturnValue ret = Solution.addSport(a);
        ret = Solution.deleteSport(a);
        assertEquals(OK , ret);

    }

    @Test
    public void simpleTestCreateAthleteTwice() {
        Sport a = new Sport();
        a.setId(1);
        a.setName("Baseball");
        a.setCity("Haifa");
        a.setAthletesCount(0);
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK, ret);
        ret = Solution.addSport(a);
        assertEquals(ALREADY_EXISTS, ret);
    }

    @Test
    public void getSportTest(){
        Sport a = new Sport();
        a.setId(1);
        a.setName("Baseball");
        a.setCity("Haifa");
        a.setAthletesCount(0);
        ReturnValue ret = Solution.addSport(a);
        assertEquals(OK , ret);
        Sport b = Solution.getSport(1);
        assertEquals(b.getId() , a.getId());
        assertEquals(b.getName() , a.getName());
    }
}