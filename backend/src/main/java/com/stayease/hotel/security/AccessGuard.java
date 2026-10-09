package com.stayease.hotel.security;

import com.stayease.hotel.repo.BookingRepository;
import com.stayease.hotel.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** Ownership checks used in @PreAuthorize expressions, e.g. @accessGuard.isSelf(#userId). */
@Component("accessGuard")
public class AccessGuard {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public AccessGuard(UserRepository userRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public boolean isSelf(Long userId) {
        String email = currentEmail();
        return email != null && userId != null &&
                userRepository.findById(userId).map(u -> u.getEmail().equals(email)).orElse(false);
    }

    public boolean isSelf(String userId) {
        try {
            return isSelf(Long.valueOf(userId));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean ownsBooking(Long bookingId) {
        String email = currentEmail();
        return email != null && bookingRepository.findById(bookingId)
                .map(b -> b.getUser() != null && email.equals(b.getUser().getEmail()))
                .orElse(false);
    }

    private String currentEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth == null || !auth.isAuthenticated()) ? null : auth.getName();
    }
}
