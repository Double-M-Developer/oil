var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 9
    };

var map = new kakao.maps.Map(mapContainer, mapOption);

function updateData() {
    var areaCode = document.getElementById('areaCode').value;
    var productCode = document.getElementById('productCode').value;
    fetchLowTop20PriceData(areaCode, productCode);
}

function fetchLowTop20PriceData(areaCode, productCode) {
    areaCode = areaCode || '서울';
    productCode = productCode || '휘발유';

    fetch(`gas-station/low-top20?areaCode=${areaCode}&productCode=${productCode}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                var firstStation = data[0];
                var center = new kakao.maps.LatLng(firstStation.GIS_Y_COOR, firstStation.GIS_X_COOR);
                map.setCenter(center);
                clearOverlays(); // 기존 오버레이를 지우고 새로운 오버레이를 추가합니다.
                data.forEach((station, index) => {
                    addMarkerAndCustomOverlay(station, index);
                });
            }
        })
        .catch(error => console.error('Error:', error));
}

var infowindows = []; // 인포윈도우 저장 배열 초기화

function addMarkerAndCustomOverlay(station, index) {
    var position = new kakao.maps.LatLng(station.GIS_Y_COOR, station.GIS_X_COOR);
    var marker = new kakao.maps.Marker({
        map: map,
        position: position
    });

    var content = `
        <div class="custom-overlay">
    <div style="position: relative; background-color: #FFF; border: 1px solid #ccc; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.3); overflow: hidden; font-family: 'Roboto', sans-serif;">
        <strong style="display: block; padding: 10px 15px; background-color: #007bff; color: #FFF; font-size: 14px; border-bottom: 1px solid #eee;">${index + 1}. ${station.OS_NM}</strong>
        <span style="display: block; padding: 5px 15px; color: #333; font-size: 12px;">가격: ${station.PRICE}원</span>
        <button style="display: inline-block; margin: 5px 15px; padding: 3px 8px; background-color: #007bff; border: none; border-radius: 4px; color: #fff; cursor: pointer; font-size: 12px;" onclick="closeOverlay(${index})">닫기</button>
    </div>
        </div>
    `;

    var overlay = new kakao.maps.CustomOverlay({
        content: content,
        position: marker.getPosition(),
        yAnchor: 1
    });

    overlay.setMap(null); // 처음에는 오버레이를 숨깁니다.
    kakao.maps.event.addListener(marker, 'click', function() {
        closeAllInfoWindows(); // 다른 모든 오버레이를 닫고
        overlay.setMap(map); // 클릭한 마커의 오버레이만 표시합니다.
    });

    infowindows[index] = overlay; // 오버레이를 배열에 저장합니다.
}

function closeOverlay(index) {
    if (infowindows[index]) {
        infowindows[index].setMap(null);
    }
}

function closeAllInfoWindows() {
    infowindows.forEach(function(overlay) {
        overlay.setMap(null);
    });
}

function clearOverlays() {
    closeAllInfoWindows();
    infowindows = []; // 오버레이 배열을 리셋합니다.
}

document.addEventListener("DOMContentLoaded", function() {
    fetchLowTop20PriceData('01', 'B027');
});
