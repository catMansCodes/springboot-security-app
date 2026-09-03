package com.catmanscodes.securityapp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
public class HomeController {

    @GetMapping
    public String home() {
        return "Home sweet Home";
    }

    // FOR ADMIN Only

    @DeleteMapping("/delete/{id}")
    public String deleteRoomById(@PathVariable Integer id) {
        return "Only admin can delete room - Room deleted successfully";
    }

    //FOR STAFF & Admin

    @GetMapping("/view")
    public String viewRoom() {
        return "Admin & staff can access - Room view";
    }

    // FOR GUEST ROLE
    @GetMapping("/view/{id}")
    public String viewRoomByGuestId(@PathVariable String id) {
        return "Admin , staff & Guest can access - Room view";
    }


}
