import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class doesn't contain real tests, all information is for visual manual analyzes only.
 */
public class PersonTest {

    private List<Person> personList = new ArrayList<>();

    @Before
    public void initialize() {
        personList.add(new Person("John", "Smith", 20));
        personList.add(new Person("John", "Doe", 38));
        personList.add(new Person("Jane", "Doe", 22));
        personList.add(new Person("Mary", "Poppins", 53));
        personList.add(new Person("Aby", "Zingler", 21));
    }

    @Test
    public void testNaturalSort() {
        personList = personList.stream().sorted().collect(Collectors.toList());
        personList.forEach(System.out::println);
    }

    @Test
    public void testSortingByGivenName() {
        Comparator<Person> comparator = Comparator.comparing(Person::getGivenName);
        personList = personList.stream().sorted(comparator).collect(Collectors.toList());
        personList.forEach(System.out::println);
    }

    @Test
    public void testSortingByLastName() {
        Comparator<Person> comparator = Comparator.comparing(Person::getLastName);
        personList = personList.stream().sorted(comparator).collect(Collectors.toList());
        personList.forEach(System.out::println);
    }

    @Test
    public void testSortingByAge() {
        Comparator<Person> comparator = Comparator.comparingInt(Person::getAge);
        personList = personList.stream().sorted(comparator).collect(Collectors.toList());
        personList.forEach(System.out::println);
    }
}
