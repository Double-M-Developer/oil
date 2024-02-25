// 지도를 생성하고 초기화하는 함수
var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 초기 중심 좌표 설정
        level: 9 // 초기 확대 레벨 설정
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성

// 사용자가 선택한 지역과 제품 코드에 따라 데이터를 업데이트하는 함수
function updateData() {
    clearMarkers();
    var areaCode = document.getElementById('areaCode').value;
    var subAreaCode = document.getElementById('subAreaCode').value;
    var finalAreaCode = areaCode;
    if (subAreaCode !== "none") { // "none"은 세부 지역을 선택하지 않았을 때의 값이라고 가정
        finalAreaCode += subAreaCode; // 세부 지역 코드를 결합
    }
    var productCode = document.getElementById('productCode').value;
    fetchLowTop20PriceData(finalAreaCode, productCode).then(() => showOverlayForFirstStation());
}

function updateSubAreas() {
    var areaCode = document.getElementById('areaCode').value;
    var subAreaSelect = document.getElementById('subAreaCode');
    subAreaSelect.innerHTML = ''; // 세부 지역 선택지 초기화

    // "선택 안함" 옵션 추가
    var defaultOption = document.createElement('option');
    defaultOption.value = "none";
    defaultOption.text = "선택 안함";
    subAreaSelect.appendChild(defaultOption);



    if (subAreas[areaCode]) {
        subAreas[areaCode].forEach(function (subArea) {
            var option = document.createElement('option');
            var value = areaCode + subArea.split(' ')[0]; // 메인 지역 코드와 세부 지역 코드 조합
            var text = subArea.split(' ')[1]; // 세부 지역 이름
            option.value = value;
            option.text = text;
            subAreaSelect.appendChild(option);
        });
    }
}

// 페이지 로드 시 초기 데이터를 가져오고, 첫 번째 주유소에 마커를 표시하는 함수
document.addEventListener("DOMContentLoaded", function () {
    // 페이지 로드 시 데이터를 가져오고 첫 번째 주유소에 마커를 표시합니다.
    updateData();
    updateSubAreas();
});

// fetchLowTop20PriceData 함수 수정 (Promise 반환 추가)
function fetchLowTop20PriceData(areaCode, productCode) {
    areaCode = areaCode || '서울';
    productCode = productCode || '휘발유';

    // fetch 함수 호출 시 Promise 반환
    return fetch(`gas-station/low-top20?areaCode=${areaCode}&productCode=${productCode}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                var firstStation = data[0];
                var center = new kakao.maps.LatLng(firstStation.GIS_Y_COOR, firstStation.GIS_X_COOR);
                map.setCenter(center);
                clearOverlays();
                data.forEach((station, index) => {
                    addMarkerAndCustomOverlay(station, index);
                });
                generateLowTop20PriceTable(data);
                // 데이터 로드 완료 후 첫 번째 주유소에 대한 마커와 오버레이를 표시
                showOverlayForFirstStation();
            }
        })
        .catch(error => console.error('Error:', error));
}

// 저렴한 주유소 정보를 기반으로 테이블을 생성하는 함수
function generateLowTop20PriceTable(data) {
    const tableContainer = document.getElementById('rankTableContainer');
    if (!tableContainer) {
        console.error('Table container element not found!');
        return;
    }

    tableContainer.innerHTML = ''; // 테이블 내용 초기화

    const table = document.createElement('table');
    table.classList.add('rank-table');
    table.appendChild(createTableHeader(['순위', '상표', '상호', '가격', '상세 주소']));
    table.appendChild(createTableBodyFromLowTop20Data(data));

    tableContainer.appendChild(table);
}

// 테이블 헤더를 생성하는 함수
function createTableHeader(headers) {
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    headers.forEach(header => {
        const th = document.createElement('th');
        th.textContent = header;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    return thead;
}

// 테이블 본문을 생성하는 함수
function createTableBodyFromLowTop20Data(data) {
    const tbody = document.createElement('tbody');
    data.forEach((item, index) => {
        const row = document.createElement('tr');
        row.appendChild(createCell(index + 1));
        row.appendChild(createCell(item.POLL_DIV_CD));
        row.appendChild(createCell(item.OS_NM));
        row.appendChild(createCell(`${item.PRICE}원`));
        row.appendChild(createCell(item.NEW_ADR || item.VAN_ADR));
        row.addEventListener('click', () => moveToStationAndShowOverlay(item, index, map));
        tbody.appendChild(row);
    });
    return tbody;
}

// 테이블 셀을 생성하는 함수
function createCell(text) {
    const cell = document.createElement('td');
    cell.textContent = text;
    return cell;
}

var infowindows = []; // 오버레이를 저장할 배열 초기화
var markers = []; // 마커를 저장할 배열 추가

// 지도 중심을 이동시키고 주유소에 대한 오버레이를 표시하는 함수
function moveToStationAndShowOverlay(station, index, map) {
    var moveLatLon = new kakao.maps.LatLng(station.GIS_Y_COOR, station.GIS_X_COOR);

    // 지도 중심을 부드럽게 이동시킴
    map.panTo(moveLatLon);

    // 해당 주유소의 오버레이가 존재하는 경우, 다른 모든 오버레이를 닫고 해당 오버레이만 표시
    if (infowindows[index]) {
        closeAllInfoWindows(); // 기존에 열려있는 모든 오버레이를 닫기
        infowindows[index].setMap(map); // 클릭한 주유소의 오버레이만을 표시
    }
}

// 마커와 커스텀 오버레이를 추가하는 함수
function addMarkerAndCustomOverlay(station, index) {
    var position = new kakao.maps.LatLng(station.GIS_Y_COOR, station.GIS_X_COOR);
    var marker = new kakao.maps.Marker({
        map: map,
        position: position
    });

    var content = `
        <div class="custom-overlay">
    <div style="margin-bottom: 20px; position: relative; background-color: #FFF; border: 1px solid #ccc; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.3); overflow: hidden; font-family: 'Roboto', sans-serif;">
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

    overlay.setMap(null); // 오버레이 초기에는 숨김
    kakao.maps.event.addListener(marker, 'click', function () {
        closeAllInfoWindows(); // 다른 오버레이 닫기
        overlay.setMap(map); // 클릭한 마커의 오버레이 표시
    });

    infowindows[index] = overlay; // 오버레이 저장
    markers.push(marker); // 생성된 마커를 markers 배열에 추가
}

// 특정 오버레이를 닫는 함수
function closeOverlay(index) {
    if (infowindows[index]) {
        infowindows[index].setMap(null);
    }
}

// 모든 오버레이를 닫는 함수
function closeAllInfoWindows() {
    infowindows.forEach(function (overlay) {
        overlay.setMap(null);
    });
}

// 모든 오버레이를 지우는 함수
function clearOverlays() {
    closeAllInfoWindows();
    infowindows = []; // 오버레이 배열 리셋
}

// 모든 마커를 지우는 함수
function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null); // 지도에서 마커 제거
    }
    markers = []; // 마커 배열 초기화
}

// 첫 번째 주유소에 대한 마커와 오버레이를 표시하는 함수
function showOverlayForFirstStation() {
    if (infowindows.length > 0) {
        var firstStationOverlay = infowindows[0];
        firstStationOverlay.setMap(map);
        var position = firstStationOverlay.getPosition();
        map.panTo(position); // 지도 중심을 첫 번째 주유소 위치로 이동합니다.
    }
}