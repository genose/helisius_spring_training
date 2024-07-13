export interface EventInterface {
    uid:                 string;
    slug:                string;
    canonical_url:       string;
    title_fr:            string;
    description_fr:      string;
    description_long_fr: string;
    event_contact:       EventContact[];
    registration:        string;
    onlineaccess_link:   null;
    event_conditions_fr: EventConditionsFr[];
    attendancemode:      string;
    event_status:        string;
    event_accessibility: string[];
    event_keywords_fr:   string[];
    event_categories:    string[];
    event_images:        EventImages;
    event_date_info:     EventDateInfo;
    event_dates:         EventDates;
    location_place:      LocationPlace[];
    contributor:         Contributor;
    participants_groups: ParticipantsGroup[];
}

export interface Contributor {
    email:           null;
    contactnumber:   null;
    contactname:     null;
    contactposition: null;
    organization:    null;
}

export interface EventConditionsFr {
    age_min: null;
    age_max: null;
}

export interface EventContact {
    email: string;
    phone: string;
}

export interface EventDateInfo {
    creation_date:   Date;
    update_date:     Date;
    firstdate_begin: Date;
    firstdate_end:   Date;
    lastdate_begin:  Date;
    lastdate_end:    Date;
}

export interface EventDates {
    dates_details:          DatesDetails;
    dates_calendar_details: DatesCalendarDetail[];
}

export interface DatesCalendarDetail {
    Title:    string;
    WeekDays: WeekDay[];
}

export interface WeekDay {
    days_name?:   string;
    days_number?: number;
    price:        number;
}

export interface DatesDetails {
    price: number;
    begin: Date;
    end:   Date;
}

export interface EventImages {
    thumbnail:            any[];
    event_image_original: null;
    event_images_list:    EventImagesList[];
}

export interface EventImagesList {
    url:           string;
    image_credits: string;
}

export interface LocationPlace {
    location_uid:            string;
    location_coordinates:    LocationCoordinates;
    location_name:           string;
    location_address:        string;
    location_district:       null;
    location_insee:          null;
    location_postalcode:     string;
    location_city:           string;
    location_department:     string;
    location_region:         string;
    location_countrycode:    string;
    location_image:          null;
    location_imagecredits:   null;
    location_phone:          null;
    location_website:        null;
    location_links:          null;
    location_tags:           null;
    location_description_fr: null;
    location_access_fr:      null;
}

export interface LocationCoordinates {
    lon: number;
    lat: number;
}

export interface ParticipantsGroup {
    id:   number;
    name: string;
}
