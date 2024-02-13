/* globals Chart:false */
// AJAX 요청을 처리하는 함수
const colorSet = ['#007bff', '#ff007b', '#ff7b00', '#00ff7b']

const fetchChartData = () => {
    fetch('/gas-station/avg') // 서버 엔드포인트 경로
        .then(response => response.json())
        .then(data => {
            // 서버로부터 받은 labels을 차트의 labels로 설정

            console.log(data);
            myChart.data.labels = data.labels;

            // 각 데이터셋에 서버로부터 받은 평균 가격 데이터 할당
            data.prices.forEach((priceObj, index) => {
                if (myChart.data.datasets[index]) {
                    myChart.data.datasets[index].data = priceObj.averagePrice;
                }
            });

            // 차트 업데이트
            myChart.update();
        })
        .catch(error => console.error('Error fetching data:', error));
};


// Graphs
const ctx = document.getElementById('myChart')
// eslint-disable-next-line no-unused-vars
const myChart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [
            'Sunday',
            'Monday',
            'Tuesday',
            'Wednesday',
            'Thursday',
            'Friday',
            'Saturday'
        ],
        datasets: [
            {
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#007bff'
            }, {
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#ff007b',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#ff007b'
            },{
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: 'rgba(119,255,0,0.66)',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: 'rgba(119,255,0,0.66)'
            },{
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: 'rgba(183,0,255,0.66)',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: 'rgba(183,0,255,0.66)'
            }
        ]
    },
    options: {
        plugins: {
            legend: {
                display: false
            },
            tooltip: {
                boxPadding: 3
            }
        }
    }
});
// })()

// 페이지 로드 시 차트 데이터 업데이트
fetchChartData();

// 예시 데이터, 실제로는 백엔드에서 가져온 데이터를 사용합니다.
const rankData = {
    preGasoline: ["brand1", "brand3", "brand2"],
    gasoline: ["brand2", "brand1", "brand3"],
    diesel: ["brand2", "brand2", "brand1"]
};

function generateRankTable(data) {
    const table = document.createElement("table");
    table.style.width = '100%';
    table.setAttribute('border', '1');
    const thead = table.createTHead();
    const tbody = table.createTBody();

    // 헤더 생성
    const headerRow = thead.insertRow();
    const firstHeaderCell = document.createElement("th");
    firstHeaderCell.textContent = "종류 / 순위";
    headerRow.appendChild(firstHeaderCell);
    Object.keys(data).forEach(fuelType => {
        const headerCell = document.createElement("th");
        headerCell.textContent = fuelType;
        headerRow.appendChild(headerCell);
    });

    // 데이터 채우기
    const rowCount = data[Object.keys(data)[0]].length; // 순위 개수
    for (let i = 0; i < rowCount; i++) {
        const row = tbody.insertRow();
        const firstCell = row.insertCell();
        firstCell.textContent = `${i + 1}위`;
        Object.values(data).forEach(fuelRanks => {
            const cell = row.insertCell();
            cell.textContent = fuelRanks[i]; // 각 연료 종류별 순위에 따른 브랜드
        });
    }

    document.getElementById("fuelRankTable").appendChild(table);
}

// 테이블 생성 함수 호출
generateRankTable(rankData);
