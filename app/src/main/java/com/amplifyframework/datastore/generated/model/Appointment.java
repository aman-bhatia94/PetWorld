package com.amplifyframework.datastore.generated.model;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import java.util.Objects;
import java.util.UUID;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/**
 * This is an auto generated class representing the Appointment type in your schema.
 */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Appointments")
public final class Appointment implements Model {
    public static final QueryField ID = field("id");
    public static final QueryField START_DATE = field("startDate");
    public static final QueryField END_DATE = field("endDate");
    public static final QueryField OWNER = field("appointmentOwnerId");
    public static final QueryField SITTER = field("appointmentSitterId");
    public static final QueryField TOTAL_AMOUNT = field("totalAmount");
    private final @ModelField(targetType = "ID", isRequired = true)
    String id;
    private final @ModelField(targetType = "String", isRequired = true)
    String startDate;
    private final @ModelField(targetType = "String", isRequired = true)
    String endDate;
    private final @ModelField(targetType = "Owner")
    @BelongsTo(targetName = "appointmentOwnerId", type = Owner.class)
    Owner owner;
    private final @ModelField(targetType = "Sitter")
    @BelongsTo(targetName = "appointmentSitterId", type = Sitter.class)
    Sitter sitter;
    private final @ModelField(targetType = "Float", isRequired = true)
    Float totalAmount;

    private Appointment(String id, String startDate, String endDate, Owner owner, Sitter sitter, Float totalAmount) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.sitter = sitter;
        this.totalAmount = totalAmount;
    }

    public static StartDateStep builder() {
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
    public static Appointment justId(String id) {
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
            throw new IllegalArgumentException(
                    "Model IDs must be unique in the format of UUID. This method is for creating instances " +
                            "of an existing object with only its ID field for sending as a mutation parameter. When " +
                            "creating a new object, use the standard builder method and leave the ID field blank."
            );
        }
        return new Appointment(
                id,
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public Sitter getSitter() {
        return sitter;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            Appointment appointment = (Appointment) obj;
            return ObjectsCompat.equals(getId(), appointment.getId()) &&
                    ObjectsCompat.equals(getStartDate(), appointment.getStartDate()) &&
                    ObjectsCompat.equals(getEndDate(), appointment.getEndDate()) &&
                    ObjectsCompat.equals(getOwner(), appointment.getOwner()) &&
                    ObjectsCompat.equals(getSitter(), appointment.getSitter()) &&
                    ObjectsCompat.equals(getTotalAmount(), appointment.getTotalAmount());
        }
    }

    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(getId())
                .append(getStartDate())
                .append(getEndDate())
                .append(getOwner())
                .append(getSitter())
                .append(getTotalAmount())
                .toString()
                .hashCode();
    }

    public CopyOfBuilder copyOfBuilder() {
        return new CopyOfBuilder(id,
                startDate,
                endDate,
                owner,
                sitter,
                totalAmount);
    }

    public interface StartDateStep {
        EndDateStep startDate(String startDate);
    }


    public interface EndDateStep {
        TotalAmountStep endDate(String endDate);
    }


    public interface TotalAmountStep {
        BuildStep totalAmount(Float totalAmount);
    }


    public interface BuildStep {
        Appointment build();

        BuildStep id(String id) throws IllegalArgumentException;

        BuildStep owner(Owner owner);

        BuildStep sitter(Sitter sitter);
    }


    public static class Builder implements StartDateStep, EndDateStep, TotalAmountStep, BuildStep {
        private String id;
        private String startDate;
        private String endDate;
        private Float totalAmount;
        private Owner owner;
        private Sitter sitter;

        @Override
        public Appointment build() {
            String id = this.id != null ? this.id : UUID.randomUUID().toString();

            return new Appointment(
                    id,
                    startDate,
                    endDate,
                    owner,
                    sitter,
                    totalAmount);
        }

        @Override
        public EndDateStep startDate(String startDate) {
            Objects.requireNonNull(startDate);
            this.startDate = startDate;
            return this;
        }

        @Override
        public TotalAmountStep endDate(String endDate) {
            Objects.requireNonNull(endDate);
            this.endDate = endDate;
            return this;
        }

        @Override
        public BuildStep totalAmount(Float totalAmount) {
            Objects.requireNonNull(totalAmount);
            this.totalAmount = totalAmount;
            return this;
        }

        @Override
        public BuildStep owner(Owner owner) {
            this.owner = owner;
            return this;
        }

        @Override
        public BuildStep sitter(Sitter sitter) {
            this.sitter = sitter;
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
        private CopyOfBuilder(String id, String startDate, String endDate, Owner owner, Sitter sitter, Float totalAmount) {
            super.id(id);
            super.startDate(startDate)
                    .endDate(endDate)
                    .totalAmount(totalAmount)
                    .owner(owner)
                    .sitter(sitter);
        }

        @Override
        public CopyOfBuilder startDate(String startDate) {
            return (CopyOfBuilder) super.startDate(startDate);
        }

        @Override
        public CopyOfBuilder endDate(String endDate) {
            return (CopyOfBuilder) super.endDate(endDate);
        }

        @Override
        public CopyOfBuilder totalAmount(Float totalAmount) {
            return (CopyOfBuilder) super.totalAmount(totalAmount);
        }

        @Override
        public CopyOfBuilder owner(Owner owner) {
            return (CopyOfBuilder) super.owner(owner);
        }

        @Override
        public CopyOfBuilder sitter(Sitter sitter) {
            return (CopyOfBuilder) super.sitter(sitter);
        }
    }

}
