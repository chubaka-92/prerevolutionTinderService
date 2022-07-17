package ru.liga.prerevolutionarytinderserver.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.entity.Person;
import ru.liga.prerevolutionarytinderserver.services.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity getPerson(@PathVariable("id") Long id) {
        return personService.getPersonById(Long.valueOf(id));
    }
    @PostMapping()
    public ResponseEntity createPerson(@RequestBody Person person) {
        return personService.addNewPerson(person);
    }

}
