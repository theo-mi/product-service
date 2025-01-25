package com.mms.product.controller;

import com.mms.product.model.Outfit;
import com.mms.product.model.response.CheapestBrandOutfitResponse;
import com.mms.product.model.response.OutfitResponse;
import com.mms.product.service.OutfitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("outfits")
@RequiredArgsConstructor
public class OutfitController {

  private final OutfitService outfitService;

  /**
   * 가장 저렴한 옷 조합을 조회한다.
   */
  @GetMapping("/cheapest")
  public ResponseEntity<OutfitResponse> getCheapestOutfit() {
    final Outfit cheapestOutfit = outfitService.getCheapestOutfit();

    OutfitResponse outfitResponse = OutfitResponse.from(cheapestOutfit);

    return ResponseEntity.ok(outfitResponse);
  }

  /**
   * 가장 저렴한 브랜드의 옷 조합을 조회한다.
   *
   * @return 가장 저렴한 브랜드의 옷 조합
   */
  @GetMapping("/cheapest/brand")
  public ResponseEntity<CheapestBrandOutfitResponse> getCheapestOutfitByBrand() {
    final Outfit cheapestOutfitByBrand = outfitService.getCheapestOutfitByBrand();

    CheapestBrandOutfitResponse outfitResponse = CheapestBrandOutfitResponse.from(cheapestOutfitByBrand);

    return ResponseEntity.ok(outfitResponse);
  }
}
