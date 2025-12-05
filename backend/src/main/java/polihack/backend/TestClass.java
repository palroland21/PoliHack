package polihack.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "TEST")
public class TestClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    @Column(name="HELLO")
    private String hello;
}
