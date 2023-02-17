package com.movieapp.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequiredResponse {

    private User user;
    private Link link;
    private Movie movie;

}
