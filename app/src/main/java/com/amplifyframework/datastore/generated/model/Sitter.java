package com.amplifyframework.datastore.generated.model;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/**
 * This is an auto generated class representing the Sitter type in your schema.
 */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Sitters")
public final class Sitter implements Model {
    public static final QueryField ID = field("id");
    public static final QueryField FIRST_NAME = field("firstName");
    public static final QueryField LAST_NAME = field("lastName");
    public static final QueryField EMAIL_ID = field("emailId");
    public static final QueryField PHONE_NUMBER = field("phoneNumber");
    public static final QueryField LOCATION = field("sitterLocationId");
    public static final QueryField DISPLAY_IMAGE = field("displayImage");
    public static final QueryField PAY_PER_DAY = field("payPerDay");
    private final @ModelField(targetType = "ID", isRequired = true)
    String id;
    private final @ModelField(targetType = "String", isRequired = true)
    String firstName;
    private final @ModelField(targetType = "String", isRequired = true)
    String lastName;
    private final @ModelField(targetType = "String", isRequired = true)
    String emailId;
    private final @ModelField(targetType = "String")
    String phoneNumber;
    private final @ModelField(targetType = "Location")
    @BelongsTo(targetName = "sitterLocationId", type = Location.class)
    Location location;
    private final @ModelField(targetType = "Appointment")
    @HasMany(associatedWith = "sitter", type = Appointment.class)
    List<Appointment> appointment = null;
    private final @ModelField(targetType = "String")
    String displayImage;
    private final @ModelField(targetType = "Float", isRequired = true)
    Float payPerDay;

