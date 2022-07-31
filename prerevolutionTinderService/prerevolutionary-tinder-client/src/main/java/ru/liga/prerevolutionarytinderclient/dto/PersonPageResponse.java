package ru.liga.prerevolutionarytinderclient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class PersonPageResponse extends PageImpl<PersonRequest> {


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PersonPageResponse(@JsonProperty("content") List<PersonRequest> personRequest,
                              @JsonProperty("number") int number,
                              @JsonProperty("size") int size,
                              @JsonProperty("totalElements") Long totalElements) {
        super(personRequest, PageRequest.of(number, size), totalElements);
    }

    public PersonPageResponse(Page<PersonRequest> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }
}

