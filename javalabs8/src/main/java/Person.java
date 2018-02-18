public class Person implements Comparable<Person> {

    private String givenName;
    private String lastName;
    private int age;

    public Person() {
    }

    public Person(String givenName, String lastName, int age) {
        this.givenName = givenName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return givenName + ' ' + lastName + ", " + age;
    }

    @Override
    public int compareTo(Person person) {
        return givenName.concat(lastName).compareTo(person.givenName.concat(person.lastName));
    }
}
