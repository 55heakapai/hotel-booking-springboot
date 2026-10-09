package com.stayease.hotel.service;

import com.stayease.hotel.dto.Response;
import com.stayease.hotel.entity.Booking;
import com.stayease.hotel.entity.Room;
import com.stayease.hotel.entity.User;
import com.stayease.hotel.repo.BookingRepository;
import com.stayease.hotel.repo.RoomRepository;
import com.stayease.hotel.repo.UserRepository;
import com.stayease.hotel.service.impl.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock private BookingRepository bookingRepository;
    @Mock private RoomRepository roomRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private BookingService bookingService;

    private Booking request(LocalDate in, LocalDate out) {
        Booking b = new Booking();
        b.setCheckInDate(in);
        b.setCheckOutDate(out);
        b.setNumOfAdults(2);
        b.setNumOfChildren(1);
        return b;
    }

    @Test
    void savesBookingWhenRoomIsFree() {
        LocalDate in = LocalDate.now().plusDays(5);
        LocalDate out = in.plusDays(2);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room()));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(bookingRepository.existsOverlappingBooking(1L, in, out)).thenReturn(false);

        Response response = bookingService.saveBooking(1L, 2L, request(in, out));

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBookingConfirmationCode());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void rejectsOverlappingBookingWith409() {
        LocalDate in = LocalDate.now().plusDays(5);
        LocalDate out = in.plusDays(2);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room()));
        when(userRepository.findById(2L)).thenReturn(Optional.of(new User()));
        when(bookingRepository.existsOverlappingBooking(1L, in, out)).thenReturn(true);

        Response response = bookingService.saveBooking(1L, 2L, request(in, out));

        assertEquals(409, response.getStatusCode());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void rejectsCheckOutNotAfterCheckIn() {
        LocalDate in = LocalDate.now().plusDays(5);

        Response response = bookingService.saveBooking(1L, 2L, request(in, in));

        assertEquals(400, response.getStatusCode());
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
