package com.mms.product.controller;

import com.mms.product.model.Outfit;
import com.mms.product.model.response.outfit.CheapestBrandOutfitResponse;
import com.mms.product.model.response.outfit.OutfitResponse;
import com.mms.product.service.OutfitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "코디 API", description = "옷 코디와 관련된 API 목록입니다.")
@RestController
@RequestMapping("outfits")
@RequiredArgsConstructor
@Validated
public class OutfitController {

  private final OutfitService outfitService;

  @Operation(summary = "최저가 코디 조회", description = "최저가 코디를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공"),
  })
  @GetMapping("/cheapest")
  public ResponseEntity<OutfitResponse> getCheapestOutfit() {
    final Outfit cheapestOutfit = outfitService.getCheapestOutfit();

    OutfitResponse outfitResponse = OutfitResponse.from(cheapestOutfit);

    return ResponseEntity.ok(outfitResponse);
  }

  @Operation(summary = "최저가 브랜드의 코디 조회", description = "최저가 브랜드의 코디를 반환.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 성공"),
  })
  @GetMapping("/cheapest/brand")
  public ResponseEntity<CheapestBrandOutfitResponse> getCheapestOutfitByBrand() {
    final Outfit cheapestOutfitByBrand = outfitService.getCheapestOutfitByBrand();

    CheapestBrandOutfitResponse outfitResponse = CheapestBrandOutfitResponse.from(cheapestOutfitByBrand);

    return ResponseEntity.ok(outfitResponse);
  }
}
