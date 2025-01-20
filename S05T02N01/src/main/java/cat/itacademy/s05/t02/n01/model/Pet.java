package cat.itacademy.s05.t02.n01.model;

import cat.itacademy.s05.t02.n01.dto.request.CreatePetRequest;
import cat.itacademy.s05.t02.n01.enums.Accessories;
import cat.itacademy.s05.t02.n01.enums.Locations;
import cat.itacademy.s05.t02.n01.enums.PetColor;
import cat.itacademy.s05.t02.n01.enums.PetType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet")
public class Pet {
    private static final int HAPPINESS_DECREASE = 20;
    private static final int HUNGER_INCREASE = 10;
    private static final int HOUR_STATUS_CHANGE = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_type", nullable = false)
    private PetType petType;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_color", nullable = false)
    private PetColor petColor;

    @Column(nullable = false)
    private int happiness;

    @Column(nullable = false)
    private int hunger;

    @Column(name = "is_asleep", nullable = false)
    private boolean isAsleep;

    @Column(name = "last_interaction")
    private LocalDateTime lastInteraction;

    @Column(name = "location")
    private Locations location;

    @Column(name = "accessories")
    private Accessories accessory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private static final int MAX_STATUS = 100;
    private static final int MIN_STATUS = 0;


    public void setHappiness(int happiness) {
        this.happiness = Math.max(MIN_STATUS, Math.min(MAX_STATUS, happiness));
    }

    public void setHunger(int hunger) {
        this.hunger = Math.max(MIN_STATUS, Math.min(MAX_STATUS, hunger));
    }

    public void hourlyStatusChanges() {
        if (this.getLastInteraction() != null) {
            long hoursSinceLastInteraction = ChronoUnit.HOURS.between(this.getLastInteraction(), LocalDateTime.now());

            int happinessChange = HAPPINESS_DECREASE * (int) (hoursSinceLastInteraction / HOUR_STATUS_CHANGE);
            int hungerChange = HUNGER_INCREASE * (int) (hoursSinceLastInteraction / HOUR_STATUS_CHANGE);

            this.setHappiness(this.getHappiness() - happinessChange);
            this.setHunger(this.getHunger() + hungerChange);

            if (hoursSinceLastInteraction >= 24) {
                this.setAsleep(true);
            }
        }
    }

}
