package ru.liga.prerevolutionarytinderserver.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.prerevolutionarytinderserver.api.PersonService;
import ru.liga.prerevolutionarytinderserver.dto.PersonDto;
import ru.liga.prerevolutionarytinderserver.entity.Person;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private static final int PAGE_SIZE = 1;
    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") Long id) {
        log.info("Was calling getPerson. Input id: {}", id);
        return ResponseEntity.ok(personService.getPersonById(id));
    }

    @GetMapping("/form/{id}")
    public ResponseEntity<PersonDto> getPersonForm(@PathVariable("id") Long id) {
        log.info("Was calling getPersonForm. Input id: {}", id);
        return ResponseEntity.ok(personService.getPersonFormById(id));
    }

    @PostMapping()
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        log.info("Was calling createPerson. Input person: {}", person);
        return ResponseEntity.ok(personService.addNewPerson(person));
    }

    @GetMapping("/favorites/{id}/pages/{page}")
    public ResponseEntity<PersonDto> getPersonsLikedByMe(@PathVariable("id") Long id, @PathVariable("page") int page) {
        log.info("Was calling getPersonsLikedByMe. Input id: {} page: {}", id, page);
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        return ResponseEntity.ok(personService.getMyLikePersonsList(id, pageRequest));
    }

    @GetMapping("/{id}/search/pages/{page}")
    public ResponseEntity<PersonDto> getCandidateFavorites(@PathVariable("id") Long id, @PathVariable("page") int page) {
        log.info("Was calling getPersons. Input id: {} page: {}", id, page);
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        return ResponseEntity.ok(personService.getCandidateFavorites(id, pageRequest));
    }

    @PostMapping("/{id}/search/")
    public ResponseEntity<String> createLikeFavorites(@PathVariable("id") Long id, @RequestBody Long currentPersonId) {
        log.info("Was calling createLikeFavorites. Input userId: {} currentPersonId: {}", id, currentPersonId);
        return ResponseEntity.ok(personService.addLikeFavorites(id, currentPersonId));
    }
}