    private Sitter(String id, String firstName, String lastName, String emailId, String phoneNumber, Location location, String displayImage, Float payPerDay) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.displayImage = displayImage;
        this.payPerDay = payPerDay;
    }

    public static FirstNameStep builder() {
        return new Builder();
    }

    /**
     * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
     * This is a convenience method to return an instance of the object with only its ID populated
     * to be used in the context of a parameter in a delete mutation or referencing a foreign key
     * in a relationship.
     *
     * @param id the id of the existing item this instance will represent
     * @return an instance of this model with only ID populated
     * @throws IllegalArgumentException Checks that ID is in the proper format
     **/
    public static Sitter justId(String id) {
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
            throw new IllegalArgumentException(
                    "Model IDs must be unique in the format of UUID. This method is for creating instances " +
                            "of an existing object with only its ID field for sending as a mutation parameter. When " +
                            "creating a new object, use the standard builder method and leave the ID field blank."
            );
        }
        return new Sitter(
                id,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Location getLocation() {
        return location;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public Float getPayPerDay() {
        return payPerDay;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            Sitter sitter = (Sitter) obj;
            return ObjectsCompat.equals(getId(), sitter.getId()) &&
                    ObjectsCompat.equals(getFirstName(), sitter.getFirstName()) &&
                    ObjectsCompat.equals(getLastName(), sitter.getLastName()) &&
                    ObjectsCompat.equals(getEmailId(), sitter.getEmailId()) &&
                    ObjectsCompat.equals(getPhoneNumber(), sitter.getPhoneNumber()) &&
                    ObjectsCompat.equals(getLocation(), sitter.getLocation()) &&
                    ObjectsCompat.equals(getDisplayImage(), sitter.getDisplayImage()) &&
                    ObjectsCompat.equals(getPayPerDay(), sitter.getPayPerDay());
        }
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(getId())
                .append(getFirstName())
                .append(getLastName())
                .append(getEmailId())
                .append(getPhoneNumber())
                .append(getLocation())
                .append(getDisplayImage())
                .append(getPayPerDay())
                .toString()
                .hashCode();
    }

    public CopyOfBuilder copyOfBuilder() {
        return new CopyOfBuilder(id,
                firstName,
                lastName,
                emailId,
                phoneNumber,
                location,
                displayImage,
                payPerDay);
    }

    public interface FirstNameStep {
        LastNameStep firstName(String firstName);
    }


    public interface LastNameStep {
        EmailIdStep lastName(String lastName);
    }


    public interface EmailIdStep {
        PayPerDayStep emailId(String emailId);
    }


    public interface PayPerDayStep {
        BuildStep payPerDay(Float payPerDay);
    }


    public interface BuildStep {
        Sitter build();

        BuildStep id(String id) throws IllegalArgumentException;

        BuildStep phoneNumber(String phoneNumber);

        BuildStep location(Location location);

        BuildStep displayImage(String displayImage);
    }


    public static class Builder implements FirstNameStep, LastNameStep, EmailIdStep, PayPerDayStep, BuildStep {
        private String id;
        private String firstName;
        private String lastName;
        private String emailId;
        private Float payPerDay;
        private String phoneNumber;
        private Location location;
        private String displayImage;

        @Override
        public Sitter build() {
            String id = this.id != null ? this.id : UUID.randomUUID().toString();

            return new Sitter(
                    id,
                    firstName,
                    lastName,
                    emailId,
                    phoneNumber,
                    location,
                    displayImage,
                    payPerDay);
        }

        @Override
        public LastNameStep firstName(String firstName) {
            Objects.requireNonNull(firstName);
            this.firstName = firstName;
            return this;
        }

        @Override
        public EmailIdStep lastName(String lastName) {
            Objects.requireNonNull(lastName);
            this.lastName = lastName;
            return this;
        }

        @Override
        public PayPerDayStep emailId(String emailId) {
            Objects.requireNonNull(emailId);
            this.emailId = emailId;
            return this;
        }

        @Override
        public BuildStep payPerDay(Float payPerDay) {
            Objects.requireNonNull(payPerDay);
            this.payPerDay = payPerDay;
            return this;
        }

        @Override
        public BuildStep phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        @Override
        public BuildStep location(Location location) {
            this.location = location;
            return this;
        }

        @Override
        public BuildStep displayImage(String displayImage) {
            this.displayImage = displayImage;
            return this;
        }

        /**
         * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
         * This should only be set when referring to an already existing object.
         *
         * @param id id
         * @return Current Builder instance, for fluent method chaining
         * @throws IllegalArgumentException Checks that ID is in the proper format
         **/
        public BuildStep id(String id) throws IllegalArgumentException {
            this.id = id;

            try {
                UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
            } catch (Exception exception) {
                throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                        exception);
            }

            return this;
        }
    }


    public final class CopyOfBuilder extends Builder {
        private CopyOfBuilder(String id, String firstName, String lastName, String emailId, String phoneNumber, Location location, String displayImage, Float payPerDay) {
            super.id(id);
            super.firstName(firstName)
                    .lastName(lastName)
                    .emailId(emailId)
                    .payPerDay(payPerDay)
                    .phoneNumber(phoneNumber)
                    .location(location)
                    .displayImage(displayImage);
        }

        @Override
        public CopyOfBuilder firstName(String firstName) {
            return (CopyOfBuilder) super.firstName(firstName);
        }

        @Override
        public CopyOfBuilder lastName(String lastName) {
            return (CopyOfBuilder) super.lastName(lastName);
        }

        @Override
        public CopyOfBuilder emailId(String emailId) {
            return (CopyOfBuilder) super.emailId(emailId);
        }

        @Override
        public CopyOfBuilder payPerDay(Float payPerDay) {
            return (CopyOfBuilder) super.payPerDay(payPerDay);
        }

        @Override
        public CopyOfBuilder phoneNumber(String phoneNumber) {
            return (CopyOfBuilder) super.phoneNumber(phoneNumber);
        }

        @Override
        public CopyOfBuilder location(Location location) {
            return (CopyOfBuilder) super.location(location);
        }

        @Override
        public CopyOfBuilder displayImage(String displayImage) {
            return (CopyOfBuilder) super.displayImage(displayImage);
        }
    }

}
