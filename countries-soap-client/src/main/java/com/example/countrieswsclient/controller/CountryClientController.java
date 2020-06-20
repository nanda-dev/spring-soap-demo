package com.example.countrieswsclient.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.countrieswsclient.config.CountryClient;
import com.example.countrieswsclient.wsdl.GetCountryResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryClientController {
  private final CountryClient countryClient;

  @PostMapping("/get-details")
  public GetCountryResponse getCountryDetails(@RequestBody String countryName) {
    return countryClient.getCountry(countryName);
  }
}
