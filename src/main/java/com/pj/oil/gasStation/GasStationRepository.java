package com.pj.oil.gasStation;

import com.pj.oil.gasStation.GasStationPriceDto;
import com.pj.oil.gasStation.GasStation;
import com.pj.oil.gasStation.PollDivCode;
import com.pj.oil.gasStation.ProductCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Long> {
    Optional<GasStation> findByUniId(String uniId);

    @Query("select s from GasStation s join fetch s.area a where a.areaCd = :areaCd")
    List<GasStation> findByArea(@Param("areaCd") String areaCd);


    Optional<GasStation> findByGisXCoorAndGisYCoor(double gisXCoor, double gisYCoor);

    List<GasStation> findByPollDivCd(PollDivCode PollDivCd);

    Optional<GasStation> findByOsNm(String osNm);

    @Query("select new com.pj.oil.dto.GasStationPriceDto(" +
            "s.uniId, s.area, s.pollDivCd, s.osNm, s.vanAdr, s.newAdr, s.gisXCoor, s.gisYCoor, " +
            "p.id, p.prodcd, p.price, p.updatedAt) " +
            "from GasStation s " +
            "join GasStationPrice p " +
            "on s.uniId = p.gasStation.uniId " +
            "where p.prodcd = :prodcd")
    List<GasStationPriceDto> findByProdcd(@Param("prodcd") ProductCode prodcd);

    @Query("select new com.pj.oil.dto.GasStationPriceDto(" +
            "s.uniId, s.area, s.pollDivCd, s.osNm, s.vanAdr, s.newAdr, s.gisXCoor, s.gisYCoor, " +
            "p.id, p.prodcd, p.price, p.updatedAt) " +
            "from GasStation s " +
            "join GasStationPrice p " +
            "on s.uniId = p.gasStation.uniId " +
            "where p.updatedAt = (select max(p2.updatedAt) from GasStationPrice p2 where p2.gasStation.uniId = s.uniId)")
    List<GasStationPriceDto> findLatestPrices();

}
