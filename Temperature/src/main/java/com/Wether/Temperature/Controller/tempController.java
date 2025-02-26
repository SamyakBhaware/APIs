package com.Wether.Temperature.Controller;

import com.Wether.Temperature.Service.TempService;
import com.Wether.Temperature.model.Temp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/temp")
public class tempController {

    @Autowired
    TempService tempService;

    // ✅ Save multiple temperature records
    @PostMapping("/saveAll")
    public ResponseEntity<?> SaveAll(@RequestBody List<Temp> temp) {
        try {
            List<Temp> savedTemps = tempService.SaveAll(temp);
            return ResponseEntity.ok(savedTemps);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save all data: " + e.getMessage());
        }
    }

    // ✅ Save a single temperature record
    @PostMapping("/save")
    public ResponseEntity<?> Save(@Valid @RequestBody Temp temp) {
        try {
            Temp savedTemp = tempService.Save(temp);
            return ResponseEntity.ok(savedTemp);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        }
    }

    // ✅ Get temperature by time
    @GetMapping("/time/{time}")
    public ResponseEntity<?> getTempByTime(@PathVariable int time) {
        try {
            List<Temp> temps = tempService.getTempByTime(time);
            if (temps.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No records found for time: " + time);
            }
            return ResponseEntity.ok(temps);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching data: " + e.getMessage());
        }
    }

    // ✅ Delete all temperature records
    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        try {
            tempService.deleteAll();
            return ResponseEntity.ok("All records deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting data: " + e.getMessage());
        }
    }

    // ✅ Delete a specific temperature record by ID
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            tempService.deleteById(id);
            return ResponseEntity.ok("Record with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting record: " + e.getMessage());
        }
    }
}
