package com.weddingplanner.weddingplanner.controller;

import com.weddingplanner.weddingplanner.model.Admin;
import com.weddingplanner.weddingplanner.model.Booking;
import com.weddingplanner.weddingplanner.model.Vendor;
import com.weddingplanner.weddingplanner.service.BookingService;
import com.weddingplanner.weddingplanner.service.UserService;
import com.weddingplanner.weddingplanner.service.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.weddingplanner.weddingplanner.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VendorService vendorService;
    @Autowired
    private UserService userService;


    private boolean isAdmin(HttpSession session) {
        Object user = session.getAttribute("user");
        return user instanceof Admin;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("vendors", vendorService.getAllVendors());
        model.addAttribute("users", userService.getAllUsers());
        return "admin_dashboard";
    }

    @GetMapping("/users/add-admin")
    public String addAdminPage(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("admin", new Admin());
        return "add_admin";
    }

    @PostMapping("/users/add-admin")
    public String addAdmin(@ModelAttribute Admin admin, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        userService.saveUser(admin);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/bookings/update")
    public String updateBooking(@RequestParam Long bookingId, @RequestParam String status, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.updateBookingStatus(bookingId, status);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/vendors/add")
    public String addVendorPage(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("vendor", new Vendor());
        return "add_vendor";
    }

    @PostMapping("/vendors/add")
    public String addVendor(@ModelAttribute Vendor vendor, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        vendorService.saveVendor(vendor);
        return "redirect:/admin/dashboard";
    }
}
