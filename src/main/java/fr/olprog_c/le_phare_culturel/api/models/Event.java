
package fr.olprog_c.le_phare_culturel.api.models;

public record Event(
    Image image,
    boolean featured,
    long attendanceMode,
    Keywords keywords,
    DateRange dateRange,
    String imageCredits,
    OriginAgenda originAgenda,
    Description description,
    Object[] label,
    Description title,
    String tarifs,
    Object onlineAccessLink,
    long uid,
    long etiquette,
    TTiming lastTiming,
    TTiming firstTiming,
    Location location,
    long typeDevenement,
    String slug,
    long status,
    TTiming nextTiming) {
}

/*
 * status
 *
 * Programmé (1): État par défaut. L'événement est programmé aux horaires
 * indiqués
 * Reprogrammé (2): Les horaires ont changé
 * Déplacé en ligne (3): L'événement qui se déroulait à un lieu physique n'est
 * désormais accessible qu'en ligne
 * Reporté (4): L'événement ne se déroule plus aux horaires indiqués, les
 * nouveaux horaires ne sont pas encore disponibles
 * Complet (5): L'événement n'est plus accessible aux nouveaux participants
 * Annulé (6): L'événement n'est plus programmé aux horaires indiqués et n'est
 * pas reporté.
 */

/*
 * attendanceMode
 *
 * 1 (offline): Participation physique au lieu où se déroule l'événement
 * 2 (online): Participation en ligne via un lien
 * 3 (mixed): Participation mixte
 */
