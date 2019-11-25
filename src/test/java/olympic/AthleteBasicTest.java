package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import org.junit.Test;

import static olympic.business.ReturnValue.*;
import static olympic.business.ReturnValue.OK;
import static org.junit.Assert.assertEquals;

public class AthleteBasicTest extends AbstractTest {
    @Test
    public void simpleTestCreateAthlete()
    {
        Athlete a = new Athlete();
        a.setId(1);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
    }

    @Test
    public void testDeleteUserNotExist(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);

        ReturnValue ret = Solution.deleteAthlete(a);
        assertEquals(NOT_EXISTS , ret);

    }

    @Test
    public void testDeleteUser(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        ret = Solution.deleteAthlete(a);
        assertEquals(OK , ret);

    }

    @Test
    public void simpleTestCreateAthleteTwice() {
        Athlete a = new Athlete();
        a.setId(1);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        ret = Solution.addAthlete(a);
        assertEquals(ALREADY_EXISTS, ret);
    }

    @Test
    public void getAthleteTest(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK , ret);
        Athlete b = Solution.getAthleteProfile(10);
        assertEquals(b.getId() , a.getId());
        assertEquals(b.getName() , a.getName());
    }
}
