package com.sid.portal_web.core;

import com.sid.portal_web.entity.activity.ActivityEntity.Priority;
import com.sid.portal_web.entity.activity.ActivityEntity.Status;

import java.time.LocalDate;
import java.util.Objects;

public record Activities(
        Integer activityId,
        String title,
        String description,
        Priority priority,
        Status status,
        Integer totalHours,
        LocalDate startDate,
        LocalDate completionDate,
        String manager

)
    {


        public Activities {
            // Validaciones básicas de campos obligatorios
            Objects.requireNonNull(title, "Title cannot be null");
            Objects.requireNonNull(priority, "Priority cannot be null");
            Objects.requireNonNull(status, "Status cannot be null");
            Objects.requireNonNull(manager, "Manager cannot be null");
            Objects.requireNonNull(description, "Description cannot be null");

            // Validaciones de cadenas no vacías
            if (title.isBlank()) {
                throw new IllegalArgumentException("Title cannot be empty or blank");
            }

            if (manager.isBlank()) {
                throw new IllegalArgumentException("Manager cannot be empty or blank");
            }

            // Validaciones de números positivos
            if (totalHours != null && totalHours <= 0) {
                throw new IllegalArgumentException("Total hours must be positive");
            }

            // Validaciones de fechas
            if (startDate != null && completionDate != null && startDate.isAfter(completionDate)) {
                throw new IllegalArgumentException("Start date must be before or equal to completion date");
            }

            // Validación de estado coherente con fechas
            if (status == Status.COMPLETADA && completionDate == null) {
                throw new IllegalArgumentException("Completed activities must have a completion date");
            }

            // Validación de descripción (opcional pero con límite)
            if (description.length() > 50) {
                throw new IllegalArgumentException("Description cannot exceed 50 characters");
            }

            if (title.length() > 50) {
                throw new IllegalArgumentException("Title cannot exceed 50 characters");
            }

        }
}
