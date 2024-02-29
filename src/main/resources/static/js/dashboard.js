/* globals Chart:false */

const colorSet = [
    '#3498db', // 고급휘발유 - 밝은 청색 (전국 평균)
    '#2ecc71', // 휘발유 - 밝은 녹색 (전국 평균)
    '#f1c40f', // 경유 - 밝은 황색 (전국 평균)
    '#9b59b6', // LPG - 밝은 보라색 (전국 평균)
];

const areaColorSet = [
    '#e74c3c', // 고급휘발유 - 밝은 붉은색 (지역 평균)
    '#16a085', // 휘발유 - 청록색 (지역 평균)
    '#d35400', // 경유 - 단풍색 (지역 평균)
    '#34495e', // LPG - 진한 남색 (지역 평균)
];
const labels = ['고급휘발유', '휘발유', '경유', 'LPG'];
const areaLabels = ['지역 고급 휘발유', '지역 휘발유', '지역 경유'];

// 페이지 로드 시 호출
document.addEventListener('DOMContentLoaded', function() {
    updateData()
    updateSubAreas();
});

let areaAveragePriceData = []; // 전역 변수로 지역별 평균 가격 데이터 저장

function updateData() {
    var areaCode = document.getElementById('areaCode').value;
    var subAreaCode = document.getElementById('subAreaCode').value;
    var finalAreaCode = areaCode;
    if (subAreaCode !== "none") { // "none"은 세부 지역을 선택하지 않았을 때의 값이라고 가정
        finalAreaCode = subAreaCode; // 세부 지역 코드를 결합
    }
    var productCode = document.getElementById('productCode').value;
    fetchDataAndRender(finalAreaCode, productCode);
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

async function fetchDataAndRender(areaCode, productCode) {
    areaCode = areaCode || '01';
    productCode = productCode || '휘발유';
    try {
        // 비동기 함수 호출을 await로 처리
        const allPriceData = await fetchAndRenderChartData(productCode);
        const areaPriceData = await fetchAndRenderAreaAverageRecentPrice(areaCode, productCode);

        // 배열로 래핑하지 않고 직접 전달
        updateChartDataWithAreaAveragePrice(allPriceData, areaPriceData);
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function fetchAndRenderChartData(productCode) {
    productCode = productCode || '휘발유';
    const response = await fetch(`/gas-station/average-recent-price?productCode=${productCode}`);
    const data = await response.json();
    console.log(data);
    return data; // 데이터 반환
}

async function fetchAndRenderAreaAverageRecentPrice(areaCode, productCode) {
    areaCode = areaCode || '01';
    productCode = productCode || '휘발유';
    try {
        const response = await fetch(`/gas-station/area-average-recent-price?areaCode=${areaCode}&productCode=${productCode}`);
        if (!response.ok) {
            throw new Error(`Server responded with status ${response.status}`);
        }

        const text = await response.text();
        if (!text) {
            throw new Error('Response body is empty');
        }

        // JSON.parse를 사용하여 응답 텍스트를 안전하게 파싱
        // 빈 문자열이거나 유효하지 않은 JSON일 경우 catch 블록으로 이동
        const data = JSON.parse(text);
        console.log(data);
        return data;
    } catch (error) {
        console.error('Error fetching area average recent price data:', error);
        // 에러 발생 시 빈 배열 또는 기본 값을 반환하여 함수의 나머지 로직이 계속 실행될 수 있도록 함
        return [];
    }
}

let myChart; // 차트 인스턴스를 저장할 전역 변수
// 제품 코드에 따른 색상 반환 함수
function getColorForProductCode(productCode) {
    const index = labels.indexOf(productCode); // labels 배열에서 제품 코드의 인덱스 찾기
    return colorSet[index % colorSet.length]; // 색상 배열에서 해당 인덱스의 색상 반환
}
function getAreaColorForLabel(productCode) {
    const index = labels.indexOf(productCode); // labels 배열에서 제품 코드의 인덱스 찾기
    return areaColorSet[index % areaColorSet.length]; // 색상 배열에서 해당 인덱스의 색상 반환
}
function updateChartDataWithAreaAveragePrice(allPrice, areaPrice) {
    const ctx = document.getElementById('myChart');
    if (ctx) {
        if (myChart) {
            myChart.destroy();
        }

        // 모든 날짜 추출 및 정렬
        const allDates = [...new Set([...allPrice.map(item => item.DATE), ...areaPrice.map(item => item.DATE)])].sort();

        // allPrice 데이터셋 생성
        const allPriceDatasets = allPrice.reduce((acc, item) => {
            let dataset = acc.find(ds => ds.label === item.PRODCD + " (전국)");
            if (!dataset) {
                dataset = {
                    label: item.PRODCD + " (전국)",
                    lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: getColorForProductCode(item.PRODCD),
                    pointBackgroundColor: getColorForProductCode(item.PRODCD),
                    borderWidth: 4,
                    data: new Array(allDates.length).fill(null)
                };
                acc.push(dataset);
            }
            const dateIndex = allDates.indexOf(item.DATE);
            dataset.data[dateIndex] = item.PRICE;
            return acc;
        }, []);

        // areaPrice 데이터셋 생성
        const areaPriceDatasets = areaPrice.reduce((acc, item) => {
            let dataset = acc.find(ds => ds.label === item.PRODCD + " (지역)");
            if (!dataset) {
                dataset = {
                    label: item.PRODCD + " (지역)",
                    lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: getAreaColorForLabel(item.PRODCD),
                    pointBackgroundColor: getAreaColorForLabel(item.PRODCD),
                    borderWidth: 4,
                    data: new Array(allDates.length).fill(null)
                };
                acc.push(dataset);
            }
            const dateIndex = allDates.indexOf(item.DATE);
            dataset.data[dateIndex] = item.PRICE;
            return acc;
        }, []);

        // allPrice와 areaPrice 데이터셋을 합쳐서 차트 생성
        const combinedDatasets = allPriceDatasets.concat(areaPriceDatasets);

        myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: allDates,
                datasets: combinedDatasets
            },
            options: getChartOptions(),
        });
    }
}

function getChartOptions() {
    return {
        plugins: {
            legend: {
                display: true,
                position: 'bottom',
            },
            tooltip: {
                enabled: true,
                mode: 'index', // 동일한 인덱스의 모든 데이터 세트 정보를 함께 표시
                intersect: false, // 마우스 위치와 상관없이 모든 데이터 세트를 표시
                bodySpacing: 8, // 툴팁 내부 텍스트 간 간격을 8px로 설정
                callbacks: {
                    label: function(context) {
                        // 라벨 커스터마이즈: 데이터 세트의 이름과 값을 포함
                        let label = context.dataset.label || '';
                        if (label) {
                            label += ': ';
                        }
                        if (context.parsed.y !== null) {
                            label += context.parsed.y;
                        }
                        return label;
                    }
                }
            },
        }
    };
}
