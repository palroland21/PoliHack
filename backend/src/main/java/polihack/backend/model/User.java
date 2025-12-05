package polihack.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import static org.postgresql.jdbc.EscapedFunctions.USER;

@Entity
@Table(name= USER)
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;
}
