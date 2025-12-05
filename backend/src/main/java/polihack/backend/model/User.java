package polihack.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;
}
