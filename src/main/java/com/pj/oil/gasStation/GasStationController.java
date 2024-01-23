package com.pj.oil.gasStation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class GasStationController {

    private final GasStationService gasStationService;

    /**
     * 모든 GasStation 를 조회할 수 있는 link 를 entityModel 에 담기
     * @param findGasStations
     * @return EntityModel<GasStation>
     */
    private EntityModel<GasStation> getGasStationEntityModel(Optional<GasStation> findGasStations) {
        EntityModel<GasStation> entityModel = EntityModel.of(findGasStations.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllGasStations());
        entityModel.add(link.withRel("all-gas-station"));
        return entityModel;
    }

    /**
     * 모든 GasStation 를 조회할 수 있는 link 를 entityModel 에 담기
     * @param findGasStations
     * @return EntityModel<List<GasStationPriceDto>>
     */
    private EntityModel<List<GasStationPriceDto>> getListEntityModel(List<GasStationPriceDto> findGasStations) {
        EntityModel<List<GasStationPriceDto>> entityModel = EntityModel.of(findGasStations);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllGasStations());
        entityModel.add(link.withRel("all-gas-station"));
        return entityModel;
    }

    /**
     * 모든 GasStation 를 조회할 수 있는 link 를 entityModel 에 담기
     * @param findGasStations
     * @return EntityModel<List<GasStation>>
     */
    private EntityModel<List<GasStation>> getEntityModel(List<GasStation> findGasStations) {
        EntityModel<List<GasStation>> entityModel = EntityModel.of(findGasStations);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllGasStations());
        entityModel.add(link.withRel("all-gas-station"));
        return entityModel;
    }

    @Operation(summary = "주유소_전체_및_가격조회", description = "db에 있는 주유소_전체_및_가격 데이터 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station")
    public ResponseEntity<List<GasStation>> findAllGasStations() {
        List<GasStation> findGasStations = gasStationService.findAllGasStations();
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(findGasStations);
    }

    @Operation(summary = "주유소_코드에_해당하는_주유소_조회", description = "주유소_코드에_해당하는_주유소 데이터 db에서 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station/uni-id/{uniId}")
    public ResponseEntity<EntityModel<GasStation>> findByUniId(@PathVariable String uniId) {
        Optional<GasStation> findGasStations = gasStationService.findByUniId(uniId);
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        EntityModel<GasStation> entityModel = getGasStationEntityModel(findGasStations);

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "주유소_제품에_해당하는_주유소_가격_조회", description = "주유소_제품에_해당하는_주유소 데이터 db에서 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station/prodcd/{prodcd}")
    public ResponseEntity<EntityModel<List<GasStationPriceDto>>> findByProdcd(@PathVariable ProductCode prodcd) {
        List<GasStationPriceDto> findGasStations = gasStationService.findByProdcd(prodcd);
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        EntityModel<List<GasStationPriceDto>> entityModel = getListEntityModel(findGasStations);

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "지역코드로 주유소 검색", description = "지역코드로 db에 있는 주유소 데이터 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station/area-cd/{areaCd}")
    public ResponseEntity<EntityModel<List<GasStation>>> findByArea(@PathVariable String areaCd) {
        List<GasStation> findGasStations = gasStationService.findByArea(areaCd);
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        EntityModel<List<GasStation>> entityModel = getEntityModel(findGasStations);

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "주유소 좌표로 주유소 검색", description = "주유소 좌표로 db에 있는 주유소 데이터 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station/gis-coor/{gisXCoor}/{gisYCoor}")
    public ResponseEntity<EntityModel<GasStation>> findByGisCoor(@PathVariable double gisXCoor, @PathVariable double gisYCoor) {
        Optional<GasStation> findGasStations = gasStationService.findByGisCoor(gisXCoor, gisYCoor);
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        EntityModel<GasStation> entityModel = getGasStationEntityModel(findGasStations);

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "주유소 상표로 주유소 검색", description = "주유소 상표로 db에 있는 주유소 데이터 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station/poll-div-cd/{pollDivCd}")
    public ResponseEntity<EntityModel<List<GasStation>>> findByPollDivCd(@PathVariable PollDivCode pollDivCd) {
        List<GasStation> findGasStations = gasStationService.findByPollDivCd(pollDivCd);
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        EntityModel<List<GasStation>> entityModel = getEntityModel(findGasStations);

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "주유소 상호로 주유소 검색", description = "주유소 상호로 db에 있는 주유소 데이터 조회")
    @ApiResponse(content = @Content(schema = @Schema(implementation = GasStation.class)))
    @GetMapping(value = "/v1/gas-station/os-nm/{osNm}")
    public ResponseEntity<EntityModel<GasStation>> findByOsNm(@PathVariable String osNm) {
        Optional<GasStation> findGasStations = gasStationService.findByOsNm(osNm);
        if (findGasStations.isEmpty()) { // 예외처리 구문
            return ResponseEntity.noContent().build();
        }
        EntityModel<GasStation> entityModel = getGasStationEntityModel(findGasStations);

        return ResponseEntity.ok(entityModel);
    }

}
