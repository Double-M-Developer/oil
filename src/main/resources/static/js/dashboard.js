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

        ],
        datasets: [
            {
                label: '고급 휘발유',
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#007bff',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#007bff'
            }, {
                label: '휘발유',
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: '#ff007b',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: '#ff007b'
            },{
                label: '경유',
                data: [

                ],
                lineTension: 0, // 0 고정
                backgroundColor: 'transparent',
                borderColor: 'rgba(119,255,0,0.66)',
                borderWidth: 4, // 그래프 곡선 두께
                pointBackgroundColor: 'rgba(119,255,0,0.66)'
            },{
                label: 'lpg',
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
                display: true,
                position : 'bottom'
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


// 페이지 로드 시 차트 데이터 업데이트 및 순위 테이블 생성
fetch('/gas-station/ranks') // 백엔드 엔드포인트 경로 수정
    .then(response => response.json())
    .then(rankData => {
        console.log(rankData);
        generateRankTable(rankData); // 순위 데이터를 사용하여 테이블 생성
    })
    .catch(error => console.error('Error fetching data:', error));


function generateRankTable(rankData) {
    // 테이블을 추가할 부모 요소를 찾거나 지정합니다.
    const tableContainer = document.getElementById('rankTableContainer');
    if (!tableContainer) {
        console.error('Table container element not found!');
        return;
    }

    // 기존에 테이블이 있으면 삭제합니다.
    while (tableContainer.firstChild) {
        tableContainer.removeChild(tableContainer.firstChild);
    }

    // 새 테이블 요소를 생성합니다.
    const table = document.createElement('table');
    table.classList.add('rank-table'); // 스타일을 위한 클래스 추가 (옵션)

    // 테이블 헤더를 생성합니다.
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    ['종류', '1위', '2위', '3위'].forEach(text => {
        const th = document.createElement('th');
        th.textContent = text;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // 테이블 본문을 생성합니다.
    const tbody = document.createElement('tbody');
    Object.keys(rankData).forEach(fuelType => {
        const row = document.createElement('tr');
        const fuelTypeCell = document.createElement('td');
        fuelTypeCell.textContent = fuelType;
        row.appendChild(fuelTypeCell);

        rankData[fuelType].forEach(brand => {
            const cell = document.createElement('td');
            cell.textContent = brand;
            row.appendChild(cell);
        });

        tbody.appendChild(row);
    });
    table.appendChild(tbody);

    // 생성한 테이블을 페이지에 추가합니다.
    tableContainer.appendChild(table);
}
