package fr.olprog_c.le_phare_culturel.api.models;

public record Location(
    String address,
    String city,
    double latitude,
    String name,
    double longitude) {
}
