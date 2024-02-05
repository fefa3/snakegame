module de.ostfalie.group4 {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires io.fusionauth;
    requires spring.beans;
    requires spring.web;
    requires jakarta.persistence;
    requires spring.data.jpa;

    exports de.ostfalia.group4;
    opens de.ostfalia.group4;
}