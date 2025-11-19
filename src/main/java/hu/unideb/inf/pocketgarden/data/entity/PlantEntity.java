package hu.unideb.inf.pocketgarden.data.entity;

import hu.unideb.inf.pocketgarden.enums.SunlightRequirement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "plants")
public class PlantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(unique = true)
    private String nickname;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "watering_frequency", nullable = false)
    private Integer wateringFrequency;

    @Column(name = "sunlight_requirement", nullable = false)
    @Enumerated(EnumType.STRING)
    private SunlightRequirement sunlightRequirement;

    @Column(name = "last_watered_at", nullable = false)
    private LocalDate lastWateredAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;
}
